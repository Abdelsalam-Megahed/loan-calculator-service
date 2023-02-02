package com.inbank.loanCalculator;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class LoanCalculatorService {
    final int MAXIMUM_SUM = 10000;

    public float calculateMaximumLoanAmount(LoanCalculatorRequest loanCalculatorRequest) throws Exception {
        List<Customer> customers = mockCustomers();
        Customer customer = customers.stream()
                .filter(c -> loanCalculatorRequest.getPersonalCode().equals(c.getPersonalCode()))
                .findAny()
                .orElse(null);

        if (customer == null) {
            throw new Exception("Personal code is not found!");
        }

        if (customer.isHasLoan()) {
            throw new Exception("Customer has a loan already!");
        }

        float creditScore = calculateCreditScore(customer.getCreditModifier(), loanCalculatorRequest.getLoanAmount(), loanCalculatorRequest.getLoanPeriod());
        float maximumSum = calculateMaximumSum(creditScore, loanCalculatorRequest.getLoanAmount());

        return Math.min(maximumSum, MAXIMUM_SUM);
    }

    public float calculateCreditScore(int creditModifier, float loanAmount, int loanPeriod) {
        if (loanAmount == 0) {
            return 0;
        }

        return (creditModifier / loanAmount) * loanPeriod;
    }

    public int calculateMaximumSum(float creditScore, float loanAmount) {
        return (int) (creditScore * loanAmount);
    }

    public List<Customer> mockCustomers() {
        List<Customer> customers = new ArrayList<>();
        Customer customerInDebt = new Customer("49002010965", 300, true);
        Customer customerInSegmentOne = new Customer("49002010976", 100, false);
        Customer customerInSegmentTwo = new Customer("49002010987", 300, false);
        Customer customerInSegmentThree = new Customer("49002010998", 1000, false);

        customers.add(customerInDebt);
        customers.add(customerInSegmentOne);
        customers.add(customerInSegmentTwo);
        customers.add(customerInSegmentThree);

        return customers;
    }
}
