package com.hasan.DTO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AccountValidationResponse {

	private boolean valid;

	private String accountStatus;
}