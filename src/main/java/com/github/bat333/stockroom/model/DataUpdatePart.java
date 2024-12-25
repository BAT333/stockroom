package com.github.bat333.stockroom.model;

import jakarta.validation.constraints.Positive;

public record DataUpdatePart(
        Long cod,
        String name,
        byte[] image,
        @Positive
        double amount,
        @Positive
        Long sector
) {
}
