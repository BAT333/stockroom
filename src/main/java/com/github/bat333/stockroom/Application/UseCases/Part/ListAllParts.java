package com.github.bat333.stockroom.Application.UseCases.Part;

import com.github.bat333.stockroom.Application.Gateways.Part.RepositoryPartGateways;
import com.github.bat333.stockroom.Domain.Entities.part.Part;

import java.util.List;

public class ListAllParts {
    private final RepositoryPartGateways repositoryPartGateways;

    public ListAllParts(RepositoryPartGateways repositoryPartGateways) {
        this.repositoryPartGateways = repositoryPartGateways;
    }

    public List<Part> listAllParts(){
        return this.repositoryPartGateways.listAllActiveParts();
    }
}
