package com.github.bat333.stockroom.Application.UseCases.Part;

import com.github.bat333.stockroom.Application.Gateways.Part.RepositoryPart;
import com.github.bat333.stockroom.Domain.Entities.part.Part;

import java.util.List;

public class ListPart {
    private final RepositoryPart repositoryPart;

    public ListPart(RepositoryPart repositoryPart){
        this.repositoryPart = repositoryPart;
    }

    public Part listPart(long id){
        if(repositoryPart.existsPartAndActive(id)){
            throw new RuntimeException();
        }
        return this.repositoryPart.listActivePart(id);
    }
}
