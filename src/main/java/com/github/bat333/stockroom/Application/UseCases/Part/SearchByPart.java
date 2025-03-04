package com.github.bat333.stockroom.Application.UseCases.Part;

import com.github.bat333.stockroom.Application.Gateways.Part.RepositoryPartGateways;
import com.github.bat333.stockroom.Domain.Entities.part.Part;

import java.util.List;
import java.util.logging.Logger;

public class SearchByPart {
    private final RepositoryPartGateways repositoryPartGateways;
    private static final Logger logger = Logger.getLogger(SearchByPart.class.getName());

    public SearchByPart(RepositoryPartGateways repositoryPartGateways) {
        this.repositoryPartGateways = repositoryPartGateways;
    }

    public List<Part> searchByPart(String name, Long cod){
        logger.info("successfully search by part");
        return repositoryPartGateways.searchPart(name,cod);
    }
}
