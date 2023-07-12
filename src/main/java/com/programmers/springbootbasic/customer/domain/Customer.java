package com.programmers.springbootbasic.customer.domain;

import com.programmers.springbootbasic.exception.InvalidCustomerValueException;
import com.programmers.springbootbasic.exception.InvalidRequestValueException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

public class Customer {

    private static final Logger log = LoggerFactory.getLogger(Customer.class);

    private final UUID customerId;
    private final String customerName;
    private final CustomerType customerType;

    public Customer(UUID customerId, String customerName) {
        checkCustomerName(customerName);

        this.customerId = customerId;
        this.customerName = customerName;
        this.customerType = CustomerType.NORMAL;
    }

    public Customer(String customerName) {
        checkCustomerName(customerName);

        this.customerId = UUID.randomUUID();
        this.customerName = customerName;
        this.customerType = CustomerType.NORMAL;
    }

    private void checkCustomerName(String customerName) {
        if (customerName.isEmpty()) {
            log.error("The customer name not found.");
            throw new InvalidRequestValueException("[ERROR] 회원 이름이 입력되지 않았습니다.");

        }

        if (customerName.length() > 30) {
            log.error("The invalid customer name found.");
            throw new InvalidCustomerValueException("[ERROR] 회원 이름은 30글자 이하여야 합니다.");
        }
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public CustomerType getCustomerType() {
        return customerType;
    }

    @Override
    public String toString() {
        return "[ Customer Id = " + customerId +
                ", customer name = " + customerName +
                ", type = " + customerType + " ]";
    }
}