package com.hasan.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountRequest {

	@NotNull(message = "Customer Id is required")
	private Long customerId;

	@NotBlank(message = "Account Type is required")
	private String accountType;

	@NotBlank(message = "Branch Code is required")
	private String branchCode;

	@NotBlank(message = "IFSC Code is required")
	private String ifscCode;
}