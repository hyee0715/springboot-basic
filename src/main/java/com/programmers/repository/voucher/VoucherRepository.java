package com.programmers.repository.voucher;

import com.programmers.domain.voucher.Voucher;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {

    Voucher save(Voucher voucher);

    List<Voucher> findAll();

    Optional<Voucher> findById(UUID id);

    Voucher update(Voucher voucher);

    void deleteById(UUID id);

    void deleteAll();
}
