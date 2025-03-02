package com.github.bat333.stockroom.start.Application.UseCases.sector;

import com.github.bat333.stockroom.start.Application.Exception.SectorExists;
import com.github.bat333.stockroom.start.Application.Gateways.Sector.RepositorySectorGateways;
import com.github.bat333.stockroom.start.Domain.Entities.sector.Sector;

public class ListSector {
    private final RepositorySectorGateways repositorySectorGateways;

    public ListSector(RepositorySectorGateways repositorySectorGateways) {
        this.repositorySectorGateways = repositorySectorGateways;
    }

    public Sector listSector(long id){
        if(!this.repositorySectorGateways.existsSectorAndActive(id)){
            throw new SectorExists("This sector does not exist");

        }

        return this.repositorySectorGateways.listActiveSector(id);
    }
}
