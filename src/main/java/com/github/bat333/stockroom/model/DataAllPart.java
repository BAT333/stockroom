package com.github.bat333.stockroom.model;

import com.github.bat333.stockroom.domain.Part;
import com.github.bat333.stockroom.domain.Sector;

import java.util.Base64;

public record DataAllPart(
        Long id,
        String name,
        byte[] imageData,
        double amount,
        String typyImg,
        DataUpdateSector sector
) {
    public DataAllPart(Part part) {
        this(part.getId(),part.getName(),part.getImage(),part.getAmount(),part.getImageType(),new DataUpdateSector(part.getSector()));
    }
    public String getImageDataUri() {
        if (imageData != null && typyImg != null) {
            return "data:" + typyImg + ";base64," + Base64.getEncoder().encodeToString(imageData);
        }
        return null;
    }
}
