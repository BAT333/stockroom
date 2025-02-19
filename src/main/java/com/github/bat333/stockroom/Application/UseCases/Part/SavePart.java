package com.github.bat333.stockroom.Application.UseCases.Part;

import com.github.bat333.stockroom.Application.Gateways.Part.RepositoryPartGateways;
import com.github.bat333.stockroom.Application.Gateways.Sector.RepositorySectorGateways;
import com.github.bat333.stockroom.Domain.Entities.part.Part;

public class SavePart {
    private final RepositoryPartGateways repositoryPartGateways;
    private final RepositorySectorGateways sectorGateways;

    public SavePart(RepositoryPartGateways repositoryPartGateways, RepositorySectorGateways sectorGateways) {
        this.repositoryPartGateways = repositoryPartGateways;
        this.sectorGateways = sectorGateways;
    }

    public Part savePart(Part part, long id){
        if(repositoryPartGateways.existsByCodAndName(part.getCod(), part.getName())|| !sectorGateways.existsSectorAndActive(id)){
            throw new RuntimeException();
        }
        return this.repositoryPartGateways.savePart(part,id);
    }
}
