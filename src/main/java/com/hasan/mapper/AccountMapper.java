package com.hasan.mapper;

import com.hasan.DTO.AccountResponse;
import com.hasan.entity.Account;

public class AccountMapper {

	private AccountMapper() {
	}

	public static AccountResponse mapToResponse(Account account) {

		return AccountResponse.builder().accountId(account.getAccountId()).customerId(account.getCustomerId())
				.accountNumber(account.getAccountNumber()).accountType(account.getAccountType())
				.balance(account.getBalance()).accountStatus(account.getAccountStatus())
				.branchCode(account.getBranchCode()).ifscCode(account.getIfscCode()).build();
	}
}