package com.github.bat333.stockroom.model;

import com.github.bat333.stockroom.domain.Part;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record DataPart(
        @NotNull
        String name,
        @NotNull
        byte[] image,
        @NotNull
        @Positive
        double amount
) {
    public DataPart(Part part) {
        this(part.getName(), part.getImage(), part.getAmount());
    }
}
