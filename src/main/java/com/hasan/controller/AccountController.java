package com.hasan.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hasan.DTO.AccountRequest;
import com.hasan.DTO.AccountResponse;
import com.hasan.DTO.AccountValidationResponse;
import com.hasan.DTO.BalanceUpdateRequest;
import com.hasan.entity.Account;
import com.hasan.service.AccountService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class AccountController {

	private final AccountService accountService;

	@PostMapping("/add")
	public ResponseEntity<AccountResponse> createAccount(@Valid @RequestBody AccountRequest request) {

		AccountResponse response = accountService.createAccount(request);

		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	@GetMapping("/my-accounts")
	public ResponseEntity<List<AccountResponse>> getMyAccounts(@AuthenticationPrincipal Jwt jwt) {

		Long customerId = jwt.getClaim("customerId");

		if (customerId == null) {
			throw new IllegalStateException("The authenticated JWT does not contain customerId");
		}

		return ResponseEntity.ok(accountService.getAccountsByCustomer(customerId));
	}

	@GetMapping("/{accountId}")
	public ResponseEntity<Account> getAccount(@PathVariable Long accountId) {

		return ResponseEntity.ok(accountService.getAccount(accountId));
	}

	@GetMapping("/all")
	public ResponseEntity<List<AccountResponse>> getAllAccount() {

		return ResponseEntity.ok(accountService.getAllAccount());
	}

	@GetMapping("/all/{status}")
	public ResponseEntity<List<AccountResponse>> getByStatus(@PathVariable String status) {

		return ResponseEntity.ok(accountService.getAccountByStatus(status));
	}

	@GetMapping("/customer/{customerId}")
	public ResponseEntity<List<AccountResponse>> getCustomerAccounts(@PathVariable Long customerId) {

		return ResponseEntity.ok(accountService.getAccountsByCustomer(customerId));
	}

	@PutMapping("/{accountId}/freeze")
	public ResponseEntity<AccountResponse> freezeAccount(@PathVariable Long accountId) {

		return ResponseEntity.ok(accountService.freezeAccount(accountId));
	}

	@PutMapping("/{accountId}/activate")
	public ResponseEntity<AccountResponse> activateAccount(@PathVariable Long accountId) {

		return ResponseEntity.ok(accountService.activateAccount(accountId));
	}

	@PutMapping("/{accountId}/close")
	public ResponseEntity<AccountResponse> closeAccount(@PathVariable Long accountId) {

		return ResponseEntity.ok(accountService.closeAccount(accountId));
	}

	@GetMapping("/validate/{accNo}")
	public ResponseEntity<AccountValidationResponse> validation(@PathVariable String accNo) {
		return ResponseEntity.ok(accountService.validateAccount(accNo));
	}

	@GetMapping("/balance/{accNo}")
	public ResponseEntity<BigDecimal> getBalance(@PathVariable String accNo) {
		return ResponseEntity.ok(accountService.getBalance(accNo));
	}

	@PutMapping("/updateBalance")
	public ResponseEntity<String> updateBalance(@RequestBody BalanceUpdateRequest req) {
		return ResponseEntity.ok(accountService.updateBalance(req));
	}
}