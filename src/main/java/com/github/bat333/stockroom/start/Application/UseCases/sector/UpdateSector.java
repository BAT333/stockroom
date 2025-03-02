package com.github.bat333.stockroom.start.Application.UseCases.sector;

import com.github.bat333.stockroom.start.Application.Exception.PartExists;
import com.github.bat333.stockroom.start.Application.Exception.SectorExists;
import com.github.bat333.stockroom.start.Application.Gateways.Sector.RepositorySectorGateways;
import com.github.bat333.stockroom.start.Domain.Entities.sector.Sector;

public class UpdateSector {
    private final RepositorySectorGateways repositorySectorGateways;

    public UpdateSector(RepositorySectorGateways repositorySectorGateways) {
        this.repositorySectorGateways = repositorySectorGateways;
    }

    public Sector updateSector(long id,Sector sector){
        if(!this.repositorySectorGateways.existsSectorAndActive(id)){
            throw new SectorExists("This sector does not exist");
        }
        if(this.repositorySectorGateways.existsBySectorsAndShelfAndColumnAndRow(sector.getSectors(),sector.getShelf(),sector.getColumn(),sector.getRow())){
            throw new PartExists(String.format("Sector with sector '%s' and Shelf '%s' and Column '%s' and Row '%s' already exists.",
                    sector.getSectors(),sector.getShelf(),sector.getColumn(),sector.getRow()));

        }
        return this.repositorySectorGateways.updateSector(id,sector);
    }
}
