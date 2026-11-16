
package com.hasan.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hasan.DTO.AccountRequest;
import com.hasan.DTO.AccountResponse;
import com.hasan.DTO.AccountValidationResponse;
import com.hasan.DTO.BalanceUpdateRequest;
import com.hasan.DTO.CustomerResponse;
import com.hasan.client.CustomerClient;
import com.hasan.entity.Account;
import com.hasan.entity.AccountNumberGenerator;
import com.hasan.exception.ResourceNotFoundException;
import com.hasan.mapper.AccountMapper;
import com.hasan.repository.AccountRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class AccountServiceImpl implements AccountService {

	private final AccountRepository accountRepository;
	private final CustomerClient customerClient;
	private final AccountNumberGenerator accountNumberGenerator;

	// Create account
	@Override
	@Caching(evict = { @CacheEvict(cacheNames = "accountList", allEntries = true),
			@CacheEvict(cacheNames = "accountsByStatus", allEntries = true),
			@CacheEvict(cacheNames = "accountsByCustomer", allEntries = true) })
	public AccountResponse createAccount(AccountRequest request) {

		CustomerResponse customer = customerClient.getCustomer(request.getCustomerId());

		if (customer == null) {
			throw new ResourceNotFoundException("Customer Not Found");
		}

		Account account = Account.builder().customerId(request.getCustomerId())
				.accountNumber(accountNumberGenerator.generate()).accountType(request.getAccountType())
				.balance(BigDecimal.ZERO).accountStatus("ACTIVE").branchCode(request.getBranchCode())
				.ifscCode(request.getIfscCode()).createdAt(LocalDateTime.now()).updatedAt(LocalDateTime.now()).build();

		Account savedAccount = accountRepository.save(account);

		return AccountMapper.mapToResponse(savedAccount);
	}

	// Get account by ID
	@Override
	@Transactional(readOnly = true)
	@Cacheable(cacheNames = "accountDetails", key = "#accountId", unless = "#result == null")
	public Account getAccount(Long accountId) {

		return accountRepository.findById(accountId)
				.orElseThrow(() -> new ResourceNotFoundException("Account Not Found"));
	}

	// Get all accounts
	@Override
	@Transactional(readOnly = true)
	@Cacheable(cacheNames = "accountList", key = "'all'", unless = "#result == null")
	public List<AccountResponse> getAllAccount() {

		List<Account> accounts = accountRepository.findAll();

		if (accounts.isEmpty()) {
			throw new ResourceNotFoundException("Accounts Not Found");
		}

		return accounts.stream().map(AccountMapper::mapToResponse).toList();
	}

	// Get accounts by status
	@Override
	@Transactional(readOnly = true)
	@Cacheable(cacheNames = "accountsByStatus", key = "#status", unless = "#result == null")
	public List<AccountResponse> getAccountByStatus(String status) {

		List<Account> accounts = accountRepository.findByAccountStatus(status);

		if (accounts.isEmpty()) {
			throw new ResourceNotFoundException("Accounts Not Found");
		}

		return accounts.stream().map(AccountMapper::mapToResponse).toList();
	}

	// Get accounts by customer ID
	@Override
	@Transactional(readOnly = true)
	@Cacheable(cacheNames = "accountsByCustomer", key = "#customerId", unless = "#result == null")
	public List<AccountResponse> getAccountsByCustomer(Long customerId) {

		return accountRepository.findByCustomerId(customerId).stream().map(AccountMapper::mapToResponse).toList();
	}

	// Freeze account
	@Override
	@Caching(evict = { @CacheEvict(cacheNames = "accountDetails", key = "#accountId"),
			@CacheEvict(cacheNames = "accountList", allEntries = true),
			@CacheEvict(cacheNames = "accountsByStatus", allEntries = true),
			@CacheEvict(cacheNames = "accountsByCustomer", allEntries = true) })
	public AccountResponse freezeAccount(Long accountId) {

		Account account = findAccount(accountId);
		account.setAccountStatus("FROZEN");
		account.setUpdatedAt(LocalDateTime.now());

		return AccountMapper.mapToResponse(accountRepository.save(account));
	}

	// Activate account
	@Override
	@Caching(evict = { @CacheEvict(cacheNames = "accountDetails", key = "#accountId"),
			@CacheEvict(cacheNames = "accountList", allEntries = true),
			@CacheEvict(cacheNames = "accountsByStatus", allEntries = true),
			@CacheEvict(cacheNames = "accountsByCustomer", allEntries = true) })
	public AccountResponse activateAccount(Long accountId) {

		Account account = findAccount(accountId);
		account.setAccountStatus("ACTIVE");
		account.setUpdatedAt(LocalDateTime.now());

		return AccountMapper.mapToResponse(accountRepository.save(account));
	}

	// Close account
	@Override
	@Caching(evict = { @CacheEvict(cacheNames = "accountDetails", key = "#accountId"),
			@CacheEvict(cacheNames = "accountList", allEntries = true),
			@CacheEvict(cacheNames = "accountsByStatus", allEntries = true),
			@CacheEvict(cacheNames = "accountsByCustomer", allEntries = true) })
	public AccountResponse closeAccount(Long accountId) {

		Account account = findAccount(accountId);

		if (account.getBalance().compareTo(BigDecimal.ZERO) != 0) {
			throw new IllegalArgumentException("Account balance must be zero before closing");
		}

		account.setAccountStatus("CLOSED");
		account.setUpdatedAt(LocalDateTime.now());

		return AccountMapper.mapToResponse(accountRepository.save(account));
	}

	// Validate account number
	@Override
	@Transactional(readOnly = true)
	public AccountValidationResponse validateAccount(String accountNumber) {

		Account account = accountRepository.findByAccountNumber(accountNumber)
				.orElseThrow(() -> new ResourceNotFoundException("Account Not Found"));

		return AccountValidationResponse.builder().valid("ACTIVE".equals(account.getAccountStatus()))
				.accountStatus(account.getAccountStatus()).build();
	}

	// Get current balance - deliberately not cached
	@Override
	@Transactional(readOnly = true)
	public BigDecimal getBalance(String accountNumber) {

		Account account = accountRepository.findByAccountNumber(accountNumber)
				.orElseThrow(() -> new ResourceNotFoundException("Account Not Found"));

		return account.getBalance();
	}

	// Update balance after a successful deposit, withdrawal or transfer
	@Override
	@Caching(evict = { @CacheEvict(cacheNames = "accountDetails", allEntries = true),
			@CacheEvict(cacheNames = "accountList", allEntries = true),
			@CacheEvict(cacheNames = "accountsByStatus", allEntries = true),
			@CacheEvict(cacheNames = "accountsByCustomer", allEntries = true) })
	public String updateBalance(BalanceUpdateRequest request) {

		Account account = accountRepository.findByAccountNumber(request.getAccountNumber())
				.orElseThrow(() -> new ResourceNotFoundException("Account Not Found"));

		BigDecimal amount = request.getAmount();

		if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
			throw new IllegalArgumentException("Amount must be greater than zero");
		}

		String operation = request.getOperation();

		if ("DEBIT".equalsIgnoreCase(operation)) {

			if (account.getBalance().compareTo(amount) < 0) {
				throw new IllegalArgumentException("Insufficient Balance");
			}

			account.setBalance(account.getBalance().subtract(amount));

		} else if ("CREDIT".equalsIgnoreCase(operation)) {

			account.setBalance(account.getBalance().add(amount));

		} else {
			throw new IllegalArgumentException("Invalid operation");
		}

		account.setUpdatedAt(LocalDateTime.now());
		accountRepository.save(account);

		return amount + ("DEBIT".equalsIgnoreCase(operation) ? " is debited from account " : " is credited to account ")
				+ request.getAccountNumber();
	}

	private Account findAccount(Long accountId) {

		return accountRepository.findById(accountId)
				.orElseThrow(() -> new ResourceNotFoundException("Account Not Found"));
	}
}
