package com.programmers.voucher.controller;

import static com.programmers.util.ValueFormatter.changeDiscountValueToNumber;
import static com.programmers.util.ValueFormatter.reformatVoucherType;

import com.programmers.voucher.domain.Voucher;
import com.programmers.voucher.domain.VoucherType;
import com.programmers.voucher.dto.VoucherCreateRequestDto;
import com.programmers.voucher.dto.VoucherResponseDto;
import com.programmers.voucher.dto.VoucherUpdateRequestDto;
import com.programmers.voucher.dto.VouchersResponseDto;
import com.programmers.exception.EmptyException;
import com.programmers.exception.InvalidInputException;
import com.programmers.io.Console;
import com.programmers.voucher.service.VoucherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.UUID;

@Controller
public class VoucherController {

    private static final Logger log = LoggerFactory.getLogger(VoucherController.class);

    private static final String DELETE_ONE_VOUCHER_NUMBER = "1";
    private static final String DELETE_ALL_VOUCHERS_NUMBER = "2";

    private final Console console;
    private final VoucherService voucherService;

    public VoucherController(Console console, VoucherService voucherService) {
        this.console = console;
        this.voucherService = voucherService;
    }

    public Voucher createVoucher() {
        Voucher voucher = makeVoucher();
        VoucherCreateRequestDto voucherCreateRequestDto = new VoucherCreateRequestDto(voucher.getVoucherName(), voucher.getVoucherValue(), voucher.getVoucherType());
        voucherService.save(voucherCreateRequestDto);
        console.printVoucherCreated();
        log.info("The voucher has been created.");

        return voucher;
    }

    public Voucher makeVoucher() {
        console.printVoucherType();
        String voucherTypeInput = reformatVoucherType(console.readInput());

        console.printDiscountValueInput();
        Long discountValue = changeDiscountValueToNumber(console.readInput());

        console.printVoucherNameInput();
        String voucherName = console.readInput();

        return VoucherType.createVoucher(voucherTypeInput, voucherName, discountValue);
    }

    public List<Voucher> getVoucherList() {
        console.printVoucherListTitle();
        VouchersResponseDto vouchersResponseDto = voucherService.findAll();

        List<Voucher> vouchers = vouchersResponseDto.vouchers();
        if (vouchers.isEmpty()) {
            console.printVoucherListEmptyMessage();
            return vouchers;
        }

        console.printVouchers(vouchers);
        log.info("The voucher list has been printed.");
        return vouchers;
    }

    public void updateVoucher() {
        if (getVoucherList().isEmpty()) {
            return;
        }

        Voucher originalVoucher = getVoucherToUpdate();
        VoucherUpdateRequestDto voucherUpdateRequestDto = makeVoucherRequestDtoToUpdate(originalVoucher);

        voucherService.update(voucherUpdateRequestDto);
        console.printUpdateVoucherCompleteMessage();
        log.info("The voucher has been updated.");
    }

    public Voucher getVoucherToUpdate() {
        console.printUpdateVoucherIdMessage();
        UUID updateVoucherId = UUID.fromString(console.readInput());

        VoucherResponseDto voucherResponseDto = voucherService.findById(updateVoucherId);

        return VoucherType.createVoucher(voucherResponseDto.type().toString(), voucherResponseDto.id(), voucherResponseDto.name(), voucherResponseDto.value());
    }

    private VoucherUpdateRequestDto makeVoucherRequestDtoToUpdate(Voucher voucher) {
        console.printUpdateNewVoucherValueMessage();
        Long updateVoucherValue = changeDiscountValueToNumber(console.readInput());

        console.printUpdateNewVoucherNameMessage();
        String updateVoucherName = console.readInput();

        return new VoucherUpdateRequestDto(voucher.getVoucherId(), updateVoucherName, updateVoucherValue, voucher.getVoucherType());
    }

    public void deleteVoucher() {
        if (getVoucherList().isEmpty()) {
            return;
        }

        console.printDeleteTypeVoucherSelectionMessage();
        String command = console.readInput();
        checkDeleteTypeSelection(command);

        switch (command) {
            case DELETE_ONE_VOUCHER_NUMBER -> deleteOneVoucher();
            case DELETE_ALL_VOUCHERS_NUMBER -> deleteAllVouchers();
        }
    }

    private void checkDeleteTypeSelection(String input) {
        if (input.isEmpty()) {
            throw new EmptyException("[ERROR] Delete Type 번호가 입력되지 않았습니다.");
        }

        if (!input.equals(DELETE_ONE_VOUCHER_NUMBER) && !input.equals(DELETE_ALL_VOUCHERS_NUMBER)) {
            throw new InvalidInputException("[ERROR] 입력하신 Delete Type 번호가 유효하지 않습니다.");
        }
    }

    public void deleteOneVoucher() {
        console.printDeleteVoucherIdMessage();
        UUID deleteVoucherId = UUID.fromString(console.readInput());

        voucherService.deleteById(deleteVoucherId);
        console.printDeleteVoucherCompleteMessage();
        log.info("The voucher has been deleted.");
    }

    public void deleteAllVouchers() {
        voucherService.deleteAll();
        console.printDeleteAllVouchersCompleteMessage();
        log.info("All vouchers have been deleted.");
    }
}