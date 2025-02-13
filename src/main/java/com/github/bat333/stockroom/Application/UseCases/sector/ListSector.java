package com.github.bat333.stockroom.Application.UseCases.sector;

import com.github.bat333.stockroom.Application.Gateways.Sector.RepositorySectorGateways;
import com.github.bat333.stockroom.Domain.Entities.sector.Sector;

public class ListSector {
    private final RepositorySectorGateways repositorySectorGateways;

    public ListSector(RepositorySectorGateways repositorySectorGateways) {
        this.repositorySectorGateways = repositorySectorGateways;
    }

    public Sector listSector(long id){
        if(this.repositorySectorGateways.existsSectorAndActive(id)){
            throw new RuntimeException();
        }

        return this.repositorySectorGateways.listActiveSector(id);
    }
}
