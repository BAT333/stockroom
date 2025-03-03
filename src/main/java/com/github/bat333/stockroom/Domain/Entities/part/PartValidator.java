package com.github.bat333.stockroom.Domain.Entities.part;

import com.github.bat333.stockroom.Domain.Entities.sector.Sector;
import com.github.bat333.stockroom.Domain.Exception.PartValidation;

public class PartValidator {
    public static void validate(Long id, Long cod, String name, byte[] image, double amount, Sector sector) {
        if (id == null) {
            throw new PartValidation("ID cannot be null");
        }
        if (cod == null) {
            throw new PartValidation("Cod cannot be null");
        }
        if (name == null || name.trim().isEmpty()) {
            throw new PartValidation("Name cannot be empty");
        }
        if (image == null || image.length == 0) {
            throw new PartValidation("Image cannot be empty");
        }
        if (amount <= 0) {
            throw new PartValidation("Amount must be greater than zero");
        }
        if (sector == null) {
            throw new PartValidation("Sector cannot be null");
        }
    }
    public static void validate( Long cod, String name, byte[] image, double amount) {

        if (cod == null) {
            throw new PartValidation("Cod cannot be null");
        }
        if (name == null || name.trim().isEmpty()) {
            throw new PartValidation("Name cannot be empty");
        }
        if (image == null || image.length == 0) {
            throw new PartValidation("Image cannot be empty");
        }
        if (amount <= 0) {
            throw new PartValidation("Amount must be greater than zero");
        }

    }
}
