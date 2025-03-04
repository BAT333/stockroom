package com.github.bat333.stockroom.Domain.Entities.sector;

import com.github.bat333.stockroom.Domain.Entities.part.Part;
import com.github.bat333.stockroom.Domain.Entities.part.PartFactory;
import com.github.bat333.stockroom.Domain.Exception.SectorValidation;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SectorFactory{
    private static final Logger logger = Logger.getLogger(SectorFactory.class.getName());

    public Sector createSector(Long id, String sectors, String shelf, String column, String row, boolean active, List<Part> parts){
       try {
           logger.info("Starting the creation of the sector with ID: " + id);
           SectorValidator.validate(sectors,shelf,column,row);
           Sector sector = new Sector(id,sectors,shelf,column,row,active,parts);
           logger.info("sector created successfully: " + sector);
           return sector;
       }catch (SectorValidation ex){
           logger.log(Level.SEVERE, "Error creating sector with ID: " + id, ex);
           throw ex;
       }
    }
}
