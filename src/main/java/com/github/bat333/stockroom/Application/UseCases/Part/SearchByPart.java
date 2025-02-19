package com.github.bat333.stockroom.Application.UseCases.Part;

import com.github.bat333.stockroom.Application.Gateways.Part.RepositoryPartGateways;
import com.github.bat333.stockroom.Domain.Entities.part.Part;

import java.util.List;

public class SearchByPart {
    private final RepositoryPartGateways repositoryPartGateways;

    public SearchByPart(RepositoryPartGateways repositoryPartGateways) {
        this.repositoryPartGateways = repositoryPartGateways;
    }

    public List<Part> searchByPart(String name, Long cod){

        return repositoryPartGateways.searchPart(name,cod);
    }
}
