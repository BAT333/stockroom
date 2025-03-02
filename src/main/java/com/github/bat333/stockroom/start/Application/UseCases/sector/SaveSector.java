package com.github.bat333.stockroom.start.Application.UseCases.sector;


import com.github.bat333.stockroom.start.Application.Exception.SectorExists;
import com.github.bat333.stockroom.start.Application.Gateways.Sector.RepositorySectorGateways;
import com.github.bat333.stockroom.start.Domain.Entities.sector.Sector;

public class SaveSector {
    private final RepositorySectorGateways repositorySectorGateways;

    public SaveSector(RepositorySectorGateways repositorySectorGateways) {
        this.repositorySectorGateways = repositorySectorGateways;
    }

    public Sector saveSector(Sector sector){
        if(this.repositorySectorGateways.existsBySectorsAndShelfAndColumnAndRow(sector.getSectors(),sector.getShelf(),sector.getColumn(),sector.getRow())){
            throw new SectorExists(String.format("Sector with sector '%s' and Shelf '%s' and Column '%s' and Row '%s' already exists.",
                    sector.getSectors(),sector.getShelf(),sector.getColumn(),sector.getRow()));
        }
        return this.repositorySectorGateways.saveSector(sector);
    }
}
