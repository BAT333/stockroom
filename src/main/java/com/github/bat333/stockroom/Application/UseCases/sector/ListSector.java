package com.github.bat333.stockroom.Application.UseCases.sector;

import com.github.bat333.stockroom.Application.Gateways.Sector.RepositorySector;
import com.github.bat333.stockroom.Domain.Entities.sector.Sector;

import java.util.List;

public class ListSector {
    private final RepositorySector repositorySector;

    public ListSector(RepositorySector repositorySector) {
        this.repositorySector = repositorySector;
    }

    public Sector listSector(long id){
        if(this.repositorySector.existsSectorAndActive(id)){
            throw new RuntimeException();
        }

        return this.repositorySector.listActiveSector(id);
    }
}
