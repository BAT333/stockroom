package com.github.bat333.stockroom.Application.UseCases.Part;

import com.github.bat333.stockroom.Application.Exception.PartExists;
import com.github.bat333.stockroom.Application.Gateways.Part.RepositoryPartGateways;

public class DeletePart {
    private final RepositoryPartGateways repositoryPartGateways;

    public DeletePart (RepositoryPartGateways repositoryPartGateways){
        this.repositoryPartGateways = repositoryPartGateways;
    }

    public void deletePart (long id){
        if(!repositoryPartGateways.existsPartAndActive(id)){
            throw new PartExists("This part does not exist");
        }

        this.repositoryPartGateways.deletePart(id);
    }
}
