package com.programmers.springbootbasic.wallet.controller;

import com.programmers.springbootbasic.customer.controller.CustomerController;
import com.programmers.springbootbasic.customer.domain.Customer;
import com.programmers.springbootbasic.customer.dto.CustomerResponseDto;
import com.programmers.springbootbasic.exception.InvalidRequestValueException;
import com.programmers.springbootbasic.io.Console;
import com.programmers.springbootbasic.voucher.controller.VoucherController;
import com.programmers.springbootbasic.voucher.domain.Voucher;
import com.programmers.springbootbasic.voucher.dto.VouchersResponseDto;
import com.programmers.springbootbasic.wallet.domain.WalletMenu;
import com.programmers.springbootbasic.wallet.dto.WalletDto;
import com.programmers.springbootbasic.wallet.service.WalletService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.UUID;

@Controller
public class WalletController {

    private static final Logger log = LoggerFactory.getLogger(WalletController.class);

    private static final String DELETE_ONE_VOUCHER_NUMBER = "1";
    private static final String DELETE_ALL_VOUCHERS_NUMBER = "2";

    private final Console console;
    private final VoucherController voucherController;
    private final CustomerController customerController;
    private final WalletService walletService;

    public WalletController(Console console, VoucherController voucherController, CustomerController customerController, WalletService walletService) {
        this.console = console;
        this.voucherController = voucherController;
        this.customerController = customerController;
        this.walletService = walletService;
    }

    public void activate() {
        console.printWalletMessage();
        String command = console.readInput();
        WalletMenu walletMenu = WalletMenu.findWalletMenu(command);

        switch (walletMenu) {
            case ASSIGN_VOUCHER -> assignVoucher();
            case SEARCH_CUSTOMER -> searchCustomerToGetVouchers();
            case SEARCH_VOUCHER -> searchVoucherToGetCustomer();
            case DELETE_VOUCHER -> deleteVoucher();
        }
    }

    public void assignVoucher() {
        console.printWalletAssignTitleMessage();
        voucherController.getVoucherList();
        customerController.getNormalCustomerList();

        WalletDto walletDto = makeWalletDto();
        walletService.updateVoucherCustomerId(walletDto);

        console.printWalletAssignCompleteMessage();
        log.info("The voucher assigned successfully. voucher id = {}, customer id = {}", walletDto.voucherId(), walletDto.customerId());
    }

    private WalletDto makeWalletDto() {
        console.printWalletAssignVoucherIdMessage();
        UUID voucherId = UUID.fromString(console.readInput());

        console.printWalletAssignCustomerIdMessage();
        UUID customerId = UUID.fromString(console.readInput());

        return new WalletDto(voucherId, customerId);
    }

    public List<Voucher> searchCustomerToGetVouchers() {
        console.printWalletSearchCustomerTitleMessage();
        customerController.getNormalCustomerList();

        console.printWalletSearchCustomerIdMessage();
        UUID customerId = UUID.fromString(console.readInput());
        VouchersResponseDto vouchersResponseDto = walletService.findVouchersByCustomerId(customerId);

        console.printVoucherListTitle();
        return voucherController.getVouchersContent(vouchersResponseDto.vouchers());
    }

    public void searchVoucherToGetCustomer() {
        console.printWalletSearchVoucherTitleMessage();
        voucherController.getVoucherList();

        console.printWalletSearchVoucherIdMessage();
        UUID voucherId = UUID.fromString(console.readInput());

        CustomerResponseDto customerResponseDto = walletService.findCustomerByVoucherId(voucherId);
        getCustomerContent(customerResponseDto);
    }

    public void getCustomerContent(CustomerResponseDto customerResponseDto) {
        if (customerResponseDto.id() == null && customerResponseDto.name() == null && customerResponseDto.type() == null) {
            console.printNormalCustomerListEmptyMessage();
            return;
        }

        Customer customer = new Customer(customerResponseDto.id(), customerResponseDto.name());
        console.printCustomer(customer);
    }

    public void deleteVoucher() {
        console.printWalletDeleteVoucherTitleMessage();
        UUID customerId = getCustomerIdToDeleteVoucher();
        VouchersResponseDto vouchersResponseDto = walletService.findVouchersByCustomerId(customerId);

        console.printVoucherListTitle();
        List<Voucher> vouchers = voucherController.getVouchersContent(vouchersResponseDto.vouchers());
        if (vouchers.isEmpty()) {
            return;
        }

        selectDeleteType(customerId);
    }

    public UUID getCustomerIdToDeleteVoucher() {
        customerController.getNormalCustomerList();

        console.printWalletDeleteCustomerIdMessage();
        return UUID.fromString(console.readInput());
    }

    public void selectDeleteType(UUID customerId) {
        console.printDeleteTypeVoucherSelectionMessage();
        String command = console.readInput();
        checkDeleteTypeSelection(command);

        switch (command) {
            case DELETE_ONE_VOUCHER_NUMBER -> deleteOneVoucher(customerId);
            case DELETE_ALL_VOUCHERS_NUMBER -> deleteAllVouchers(customerId);
        }
    }

    private void checkDeleteTypeSelection(String deleteTypeRequest) {
        if (deleteTypeRequest.isEmpty()) {
            throw new InvalidRequestValueException("[ERROR] Delete Type 번호 요청 값이 비었습니다.");
        }

        if (!deleteTypeRequest.equals(DELETE_ONE_VOUCHER_NUMBER) && !deleteTypeRequest.equals(DELETE_ALL_VOUCHERS_NUMBER)) {
            throw new InvalidRequestValueException("[ERROR] 요청하신 Delete Type 번호가 유효하지 않습니다.");
        }
    }

    public void deleteOneVoucher(UUID customerId) {
        console.printDeleteVoucherIdMessage();
        UUID voucherId = UUID.fromString(console.readInput());

        WalletDto walletDto = new WalletDto(voucherId, customerId);
        walletService.deleteVoucherByVoucherIdAndCustomerId(walletDto);
        console.printDeleteVoucherCompleteMessage();
        log.info("The voucher of one customer has been deleted. customer id = {}", customerId);
    }

    public void deleteAllVouchers(UUID customerId) {
        walletService.deleteAllVouchersByCustomerId(customerId);
        console.printDeleteAllVouchersCompleteMessage();
        log.info("All vouchers of one customer have been deleted. customer id = {}", customerId);
    }
}