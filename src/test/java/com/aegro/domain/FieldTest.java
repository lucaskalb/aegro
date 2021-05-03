package com.aegro.domain;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FieldTest {

    @Test
    void of() {
        var field = Field.of("Field Name", BigDecimal.TEN);

        assertThat(field.getId(), notNullValue());
        assertThat(field.getName(), equalTo("Field Name"));

        assertThat(field.getGoods(), empty());
    }

    @Test
    void errorWhenAreaEqualZero() {
        var exception = assertThrows(IllegalArgumentException.class, () -> Field.of("Name", BigDecimal.ZERO));

        assertThat(exception.getMessage(), equalTo("Area cannot be less than or equal to zero"));
    }

    @Test
    void errorWhenAreaLessThan() {
        var exception = assertThrows(IllegalArgumentException.class, () -> Field.of("Name", BigDecimal.valueOf(-0.01)));

        assertThat(exception.getMessage(), equalTo("Area cannot be less than or equal to zero"));
    }
}
