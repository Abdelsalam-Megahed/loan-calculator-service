package com.inbank.loanCalculator.controllers;

import com.inbank.loanCalculator.requests.LoanCalculatorRequest;
import com.inbank.loanCalculator.responses.LoanCalculatorResponse;
import com.inbank.loanCalculator.services.LoanCalculatorService;
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
    public ResponseEntity<LoanCalculatorResponse> calculateMaximumLoanSum(@RequestBody @Valid LoanCalculatorRequest loanCalculatorRequest) throws Exception {
        LoanCalculatorResponse response = loanCalculatorService.calculateMaximumLoanSum(loanCalculatorRequest);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
