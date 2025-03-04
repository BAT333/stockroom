package com.github.bat333.stockroom.Application.UseCases.sector;

import com.github.bat333.stockroom.Application.Exception.PartExists;
import com.github.bat333.stockroom.Application.Exception.SectorExists;
import com.github.bat333.stockroom.Application.Gateways.Sector.RepositorySectorGateways;
import com.github.bat333.stockroom.Domain.Entities.sector.Sector;

import java.util.logging.Level;
import java.util.logging.Logger;

public class UpdateSector {
    private final RepositorySectorGateways repositorySectorGateways;
    private static final Logger logger = Logger.getLogger(UpdateSector.class.getName());

    public UpdateSector(RepositorySectorGateways repositorySectorGateways) {
        this.repositorySectorGateways = repositorySectorGateways;
    }

    public Sector updateSector(long id,Sector sector){
        logger.info("Starting sector update with ID: "+ id);

        if(!this.repositorySectorGateways.existsSectorAndActive(id)){
            logger.log(Level.SEVERE,"This sector does not exist with ID: "+ id);

            throw new SectorExists("This sector does not exist");
        }
        if(this.repositorySectorGateways.existsBySectorsAndShelfAndColumnAndRow(sector.getSectors(),sector.getShelf(),sector.getColumn(),sector.getRow())){
            logger.log(Level.SEVERE,String.format("Sector with sector '%s' and Shelf '%s' and Column '%s' and Row '%s' already exists.",
                    sector.getSectors(),sector.getShelf(),sector.getColumn(),sector.getRow()));

            throw new PartExists(String.format("Sector with sector '%s' and Shelf '%s' and Column '%s' and Row '%s' already exists.",
                    sector.getSectors(),sector.getShelf(),sector.getColumn(),sector.getRow()));

        }
        logger.info("successfully update with ID : " + id);

        return this.repositorySectorGateways.updateSector(id,sector);
    }
}
