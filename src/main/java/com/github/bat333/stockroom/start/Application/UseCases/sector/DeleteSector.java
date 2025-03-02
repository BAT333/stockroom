package com.github.bat333.stockroom.start.Application.UseCases.sector;

import com.github.bat333.stockroom.start.Application.Exception.SectorExists;
import com.github.bat333.stockroom.start.Application.Gateways.Sector.RepositorySectorGateways;

public class DeleteSector {
    private final RepositorySectorGateways repositorySectorGateways;

    public DeleteSector(RepositorySectorGateways repositorySectorGateways) {
        this.repositorySectorGateways = repositorySectorGateways;
    }

    public void deleteSector(long id){
        if(!this.repositorySectorGateways.existsSectorAndActive(id)){
            throw new SectorExists("This sector does not exist");

        }
        this.repositorySectorGateways.deleteSector(id);
    }
}
