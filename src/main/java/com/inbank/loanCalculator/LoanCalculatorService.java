package com.inbank.loanCalculator;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class LoanCalculatorService {
    public Customer calculateMaximumLoanAmount(LoanCalculatorRequest loanCalculatorRequest) throws Exception {
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

        return customer;
    }

    public List<Customer> mockCustomers() {
        List<Customer> customers = new ArrayList<>();
        Customer customerInDebt = new Customer("49002010965", 300, true);
        Customer customerInSegmentOne = new Customer("49002010976", 100, false);
        Customer customerInSegmentTwo = new Customer("49002010987", 300, false);
        Customer customerInSegmentThree = new Customer("49002010987", 1000, false);

        customers.add(customerInDebt);
        customers.add(customerInSegmentOne);
        customers.add(customerInSegmentTwo);
        customers.add(customerInSegmentThree);

        return customers;
    }
}
