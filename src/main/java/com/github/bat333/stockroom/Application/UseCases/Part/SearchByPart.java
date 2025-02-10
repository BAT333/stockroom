package com.github.bat333.stockroom.Application.UseCases.Part;

import com.github.bat333.stockroom.Application.Gateways.Part.RepositoryPart;
import com.github.bat333.stockroom.Domain.Entities.part.Part;

import java.util.List;

public class SearchByPart {
    private final RepositoryPart repositoryPart;

    public SearchByPart(RepositoryPart repositoryPart) {
        this.repositoryPart = repositoryPart;
    }

    public List<Part> searchByPart(String name, long cod){

        return repositoryPart.searchPart(name,cod);
    }
}
