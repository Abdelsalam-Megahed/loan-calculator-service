package com.inbank.loanCalculator.services;

import com.inbank.loanCalculator.exceptions.ApplicationCustomException;
import com.inbank.loanCalculator.models.Customer;
import com.inbank.loanCalculator.requests.LoanCalculatorRequest;
import com.inbank.loanCalculator.responses.LoanCalculatorResponse;
import com.inbank.loanCalculator.utils.Mocker;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.List;

@Service
@AllArgsConstructor
public class LoanCalculatorService {
    final int MAXIMUM_SUM = 10000;
    final int MINIMUM_SUM = 2000;

    public LoanCalculatorResponse calculateMaximumLoanSum(LoanCalculatorRequest loanCalculatorRequest) throws Exception {
        int loanPeriod = loanCalculatorRequest.getLoanPeriod();
        List<Customer> customers = Mocker.mockCustomers();
        Customer customer = customers.stream()
                .filter(c -> loanCalculatorRequest.getPersonalCode().equals(c.getPersonalCode()))
                .findAny()
                .orElse(null);

        if (customer == null) {
            throw new ApplicationCustomException("Account not found!");
        }

        if (customer.isHasLoan()) {
            throw new ApplicationCustomException("Customer has a loan already!");
        }

        float creditScore = calculateCreditScore(customer.getCreditModifier(), loanCalculatorRequest.getLoanAmount(), loanPeriod);
        float maximumSum = calculateMaximumSum(creditScore, loanCalculatorRequest.getLoanAmount());

        if (maximumSum < MINIMUM_SUM) {
            loanPeriod = calculateNewPeriod(loanCalculatorRequest.getLoanPeriod(), maximumSum);
            maximumSum = MINIMUM_SUM;
        }

        return new LoanCalculatorResponse(Math.min(maximumSum, MAXIMUM_SUM), loanPeriod);
    }

    public float calculateCreditScore(int creditModifier, float loanAmount, int loanPeriod) throws ApplicationCustomException {
        if (loanAmount <= 0) {
            throw new ApplicationCustomException("Loan amount is invalid!");
        }

        return (creditModifier / loanAmount) * loanPeriod;
    }

    public float calculateMaximumSum(float creditScore, float loanAmount) {
        float maximumSum = creditScore * loanAmount;
        DecimalFormat decimalFormat = new DecimalFormat("#.00");

        return Float.parseFloat(decimalFormat.format(maximumSum));
    }

    public int calculateNewPeriod(int requestedLoanPeriod, float maximumSum){
        return (int) ((requestedLoanPeriod * MINIMUM_SUM) / maximumSum);
    }
}
