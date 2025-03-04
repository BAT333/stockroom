package com.github.bat333.stockroom.Application.UseCases.Part;

import com.github.bat333.stockroom.Application.Exception.PartExists;
import com.github.bat333.stockroom.Application.Gateways.Part.RepositoryPartGateways;
import com.github.bat333.stockroom.Domain.Entities.sector.SectorFactory;

import java.util.logging.Level;
import java.util.logging.Logger;

public class DeletePart {
    private final RepositoryPartGateways repositoryPartGateways;
    private static final Logger logger = Logger.getLogger(DeletePart.class.getName());


    public DeletePart (RepositoryPartGateways repositoryPartGateways){
        this.repositoryPartGateways = repositoryPartGateways;
    }

    public void deletePart (long id){
        logger.info("Starting part delete with ID: "+ id);

        if(!repositoryPartGateways.existsPartAndActive(id)){
            logger.log(Level.SEVERE,"Error when deleting part with ID: " + id);
            throw new PartExists("This part does not exist");
        }
        logger.info("Part delete successfully with ID: " + id);
        this.repositoryPartGateways.deletePart(id);
    }
}
