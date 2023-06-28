package com.programmers;

import com.programmers.domain.*;
import com.programmers.io.Console;
import com.programmers.service.BlacklistService;
import com.programmers.service.VoucherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.UUID;

@Controller
public class VoucherController {

    private static final Logger log = LoggerFactory.getLogger(VoucherController.class);

    private final Console console;
    private final VoucherService voucherService;
    private final BlacklistService blacklistService;

    public VoucherController(Console console, VoucherService voucherService, BlacklistService blacklistService) {
        this.console = console;
        this.voucherService = voucherService;
        this.blacklistService = blacklistService;
    }

    public void run() {
        boolean activated = true;
        log.info("The voucher program is activated.");

        while (activated) {
            console.printMenu();
            String command = console.readInput();
            Menu menu = Menu.findMenu(command);

            switch (menu) {
                case EXIT -> {
                    activated = false;
                    log.info("The program has been terminated.");
                }
                case CREATE -> createVoucher();
                case LIST -> getVoucherList();
                case BLACKLIST -> getBlacklist();
            }
        }
    }

    public VoucherType getVoucherType() {
        String voucherTypeInput = console.readVoucherName();

        if (isNumeric(voucherTypeInput)) {
            return VoucherType.findVoucherTypeByNumber(voucherTypeInput);
        }

        return VoucherType.findVoucherTypeByName(voucherTypeInput);
    }

    public Voucher createVoucher() {
        Voucher voucher = makeVoucher();
        voucherService.save(voucher);
        console.printVoucherCreated();
        log.info("The voucher has been created.");

        return voucher;
    }

    public Voucher makeVoucher() {
        console.printVoucherType();
        VoucherType voucherType = getVoucherType();

        console.printDiscountValueInput();
        Long discountValue = changeDiscountValueToNumber(console.readInput());

        console.printVoucherNameInput();
        String voucherName = console.readInput();

        if (voucherType == VoucherType.FixedAmountVoucher) {
            return new FixedAmountVoucher(UUID.randomUUID(), voucherName, discountValue);
        }

        return new PercentDiscountVoucher(UUID.randomUUID(), voucherName, discountValue);
    }

    public boolean isNumeric(String str) {
        if (str == null) {
            return false;
        }

        try {
            Integer.parseInt(str);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    public Long changeDiscountValueToNumber(String discountValue) {
        try {
            Long.parseLong(discountValue);
        } catch (NumberFormatException e) {
            log.error("The discount value input is not in numeric format. input value = {}", discountValue);
            throw new IllegalArgumentException();
        }
        return Long.parseLong(discountValue);
    }

    public List<Voucher> getVoucherList() {
        console.printVoucherListTitle();

        return voucherService.findAll();
    }

    private List<String> getBlacklist() {
        console.printBlacklistTitle();
        List<String> blacklist = blacklistService.findAll();
        console.printBlacklist(blacklist);
        log.info("The blacklist has been printed.");

        return blacklist;
    }
}
