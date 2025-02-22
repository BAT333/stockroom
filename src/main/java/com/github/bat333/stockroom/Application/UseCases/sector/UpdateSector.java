package com.github.bat333.stockroom.Application.UseCases.sector;

import com.github.bat333.stockroom.Application.Gateways.Sector.RepositorySectorGateways;
import com.github.bat333.stockroom.Domain.Entities.sector.Sector;

public class UpdateSector {
    private final RepositorySectorGateways repositorySectorGateways;

    public UpdateSector(RepositorySectorGateways repositorySectorGateways) {
        this.repositorySectorGateways = repositorySectorGateways;
    }

    public Sector updateSector(long id,Sector sector){
        if(!this.repositorySectorGateways.existsSectorAndActive(id)||this.repositorySectorGateways.existsBySectorsAndShelfAndColumnAndRow(sector.getSectors(),sector.getShelf(),sector.getColumn(),sector.getRow())){
            throw new RuntimeException();
        }
        return this.repositorySectorGateways.updateSector(id,sector);
    }
}
