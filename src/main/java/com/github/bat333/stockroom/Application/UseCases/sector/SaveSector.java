package com.github.bat333.stockroom.Application.UseCases.sector;

import com.github.bat333.stockroom.Application.Gateways.Sector.RepositorySector;
import com.github.bat333.stockroom.Domain.Entities.sector.Sector;

public class SaveSector {
    private final RepositorySector repositorySector;

    public SaveSector(RepositorySector repositorySector) {
        this.repositorySector = repositorySector;
    }

    public Sector saveSector(Sector sector){
        if(this.repositorySector.existsBySectorsAndShelfAndColumnAndRow(sector.getSectors(),sector.getShelf(),sector.getColumn(),sector.getRow())){
            throw new RuntimeException();
        }
        return this.repositorySector.saveSector(sector);
    }
}
