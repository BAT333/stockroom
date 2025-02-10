package com.github.bat333.stockroom.Domain.Entities.sector;

public class SectorValidator {

    public static void validate(Long id, String sectors, String shelf, String column, String row) {
        if (id == null ) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        if (sectors == null || sectors.trim().isEmpty()) {
            throw new IllegalArgumentException("sectors cannot be empty");
        }
        if (shelf == null || shelf.trim().isEmpty()) {
            throw new IllegalArgumentException("shelf cannot be empty");
        }
        if (column == null || column.trim().isEmpty()) {
            throw new IllegalArgumentException("column cannot be empty");
        }
        if (row == null || row.trim().isEmpty()) {
            throw new IllegalArgumentException("row cannot be empty");
        }

    }
}
