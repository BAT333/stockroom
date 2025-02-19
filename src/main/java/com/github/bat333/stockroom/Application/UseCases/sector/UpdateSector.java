package com.github.bat333.stockroom.Application.UseCases.sector;

import com.github.bat333.stockroom.Application.Gateways.Sector.RepositorySectorGateways;
import com.github.bat333.stockroom.Domain.Entities.sector.Sector;

public class UpdateSector {
    private final RepositorySectorGateways repositorySectorGateways;

    public UpdateSector(RepositorySectorGateways repositorySectorGateways) {
        this.repositorySectorGateways = repositorySectorGateways;
    }

    public Sector updateSector(long id,Sector sector){
        if(this.repositorySectorGateways.existsSectorAndActive(id)){
            throw new RuntimeException();
        }
        Sector sectorUpdate = this.repositorySectorGateways.updateSector(id,sector);

        if(this.repositorySectorGateways.existsBySectorsAndShelfAndColumnAndRow(sectorUpdate.getSectors(),sectorUpdate.getShelf(),sectorUpdate.getColumn(),sectorUpdate.getRow())){
            throw new RuntimeException();
        }
        return sectorUpdate ;
    }
}
