package com.github.bat333.stockroom.Infra.Dto.sector;


import com.github.bat333.stockroom.Infra.Persistence.sector.SectorEntity;



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
