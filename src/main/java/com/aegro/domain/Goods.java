package com.aegro.domain;

import lombok.Getter;
import lombok.Value;
import org.springframework.data.annotation.PersistenceConstructor;

import java.math.BigDecimal;

@Value
@Getter
public class Goods {

    BigDecimal quantity;

    @PersistenceConstructor
    private Goods(BigDecimal quantity) {
        guardIfIsValidQuantity(quantity);

        this.quantity = quantity;
    }

    private void guardIfIsValidQuantity(BigDecimal quantity) {
        if (quantity.compareTo(BigDecimal.ZERO) <= 0)
            throw new IllegalArgumentException("Quantity cannot be less than or equal to zero");
    }

    public static Goods of(BigDecimal quantity) {
        return new Goods(quantity);
    }
}
