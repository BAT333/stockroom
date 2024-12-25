package com.github.bat333.stockroom.model;

import com.github.bat333.stockroom.domain.Part;

import java.util.Base64;

public record DataAllPart(
        Long id,
        String name,
        String image,
        double amount,
        DataSector sector
) {
    public DataAllPart(Part part) {
        this(part.getId(),part.getName(), Base64.getEncoder().encodeToString(part.getImage()),part.getAmount(),new DataSector(part.getSector()));
    }
}
