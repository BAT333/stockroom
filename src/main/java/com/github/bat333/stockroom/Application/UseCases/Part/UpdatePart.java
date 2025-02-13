package com.github.bat333.stockroom.Application.UseCases.Part;

import com.github.bat333.stockroom.Application.Gateways.Part.RepositoryPartGateways;
import com.github.bat333.stockroom.Domain.Entities.part.Part;

public class UpdatePart {
    private final RepositoryPartGateways repositoryPartGateways;

    public UpdatePart(RepositoryPartGateways repositoryPartGateways) {
        this.repositoryPartGateways = repositoryPartGateways;
    }

    public Part updatePart(long id, Part part){
        if(repositoryPartGateways.existsPartAndActive(id)){
            throw new RuntimeException();
        }

       return repositoryPartGateways.updatePart(id,part);

    }
}
