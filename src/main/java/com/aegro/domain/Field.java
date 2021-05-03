package com.aegro.domain;


import lombok.Getter;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.index.Indexed;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Getter
public class Field {

    @Indexed
    private UUID id;
    private String name;
    private BigDecimal area;
    private List<Goods> goods;

    @PersistenceConstructor
    private Field(UUID id, BigDecimal area, List<Goods> goods, String name) {
        Objects.requireNonNull(id, "Id cannot be null");
        Objects.requireNonNull(name, "Name cannot be null");
        guardIfIsValidArea(area);

        this.id = id;
        this.area = area;
        this.goods = goods;
        this.name = name;
    }

    private void guardIfIsValidArea(BigDecimal area) {
        Objects.requireNonNull(area, "Area cannot be null");

        if (area.compareTo(BigDecimal.ZERO) <= 0)
            throw new IllegalArgumentException("Area cannot be less than or equal to zero");
    }

    public static Field of(String name, BigDecimal area) {
        return new Field(UUID.randomUUID(), area, new ArrayList<>(), name);
    }

    void addGoods(Goods good) {
        goods.add(good);
    }
}
