package com.hasan.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "bank_account")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Account implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 379574735479799953L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long accountId;

	private Long customerId;

	@Column(unique = true)
	private String accountNumber;

	private String accountType;

	private BigDecimal balance;

	private String accountStatus;

	private String branchCode;

	private String ifscCode;

	private LocalDateTime createdAt;

	private LocalDateTime updatedAt;
}