package com.github.bat333.stockroom.Domain.Entities.sector.dto;


import com.github.bat333.stockroom.start.Infra.Persistence.sector.SectorEntity;
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
    public DataSector(SectorEntity sector) {
        this( sector.getSectors(),sector.getShelf(),sector.getColumn(), sector.getRow());
    }
}
