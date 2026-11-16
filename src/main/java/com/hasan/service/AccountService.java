package com.hasan.service;

import java.math.BigDecimal;
import java.util.List;

import com.hasan.DTO.AccountRequest;
import com.hasan.DTO.AccountResponse;
import com.hasan.DTO.AccountValidationResponse;
import com.hasan.DTO.BalanceUpdateRequest;
import com.hasan.entity.Account;

public interface AccountService {

	AccountResponse createAccount(AccountRequest request);

	Account getAccount(Long accountId);
	
	List<AccountResponse> getAllAccount();
	
	List<AccountResponse> getAccountByStatus(String status);

	List<AccountResponse> getAccountsByCustomer(Long customerId);

	AccountResponse freezeAccount(Long accountId);

	AccountResponse activateAccount(Long accountId);

	AccountResponse closeAccount(Long accountId);

	AccountValidationResponse validateAccount(String accountNumber);

	BigDecimal getBalance(String accountNumber);

	String updateBalance(BalanceUpdateRequest request);
}