package com.github.bat333.stockroom.model;

import com.github.bat333.stockroom.domain.Sector;

public record DataUpdateSector(
        Long id,
        String shelf,
        String column,
        String row
) {
    public DataUpdateSector(Sector sector) {
        this(sector.getId(), sector.getShelf(), sector.getColumn(), sector.getRow());
    }
}
