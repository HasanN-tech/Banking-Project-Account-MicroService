package com.hasan.DTO;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountResponse implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6840849488517555866L;

	private Long accountId;

	private Long customerId;

	private String accountNumber;

	private String accountType;

	private BigDecimal balance;

	private String accountStatus;

	private String branchCode;

	private String ifscCode;

	private LocalDateTime createdAt;
}