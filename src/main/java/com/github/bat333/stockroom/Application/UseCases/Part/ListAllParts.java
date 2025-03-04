package com.github.bat333.stockroom.Application.UseCases.Part;

import com.github.bat333.stockroom.Application.Gateways.Part.RepositoryPartGateways;
import com.github.bat333.stockroom.Domain.Entities.part.Part;

import java.util.List;
import java.util.logging.Logger;

public class ListAllParts {
    private final RepositoryPartGateways repositoryPartGateways;
    private static final Logger logger = Logger.getLogger(ListAllParts.class.getName());

    public ListAllParts(RepositoryPartGateways repositoryPartGateways) {
        logger.info("Listing all parts");
        this.repositoryPartGateways = repositoryPartGateways;
    }

    public List<Part> listAllParts(){
        return this.repositoryPartGateways.listAllActiveParts();
    }
}
