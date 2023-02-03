package com.inbank.loanCalculator;

import com.inbank.loanCalculator.exception.ApplicationCustomException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;

@Service
@AllArgsConstructor
public class LoanCalculatorService {
    final int MAXIMUM_SUM = 10000;

    public LoanCalculatorResponse calculateMaximumLoanAmount(LoanCalculatorRequest loanCalculatorRequest) throws Exception {
        int loanPeriod = loanCalculatorRequest.getLoanPeriod();
        List<Customer> customers = mockCustomers();
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

        while (creditScore < 1 && loanPeriod < 60) {
            loanPeriod = loanPeriod + 1;
            creditScore = calculateCreditScore(customer.getCreditModifier(), loanCalculatorRequest.getLoanAmount(), loanPeriod);
        }

        float maximumSum = calculateMaximumSum(creditScore, loanCalculatorRequest.getLoanAmount());

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

    public List<Customer> mockCustomers() {
        Customer customerInDebt = new Customer("49002010965", 300, true);
        Customer customerInSegmentOne = new Customer("49002010976", 100, false);
        Customer customerInSegmentTwo = new Customer("49002010987", 300, false);
        Customer customerInSegmentThree = new Customer("49002010998", 1000, false);

        return Arrays.asList(customerInDebt, customerInSegmentOne, customerInSegmentTwo, customerInSegmentThree);
    }
}
