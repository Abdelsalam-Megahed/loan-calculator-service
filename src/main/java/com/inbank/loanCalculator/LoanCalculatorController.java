package com.inbank.loanCalculator;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/calculate")
@CrossOrigin
public class LoanCalculatorController {
    private final LoanCalculatorService loanCalculatorService;

    @PostMapping
    public ResponseEntity<LoanCalculatorResponse> calculateMaximumLoanAmount(@RequestBody @Valid LoanCalculatorRequest loanCalculatorRequest) throws Exception {
        LoanCalculatorResponse response = loanCalculatorService.calculateMaximumLoanAmount(loanCalculatorRequest);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
