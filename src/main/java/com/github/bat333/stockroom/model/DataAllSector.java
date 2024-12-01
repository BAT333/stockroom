package com.github.bat333.stockroom.model;

import com.github.bat333.stockroom.domain.Part;
import com.github.bat333.stockroom.domain.Sector;

import java.util.List;

public record DataAllSector(
        Long id,
        String shelf,
        String column,
        String row


) {

    public DataAllSector(Sector sector) {
        this(sector.getId(), sector.getShelf(), sector.getColumn(), sector.getRow());
    }
}
