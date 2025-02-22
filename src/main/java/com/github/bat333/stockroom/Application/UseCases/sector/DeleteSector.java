package com.github.bat333.stockroom.Application.UseCases.sector;

import com.github.bat333.stockroom.Application.Gateways.Sector.RepositorySectorGateways;

public class DeleteSector {
    private final RepositorySectorGateways repositorySectorGateways;

    public DeleteSector(RepositorySectorGateways repositorySectorGateways) {
        this.repositorySectorGateways = repositorySectorGateways;
    }

    public void deleteSector(long id){
        if(!this.repositorySectorGateways.existsSectorAndActive(id)){
            throw new RuntimeException();
        }
        this.repositorySectorGateways.deleteSector(id);
    }
}
