package com.github.bat333.stockroom.Application.UseCases.Part;

import com.github.bat333.stockroom.Application.Gateways.Part.RepositoryPart;
import com.github.bat333.stockroom.Domain.Entities.part.Part;

import java.util.List;

public class ListAllParts {
    private final RepositoryPart repositoryPart;

    public ListAllParts(RepositoryPart repositoryPart) {
        this.repositoryPart = repositoryPart;
    }

    public List<Part> listAllParts(){
        return this.repositoryPart.listAllActiveParts();
    }
}
