package com.hasan.DTO;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerResponse {

    private Long customerId;

    private String firstName;

    private String lastName;

    private String email;

    private String mobile;

    private LocalDate dob;

    private String gender;

    private String aadhaarNumber;

    private String panNumber;

    private String occupation;

    private BigDecimal annualIncome;

    private String customerStatus;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}