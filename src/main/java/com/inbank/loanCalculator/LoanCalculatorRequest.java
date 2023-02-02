package com.inbank.loanCalculator;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoanCalculatorRequest {
    @NotBlank(message = "Personal code is invalid!")
    private String personalCode;
    @Min(2000)
    @Max(10000)
    private float loanAmount;
    @Min(12)
    @Max(60)
    private int loanPeriod;
}
