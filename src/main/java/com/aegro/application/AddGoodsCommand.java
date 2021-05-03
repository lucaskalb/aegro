package com.aegro.application;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@AllArgsConstructor(staticName = "of")
public class AddGoodsCommand {

    private UUID farmId;
    private UUID fieldId;
    private BigDecimal amount;
}
