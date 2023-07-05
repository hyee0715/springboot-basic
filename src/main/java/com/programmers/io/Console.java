package com.programmers.io;

import com.programmers.domain.customer.Customer;
import com.programmers.domain.customer.dto.CustomersResponseDto;
import com.programmers.domain.voucher.Voucher;
import com.programmers.domain.voucher.dto.VouchersResponseDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;

@Component
public class Console implements Input, Output {

    private static final String MENU_MESSAGE = "=== Voucher Program ===\n" +
            "Type 'exit' or '1' to exit the program.\n" +
            "Type 'create' or '2' to create a new voucher or a new customer.\n" +
            "Type 'list' or '3' to list all vouchers or all customers.\n" +
            "Type 'update' or '4' to update a voucher or a customer.\n" +
            "Type 'delete' or '5' to delete a voucher or a customer.";
    private static final String VOUCHER_TYPE_MESSAGE = "\n=== Voucher Type ===\n" +
            "Type voucher name or number to create.\n" +
            "1. Fixed Amount Voucher\n" +
            "2. Percent Discount Voucher";
    private static final String DISCOUNT_VALUE_MESSAGE = "\n=== Type discount amount or rate ===";
    private static final String VOUCHER_NAME_MESSAGE = "\n=== Type voucher name ===";
    private static final String VOUCHER_CREATED_MESSAGE = "--- Voucher Created !! ---\n";
    private static final String VOUCHER_LIST_TITLE_MESSAGE = "\n=== Voucher List ===";
    private static final String VOUCHER_LIST_EMPTY_MESSAGE = "\n(There is no voucher.)\n";
    private static final String BLACKLIST_MESSAGE = "\n=== Blacklist ===";
    private static final String CREATE_MESSAGE = "\n=== Create ===\n" +
            "Type '1' or '2' to create item.\n" +
            "1. voucher\n" +
            "2. customer";
    private static final String CUSTOMER_NAME_MESSAGE = "\n=== Type customer name ===";
    private static final String CUSTOMER_CREATED_MESSAGE = "--- Customer Created !! ---\n";
    private static final String LIST_MESSAGE = "\n=== List ===\n" +
            "Type '1' or '2' to list items.\n" +
            "1. voucher\n" +
            "2. customer";
    private static final String CUSTOMER_LIST_MESSAGE = "\n=== Customer List ===\n" +
            "Type '1' or '2' to choose Customer Type.\n" +
            "1. Normal Customer\n" +
            "2. Blacklist";
    private static final String NORMAL_CUSTOMER_LIST_TITLE_MESSAGE = "\n=== Normal Customer List ===";
    private static final String UPDATE_MESSAGE = "\n=== Update ===\n" +
            "Type '1' or '2' to update item.\n" +
            "1. voucher\n" +
            "2. normal customer";
    private static final String UPDATE_VOUCHER_ID_MESSAGE = "=== Type voucher id to update ===";
    private static final String UPDATE_NEW_VOUCHER_NAME_MESSAGE = "\n=== Type new voucher name ===";
    private static final String UPDATE_NEW_VOUCHER_VALUE_MESSAGE = "\n=== Type new voucher value ===";
    private static final String UPDATE_VOUCHER_COMPLETE_MESSAGE = "--- The voucher updated successfully !! ---\n";

    private static final String UPDATE_CUSTOMER_ID_MESSAGE = "=== Type customer id to update ===";
    private static final String UPDATE_NEW_CUSTOMER_NAME_MESSAGE = "\n=== Type new customer name ===";
    private static final String UPDATE_CUSTOMER_COMPLETE_MESSAGE = "--- The customer updated successfully !! ---\n";

    private static final String DELETE_MESSAGE = "\n=== Delete ===\n" +
            "Type '1' or '2' to delete item.\n" +
            "1. voucher\n" +
            "2. normal customer";
    private static final String DELETE_VOUCHER_ID_MESSAGE = "=== Type voucher id to delete ===";
    private static final String DELETE_TYPE_VOUCHER_SELECTION_MESSAGE = "\n=== Delete ===\n" +
            "Type '1' or '2' to delete type.\n" +
            "1. Delete one voucher.\n" +
            "2. Delete all vouchers.";
    private static final String DELETE_VOUCHER_COMPLETE_MESSAGE = "--- The voucher deleted successfully !! ---\n";
    private static final String DELETE_ALL_VOUCHERS_COMPLETE_MESSAGE = "--- All vouchers deleted successfully !! ---\n";

    private static final String DELETE_CUSTOMER_ID_MESSAGE = "=== Type customer id to delete ===";
    private static final String DELETE_TYPE_CUSTOMER_SELECTION_MESSAGE = "\n=== Delete ===\n" +
            "Type '1' or '2' to delete type.\n" +
            "1. Delete one customer.\n" +
            "2. Delete all customers.";
    private static final String DELETE_CUSTOMER_COMPLETE_MESSAGE = "--- The customer deleted successfully !! ---\n";
    private static final String DELETE_ALL_CUSTOMERS_COMPLETE_MESSAGE = "--- All customerss deleted successfully !! ---\n";


