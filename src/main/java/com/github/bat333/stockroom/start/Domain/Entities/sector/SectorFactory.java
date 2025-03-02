package com.github.bat333.stockroom.start.Domain.Entities.sector;

import com.github.bat333.stockroom.start.Domain.Entities.part.Part;

import java.util.List;

public class SectorFactory{
    public Sector createSector(Long id, String sectors, String shelf, String column, String row, boolean active, List<Part> parts){
        SectorValidator.validate(sectors,shelf,column,row);
        return new Sector(id,sectors,shelf,column,row,active,parts);
    }
}
