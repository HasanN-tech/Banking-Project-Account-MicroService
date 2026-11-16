package com.hasan.DTO;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BalanceUpdateRequest {

    private String accountNumber;

    private BigDecimal amount;

    private String operation;
}