    @Override
    public void printMenu() {
        System.out.println(MENU_MESSAGE);
    }

    @Override
    public String readInput() {
        return new Scanner(System.in).nextLine();
    }

    @Override
    public void printVoucherType() {
        System.out.println(VOUCHER_TYPE_MESSAGE);
    }

    @Override
    public void printDiscountValueInput() {
        System.out.println(DISCOUNT_VALUE_MESSAGE);
    }

    @Override
    public void printVoucherNameInput() {
        System.out.println(VOUCHER_NAME_MESSAGE);
    }

    public void printVoucherCreated() {
        System.out.println(VOUCHER_CREATED_MESSAGE);
    }

    @Override
    public void printVoucherListTitle() {
        System.out.println(VOUCHER_LIST_TITLE_MESSAGE);
    }

    public void printVoucherListEmptyMessage() {
        System.out.println(VOUCHER_LIST_EMPTY_MESSAGE);
    }

    public void printVouchers(List<Voucher> vouchers) {
        vouchers.forEach(System.out::println);
        System.out.println();
    }

    public void printBlacklistTitle() {
        System.out.println(BLACKLIST_MESSAGE);
    }

    public void printBlacklist(List<String> blacklist) {
        blacklist.forEach(System.out::println);

        System.out.println();
    }

    public void printCreateMessage() {
        System.out.println(CREATE_MESSAGE);
    }

    public void printCustomerNameInput() {
        System.out.println(CUSTOMER_NAME_MESSAGE);
    }

    public void printCustomerCreated() {
        System.out.println(CUSTOMER_CREATED_MESSAGE);
    }

    public void printListMessage() {
        System.out.println(LIST_MESSAGE);
    }

    public void printCustomerListMessage() {
        System.out.println(CUSTOMER_LIST_MESSAGE);
    }

    public void printNormalCustomerListTitle() {
        System.out.println(NORMAL_CUSTOMER_LIST_TITLE_MESSAGE);
    }

    public void printCustomers(CustomersResponseDto customersResponseDto) {
        List<Customer> customers = customersResponseDto.customers();
        customers.forEach(System.out::println);
        System.out.println();
    }

    public void printUpdateMessage() {
        System.out.println(UPDATE_MESSAGE);
    }

    public void printUpdateVoucherIdMessage() {
        System.out.println(UPDATE_VOUCHER_ID_MESSAGE);
    }

    public void printUpdateNewVoucherValueMessage() {
        System.out.println(UPDATE_NEW_VOUCHER_VALUE_MESSAGE);
    }

    public void printUpdateNewVoucherNameMessage() {
        System.out.println(UPDATE_NEW_VOUCHER_NAME_MESSAGE);
    }

    public void printUpdateVoucherCompleteMessage() {
        System.out.println(UPDATE_VOUCHER_COMPLETE_MESSAGE);
    }

    public void printUpdateCustomerIdMessage() {
        System.out.println(UPDATE_CUSTOMER_ID_MESSAGE);
    }

    public void printUpdateNewCustomerNameMessage() {
        System.out.println(UPDATE_NEW_CUSTOMER_NAME_MESSAGE);
    }

    public void printUpdateCustomerCompleteMessage() {
        System.out.println(UPDATE_CUSTOMER_COMPLETE_MESSAGE);
    }

    public void printDeleteMessage() {
        System.out.println(DELETE_MESSAGE);
    }

    public void printDeleteVoucherIdMessage() {
        System.out.println(DELETE_VOUCHER_ID_MESSAGE);
    }

    public void printDeleteTypeVoucherSelectionMessage() {
        System.out.println(DELETE_TYPE_VOUCHER_SELECTION_MESSAGE);
    }

    public void printDeleteVoucherCompleteMessage() {
        System.out.println(DELETE_VOUCHER_COMPLETE_MESSAGE);
    }

    public void printDeleteAllVouchersCompleteMessage() {
        System.out.println(DELETE_ALL_VOUCHERS_COMPLETE_MESSAGE);
    }

    public void printDeleteCustomerIdMessage() {
        System.out.println(DELETE_CUSTOMER_ID_MESSAGE);
    }

    public void printDeleteTypeCustomerSelectionMessage() {
        System.out.println(DELETE_TYPE_CUSTOMER_SELECTION_MESSAGE);
    }

    public void printDeleteCustomerCompleteMessage() {
        System.out.println(DELETE_CUSTOMER_COMPLETE_MESSAGE);
    }

    public void printDeleteAllCustomersCompleteMessage() {
        System.out.println(DELETE_ALL_CUSTOMERS_COMPLETE_MESSAGE);
    }
}
