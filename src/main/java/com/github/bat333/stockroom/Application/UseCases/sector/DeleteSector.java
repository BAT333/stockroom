package com.github.bat333.stockroom.Application.UseCases.sector;

import com.github.bat333.stockroom.Application.Exception.SectorExists;
import com.github.bat333.stockroom.Application.Gateways.Sector.RepositorySectorGateways;
import com.github.bat333.stockroom.Application.UseCases.Part.UpdatePart;

import java.util.logging.Level;
import java.util.logging.Logger;

public class DeleteSector {
    private final RepositorySectorGateways repositorySectorGateways;
    private static final Logger logger = Logger.getLogger(DeleteSector.class.getName());

    public DeleteSector(RepositorySectorGateways repositorySectorGateways) {
        this.repositorySectorGateways = repositorySectorGateways;
    }

    public void deleteSector(long id){
        logger.info("Starting sector delete with ID: "+id);

        if(!this.repositorySectorGateways.existsSectorAndActive(id)){
            logger.log(Level.SEVERE,"This sector does not exist with ID: "+ id);

            throw new SectorExists("This sector does not exist");
        }
        logger.info("successfully delete sector ID: " + id);

        this.repositorySectorGateways.deleteSector(id);
    }
}
