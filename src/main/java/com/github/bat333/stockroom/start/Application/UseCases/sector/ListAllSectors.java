package com.github.bat333.stockroom.start.Application.UseCases.sector;

import com.github.bat333.stockroom.start.Application.Gateways.Sector.RepositorySectorGateways;
import com.github.bat333.stockroom.start.Domain.Entities.sector.Sector;

import java.util.List;

public class ListAllSectors {
    private final RepositorySectorGateways repositorySectorGateways;

    public ListAllSectors(RepositorySectorGateways repositorySectorGateways) {
        this.repositorySectorGateways = repositorySectorGateways;
    }

    public List<Sector> listAllSectors (){
        return this.repositorySectorGateways.listAllActiveSectors();
    }
}
