package com.programmers.voucher.service;

import com.programmers.voucher.domain.Voucher;
import com.programmers.voucher.domain.VoucherType;
import com.programmers.voucher.dto.VoucherCreateRequestDto;
import com.programmers.voucher.dto.VoucherResponseDto;
import com.programmers.voucher.dto.VoucherUpdateRequestDto;
import com.programmers.voucher.dto.VouchersResponseDto;
import com.programmers.voucher.repository.VoucherRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class VoucherService {

    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    @Transactional
    public VoucherResponseDto save(VoucherCreateRequestDto voucherCreateRequestDto) {
        Voucher requestVoucher = VoucherType.createVoucher(voucherCreateRequestDto.type().toString(), voucherCreateRequestDto.name(), voucherCreateRequestDto.value());
        Voucher voucher = voucherRepository.save(requestVoucher);

        return new VoucherResponseDto(voucher.getVoucherId(), voucher.getVoucherName(), voucher.getVoucherValue(), voucher.getVoucherType());
    }

    public VouchersResponseDto findAll() {
        List<Voucher> vouchers = voucherRepository.findAll();

        return new VouchersResponseDto(vouchers);
    }

    public VoucherResponseDto findById(UUID id) {
        Voucher voucher = voucherRepository.findById(id).get();

        return new VoucherResponseDto(voucher.getVoucherId(), voucher.getVoucherName(), voucher.getVoucherValue(), voucher.getVoucherType());
    }

    @Transactional
    public VoucherResponseDto update(VoucherUpdateRequestDto voucherDto) {
        Voucher requestVoucher = VoucherType.createVoucher(voucherDto.type().toString(), voucherDto.id(), voucherDto.name(), voucherDto.amount());
        Voucher voucher = voucherRepository.update(requestVoucher);

        return new VoucherResponseDto(voucher.getVoucherId(), voucher.getVoucherName(), voucher.getVoucherValue(), voucher.getVoucherType());
    }

    @Transactional
    public void deleteById(UUID id) {
        voucherRepository.deleteById(id);
    }

    @Transactional
    public void deleteAll() {
        voucherRepository.deleteAll();
    }
}
