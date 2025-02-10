package com.github.bat333.stockroom.Application.UseCases.Part;

import com.github.bat333.stockroom.Application.Gateways.Part.RepositoryPart;
import com.github.bat333.stockroom.Domain.Entities.part.Part;

public class SavePart {
    private final RepositoryPart repositoryPart;

    public SavePart(RepositoryPart repositoryPart) {
        this.repositoryPart = repositoryPart;
    }

    public Part savePart(Part part){
        if(repositoryPart.existsByCodAndName(part.getCod(), part.getName())){
            throw new RuntimeException();
        }
        return this.repositoryPart.savePart(part);
    }
}
