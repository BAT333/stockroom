package com.github.bat333.stockroom.Application.UseCases.sector;

import com.github.bat333.stockroom.Application.Gateways.Sector.RepositorySector;
import com.github.bat333.stockroom.Domain.Entities.sector.Sector;

public class UpdateSector {
    private final RepositorySector repositorySector;

    public UpdateSector(RepositorySector repositorySector) {
        this.repositorySector = repositorySector;
    }

    public Sector updateSector(long id,Sector sector){
        if(this.repositorySector.existsSectorAndActive(id)){
            throw new RuntimeException();
        }

        return this.repositorySector.updateSector(id,sector);
    }
}
