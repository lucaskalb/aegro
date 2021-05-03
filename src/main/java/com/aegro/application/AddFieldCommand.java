package com.aegro.application;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@AllArgsConstructor(staticName = "of")
public class AddFieldCommand {

    private UUID farmId;
    private String name;
    private BigDecimal area;

}
