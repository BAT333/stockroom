package com.github.bat333.stockroom.Application.UseCases.sector;

import com.github.bat333.stockroom.Application.Gateways.Sector.RepositorySector;

public class DeleteSector {
    private final RepositorySector repositorySector;

    public DeleteSector(RepositorySector repositorySector) {
        this.repositorySector = repositorySector;
    }

    public void deleteSector(long id){
        if(this.repositorySector.existsSectorAndActive(id)){
            throw new RuntimeException();
        }
        this.repositorySector.deleteSector(id);
    }
}
