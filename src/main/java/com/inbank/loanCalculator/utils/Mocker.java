package com.inbank.loanCalculator.utils;

import com.inbank.loanCalculator.Customer;

import java.util.Arrays;
import java.util.List;

public class Mocker {
    public static List<Customer> mockCustomers() {
        Customer customerInDebt = new Customer("49002010965", 300, true);
        Customer customerInSegmentOne = new Customer("49002010976", 100, false);
        Customer customerInSegmentTwo = new Customer("49002010987", 300, false);
        Customer customerInSegmentThree = new Customer("49002010998", 1000, false);

        return Arrays.asList(customerInDebt, customerInSegmentOne, customerInSegmentTwo, customerInSegmentThree);
    }
}
