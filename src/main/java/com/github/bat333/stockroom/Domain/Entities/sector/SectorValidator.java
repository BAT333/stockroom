package com.github.bat333.stockroom.Domain.Entities.sector;

import com.github.bat333.stockroom.Infrastructure.exception.SectorValidation;

public class SectorValidator {

    public static void validate( String sectors, String shelf, String column, String row) {
        if (sectors == null || sectors.trim().isEmpty()) {
            throw new SectorValidation("sectors cannot be empty");
        }
        if (shelf == null || shelf.trim().isEmpty()) {
            throw new SectorValidation("shelf cannot be empty");
        }
        if (column == null || column.trim().isEmpty()) {
            throw new SectorValidation("column cannot be empty");
        }
        if (row == null || row.trim().isEmpty()) {
            throw new SectorValidation("row cannot be empty");
        }

    }
}
