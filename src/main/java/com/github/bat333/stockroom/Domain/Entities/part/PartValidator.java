package com.github.bat333.stockroom.Domain.Entities.part;

import com.github.bat333.stockroom.Domain.Entities.sector.Sector;

public class PartValidator {
    public static void validate(Long id, Long cod, String name, byte[] image, double amount, Sector sector) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        if (cod == null) {
            throw new IllegalArgumentException("Cod cannot be null");
        }
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }
        if (image == null || image.length == 0) {
            throw new IllegalArgumentException("Image cannot be empty");
        }
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero");
        }
        if (sector == null) {
            throw new IllegalArgumentException("Sector cannot be null");
        }
    }
    public static void validate( Long cod, String name, byte[] image, double amount) {

        if (cod == null) {
            throw new IllegalArgumentException("Cod cannot be null");
        }
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }
        if (image == null || image.length == 0) {
            throw new IllegalArgumentException("Image cannot be empty");
        }
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero");
        }

    }
}
