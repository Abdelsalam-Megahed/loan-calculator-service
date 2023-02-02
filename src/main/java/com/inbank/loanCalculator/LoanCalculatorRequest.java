package com.inbank.loanCalculator;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoanCalculatorRequest {
    private String personalCode;
    private float loanAmount;
    private int loanPeriod;
}
