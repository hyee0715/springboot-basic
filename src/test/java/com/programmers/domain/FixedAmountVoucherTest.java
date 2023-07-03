package com.programmers.domain;

import com.programmers.domain.voucher.FixedAmountVoucher;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

class FixedAmountVoucherTest {

    @DisplayName("FixedAmountVoucher 객체를 생성한다")
    @Test
    void FixedAmountVoucher() {
        //given
        //when
        FixedAmountVoucher result = new FixedAmountVoucher(UUID.randomUUID(), "name", 1L);

        //then
        Assertions.assertThat(result).isInstanceOf(FixedAmountVoucher.class);
    }

    @DisplayName("FixedAmountVoucher 생성자에 이름이 입력되지 않은 경우 예외처리한다")
    @Test
    void FixedAmountVoucherWithEmptyName() {
        //given
        //when
        //then
        Assertions.assertThatThrownBy(() -> new FixedAmountVoucher(UUID.randomUUID(), "", 1L))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("FixedAmountVoucher 생성자의 amount 값이 0보다 작으면 예외처리한다")
    @Test
    void FixedAmountVoucherWithWrongAmount() {
        //given
        //when
        //then
        Assertions.assertThatThrownBy(() -> new FixedAmountVoucher(UUID.randomUUID(), "", -1L))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("할인이 적용된 최종 값을 구한다")
    @Test
    void discount() {
        //given
        FixedAmountVoucher fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), "testVoucher", 20L);
        long originalAmount = 100L;

        //when
        long result = fixedAmountVoucher.discount(originalAmount);

        //then
        Assertions.assertThat(result).isEqualTo(80L);
    }
}