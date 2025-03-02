package com.github.bat333.stockroom.start.Infra.Dto.part;

import com.github.bat333.stockroom.start.Domain.Entities.part.Part;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record DataPart(
        @NotNull
        Long cod,
        @NotNull
        String name,
        @NotNull
        byte[] image,
        @NotNull
        @Positive
        double amount
) {
    public DataPart(Part part) {
        this(part.getCod(),part.getName(), part.getImage(), part.getAmount());
    }
}
