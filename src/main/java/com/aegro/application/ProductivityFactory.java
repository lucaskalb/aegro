package com.aegro.application;

import com.aegro.domain.Farm;
import com.aegro.domain.Field;
import com.aegro.domain.Goods;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@AllArgsConstructor(staticName = "of")
class ProductivityFactory {

    private final Farm farm;

    Productivity build() {
        return Productivity.builder()
                .fields(calculateFields())
                .farmProductivity(calculateFarm())
                .farmId(farm.getId())
                .build();
    }

    private BigDecimal calculateFarm() {
        var totalArea = farm.getFields().stream()
                .map(Field::getArea)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        var totalGoods = farm.getFields().stream()
                .flatMap(f -> f.getGoods().stream())
                .map(Goods::getQuantity)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return totalGoods.divide(totalArea, 2, RoundingMode.HALF_EVEN);
    }

    private Map<UUID, BigDecimal> calculateFields() {
        Map<UUID, BigDecimal> fields = new HashMap<>();

        for (Field field : farm.getFields()) {
            BigDecimal productivity = field.getGoods().stream()
                    .map(Goods::getQuantity)
                    .reduce(BigDecimal.ZERO, BigDecimal::add)
                    .divide(field.getArea(), 2, RoundingMode.HALF_EVEN);

            fields.put(field.getId(), productivity);
        }
        return fields;
    }

}
