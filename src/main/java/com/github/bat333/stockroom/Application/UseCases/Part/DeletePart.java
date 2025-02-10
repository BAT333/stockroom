package com.github.bat333.stockroom.Application.UseCases.Part;

import com.github.bat333.stockroom.Application.Gateways.Part.RepositoryPart;

public class DeletePart {
    private final RepositoryPart repositoryPart;

    public DeletePart (RepositoryPart repositoryPart){
        this.repositoryPart = repositoryPart;
    }

    public void deletePart (long id){
        if(repositoryPart.existsPartAndActive(id)){
            throw new RuntimeException();
        }

        this.repositoryPart.deletePart(id);
    }
}
