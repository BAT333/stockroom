package com.github.bat333.stockroom.Application.UseCases.sector;

import com.github.bat333.stockroom.Application.Exception.SectorExists;
import com.github.bat333.stockroom.Application.Gateways.Sector.RepositorySectorGateways;
import com.github.bat333.stockroom.Domain.Entities.sector.Sector;

import java.util.logging.Level;
import java.util.logging.Logger;

public class ListSector {
    private final RepositorySectorGateways repositorySectorGateways;
    private static final Logger logger = Logger.getLogger(ListSector.class.getName());

    public ListSector(RepositorySectorGateways repositorySectorGateways) {
        this.repositorySectorGateways = repositorySectorGateways;
    }

    public Sector listSector(long id){
        logger.info("Get sector with ID: "+ id);
        if(!this.repositorySectorGateways.existsSectorAndActive(id)){
            logger.log(Level.SEVERE,"This sector does not exist with ID: "+ id);

            throw new SectorExists("This sector does not exist");

        }
        logger.info("successfully get sector ID: " + id);
        return this.repositorySectorGateways.listActiveSector(id);
    }
}
