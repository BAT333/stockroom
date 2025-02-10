package com.github.bat333.stockroom.Application.UseCases.Part;

import com.github.bat333.stockroom.Application.Gateways.Part.RepositoryPart;
import com.github.bat333.stockroom.Domain.Entities.part.Part;

public class UpdatePart {
    private final RepositoryPart repositoryPart;

    public UpdatePart(RepositoryPart repositoryPart) {
        this.repositoryPart = repositoryPart;
    }

    public Part updatePart(long id, Part part){
        if(repositoryPart.existsPartAndActive(id)){
            throw new RuntimeException();
        }

       return repositoryPart.updatePart(id,part);

    }
}
