package com.github.bat333.stockroom.model;

import com.github.bat333.stockroom.domain.Sector;
import jakarta.validation.constraints.NotNull;

public record DataSector(
        @NotNull
        String sector,
        @NotNull
        String shelf,
        @NotNull
        String column,
        @NotNull
        String row

) {
    public DataSector(Sector sector) {
        this( sector.getSectors(),sector.getShelf(),sector.getColumn(), sector.getRow());
    }
}
