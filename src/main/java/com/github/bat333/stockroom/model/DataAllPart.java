package com.github.bat333.stockroom.model;

import com.github.bat333.stockroom.domain.Part;
import com.github.bat333.stockroom.domain.Sector;

public record DataAllPart(
        Long id,
        String name,
        byte[] imageData,
        double amount,
        Sector sector
) {
    public DataAllPart(Part part) {
        this(part.getId(),part.getName(),part.getImage(),part.getAmount(),part.getSector());
    }
}