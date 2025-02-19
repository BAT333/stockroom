package com.github.bat333.stockroom.Application.UseCases.Part;

import com.github.bat333.stockroom.Application.Gateways.Part.RepositoryPartGateways;
import com.github.bat333.stockroom.Application.Gateways.Sector.RepositorySectorGateways;
import com.github.bat333.stockroom.Domain.Entities.part.Part;

public class UpdatePart {
    private final RepositoryPartGateways repositoryPartGateways;
    private final RepositorySectorGateways sectorGateways;

    public UpdatePart(RepositoryPartGateways repositoryPartGateways, RepositorySectorGateways sectorGateways) {
        this.repositoryPartGateways = repositoryPartGateways;
        this.sectorGateways = sectorGateways;
    }

    public Part updatePart(long id, Part part){
        if(repositoryPartGateways.existsPartAndActive(id)||!sectorGateways.existsSectorAndActive(part.getSector().getId())){
            throw new RuntimeException();
        }

       return repositoryPartGateways.updatePart(id,part);

    }
}
