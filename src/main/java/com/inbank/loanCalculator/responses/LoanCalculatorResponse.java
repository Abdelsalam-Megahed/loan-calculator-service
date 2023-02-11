package com.inbank.loanCalculator.responses;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoanCalculatorResponse {
    private float amount;
    private int loanPeriod;
}
