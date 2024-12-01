package com.github.bat333.stockroom.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record DataUpdatePart(
        String name,
        byte[] imageData,
        @Positive
        double amount,
        @Positive
        Long idSector
) {
}
