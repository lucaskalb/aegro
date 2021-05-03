package com.aegro.domain;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;

class GoodsTest {

    @Test
    void of() {
        var goods = Goods.of(BigDecimal.TEN);

        assertThat(goods.getQuantity(), equalTo(BigDecimal.TEN));
    }


    @Test
    void errorWhenQuantityEqualToZero() {
        var exception = assertThrows(IllegalArgumentException.class, () -> Goods.of(BigDecimal.ZERO));

        assertThat(exception.getMessage(), equalTo("Quantity cannot be less than or equal to zero"));
    }
}
