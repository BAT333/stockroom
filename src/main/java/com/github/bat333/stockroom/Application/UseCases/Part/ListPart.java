package com.github.bat333.stockroom.Application.UseCases.Part;

import com.github.bat333.stockroom.Application.Exception.PartExists;
import com.github.bat333.stockroom.Application.Gateways.Part.RepositoryPartGateways;
import com.github.bat333.stockroom.Domain.Entities.part.Part;

import java.util.logging.Level;
import java.util.logging.Logger;

public class ListPart {
    private final RepositoryPartGateways repositoryPartGateways;
    private static final Logger logger = Logger.getLogger(ListPart.class.getName());

    public ListPart(RepositoryPartGateways repositoryPartGateways){
        this.repositoryPartGateways = repositoryPartGateways;
    }

    public Part listPart(long id){
        logger.info("part with ID:"+ id);
        if(!repositoryPartGateways.existsPartAndActive(id)){
            logger.log(Level.SEVERE,"Error get part with ID: " + id);
            throw new PartExists("This part does not exist");
        }
        logger.info("Get part successfully with ID: " + id);
        return this.repositoryPartGateways.listActivePart(id);
    }
}
