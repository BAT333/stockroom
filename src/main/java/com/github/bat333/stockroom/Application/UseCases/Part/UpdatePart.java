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
        System.out.println("1");
        //||part.getSector().getId()!=null?!sectorGateways.existsSectorAndActive(part.getSector().getId()):false
        if(!repositoryPartGateways.existsPartAndActive(id)){
            throw new RuntimeException();
        }
        System.out.println("2");

       return repositoryPartGateways.updatePart(id,part);

    }
}
