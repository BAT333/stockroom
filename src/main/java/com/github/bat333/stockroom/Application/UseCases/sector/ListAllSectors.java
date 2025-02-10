package com.github.bat333.stockroom.Application.UseCases.sector;

import com.github.bat333.stockroom.Application.Gateways.Sector.RepositorySector;
import com.github.bat333.stockroom.Domain.Entities.sector.Sector;

import java.util.List;

public class ListAllSectors {
    private final RepositorySector repositorySector;

    public ListAllSectors(RepositorySector repositorySector) {
        this.repositorySector = repositorySector;
    }

    public List<Sector> listAllSectors (){
        return this.repositorySector.listAllActiveSectors();
    }
}
