package com.github.bat333.stockroom.Application.UseCases.Part;

import com.github.bat333.stockroom.Application.Gateways.Part.RepositoryPartGateways;
import com.github.bat333.stockroom.Domain.Entities.part.Part;

public class SavePart {
    private final RepositoryPartGateways repositoryPartGateways;

    public SavePart(RepositoryPartGateways repositoryPartGateways) {
        this.repositoryPartGateways = repositoryPartGateways;
    }

    public Part savePart(Part part){
        if(repositoryPartGateways.existsByCodAndName(part.getCod(), part.getName())){
            throw new RuntimeException();
        }
        return this.repositoryPartGateways.savePart(part);
    }
}
