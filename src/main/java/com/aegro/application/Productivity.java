package com.aegro.application;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;

@Getter
@Builder
public class Productivity {

    private UUID farmId;
    private BigDecimal farmProductivity;
    private Map<UUID, BigDecimal> fields;
}
