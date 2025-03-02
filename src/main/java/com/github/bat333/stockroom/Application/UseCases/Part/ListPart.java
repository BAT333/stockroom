package com.github.bat333.stockroom.Application.UseCases.Part;

import com.github.bat333.stockroom.Application.Exception.PartExists;
import com.github.bat333.stockroom.Application.Gateways.Part.RepositoryPartGateways;
import com.github.bat333.stockroom.Domain.Entities.part.Part;

public class ListPart {
    private final RepositoryPartGateways repositoryPartGateways;

    public ListPart(RepositoryPartGateways repositoryPartGateways){
        this.repositoryPartGateways = repositoryPartGateways;
    }

    public Part listPart(long id){
        if(!repositoryPartGateways.existsPartAndActive(id)){
            throw new PartExists("This part does not exist");
        }
        return this.repositoryPartGateways.listActivePart(id);
    }
}
