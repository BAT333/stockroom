package com.github.bat333.stockroom.Application.UseCases.sector;

import com.github.bat333.stockroom.Application.Exception.SectorExists;
import com.github.bat333.stockroom.Application.Gateways.Sector.RepositorySectorGateways;
import com.github.bat333.stockroom.Domain.Entities.sector.Sector;

import java.util.logging.Level;
import java.util.logging.Logger;

public class SaveSector {
    private final RepositorySectorGateways repositorySectorGateways;
    private static final Logger logger = Logger.getLogger(SaveSector.class.getName());

    public SaveSector(RepositorySectorGateways repositorySectorGateways) {
        this.repositorySectorGateways = repositorySectorGateways;
    }

    public Sector saveSector(Sector sector){
        logger.info("Starting sector register with Sector: "+ sector);
        if(this.repositorySectorGateways.existsBySectorsAndShelfAndColumnAndRow(sector.getSectors(),sector.getShelf(),sector.getColumn(),sector.getRow())){
            logger.log(Level.SEVERE,String.format("Sector with sector '%s' and Shelf '%s' and Column '%s' and Row '%s' already exists.",
                    sector.getSectors(),sector.getShelf(),sector.getColumn(),sector.getRow()));

            throw new SectorExists(String.format("Sector with sector '%s' and Shelf '%s' and Column '%s' and Row '%s' already exists.",
                    sector.getSectors(),sector.getShelf(),sector.getColumn(),sector.getRow()));
        }
        logger.info("successfully register sector : " + sector);
        return this.repositorySectorGateways.saveSector(sector);
    }
}
