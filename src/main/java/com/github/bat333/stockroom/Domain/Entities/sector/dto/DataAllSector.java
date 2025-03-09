package com.github.bat333.stockroom.Domain.Entities.sector.dto;


import com.github.bat333.stockroom.Adapters.outbound.entities.sector.SectorEntity;

public record DataAllSector(
        Long id,
        String sector,
        String shelf,
        String column,
        String row


) {

   public DataAllSector(SectorEntity sector) {
       this(sector.getId(), sector.getSectors(),sector.getShelf(), sector.getColumn(), sector.getRow());
   }
}
