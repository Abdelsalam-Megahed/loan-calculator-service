package com.inbank.loanCalculator;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Customer {
    private String personalCode;
    private int creditModifier;
    private boolean hasLoan;
}
