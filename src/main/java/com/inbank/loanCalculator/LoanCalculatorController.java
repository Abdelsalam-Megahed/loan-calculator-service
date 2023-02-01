package com.inbank.loanCalculator;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/calculate")
public class LoanCalculatorController {
    private final LoanCalculatorService loanCalculatorService;

    @PostMapping
    public Customer calculateMaximumLoanAmount(@RequestBody LoanCalculatorRequest loanCalculatorRequest) throws Exception {
        return loanCalculatorService.calculateMaximumLoanAmount(loanCalculatorRequest);
    }
}
