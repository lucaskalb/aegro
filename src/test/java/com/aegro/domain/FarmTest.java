package com.aegro.domain;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FarmTest {

    @Test
    void of() {
        var farm = Farm.of("Farm Name");

        assertThat(farm.getId(), notNullValue());
        assertThat(farm.getName(), equalTo("Farm Name"));
        assertThat(farm.getFields(), empty());
    }

    @Test
    void errorWhenNameIsNull() {
        var exception = assertThrows(NullPointerException.class, () -> Farm.of(null));

        assertThat(exception.getMessage(), equalTo("Name cannot be null"));
    }

    @Test
    void addField() {
        var farm = Farm.of("Farm Name");
        var field = Field.of("Campo 1", BigDecimal.ONE);

        farm.addField(field);

        assertThat(farm.getId(), notNullValue());
        assertThat(farm.getName(), equalTo("Farm Name"));
        assertThat(farm.getFields(), hasSize(1));
        assertThat(farm.getFields(), contains(field));
    }

    @Test
    void addGoods() {
        var farm = Farm.of("Farm Name");
        var field = Field.of("Campo 1", BigDecimal.ONE);
        farm.addField(field);
        var goods = Goods.of(BigDecimal.TEN);

        farm.addGoods(goods, field.getId());

        assertThat(farm.getId(), notNullValue());
        assertThat(farm.getName(), equalTo("Farm Name"));
        assertThat(farm.getFields(), hasSize(1));
        assertThat(farm.getFields(), contains(field));
        assertThat(farm.getFields().get(0).getGoods(), hasSize(1));
        assertThat(farm.getFields().get(0).getGoods(), contains(goods));
    }
}
