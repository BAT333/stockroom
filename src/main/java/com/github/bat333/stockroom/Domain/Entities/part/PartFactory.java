package com.github.bat333.stockroom.Domain.Entities.part;

import com.github.bat333.stockroom.Domain.Entities.sector.Sector;

public class PartFactory {
    public static Part createPart(Long id, Long cod, String name, byte[] image, double amount, boolean active, Sector sector) {
        PartValidator.validate(id, cod, name, image, amount, sector);
        return new Part(id, cod, name, image, amount, active, sector);
    }

    public static Part createPartUpdate( Long cod, String name, byte[] image, double amount) {
        Part part = new Part();
        part.setCod(cod);
        part.setName(name);
        part.setImage(image);
        part.setAmount(amount);
        return part;
    }
}
