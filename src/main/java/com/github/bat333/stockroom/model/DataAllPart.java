package com.github.bat333.stockroom.model;

import com.github.bat333.stockroom.domain.Part;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record DataAllPart(
        Long id,
        String name,
        byte[] imageData,
        double amount
) {
    public DataAllPart(Part part) {
        this(part.getId(),part.getName(),part.getImage(),part.getAmount());
    }
}
