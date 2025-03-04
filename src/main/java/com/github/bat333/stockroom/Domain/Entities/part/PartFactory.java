package com.github.bat333.stockroom.Domain.Entities.part;

import com.github.bat333.stockroom.Domain.Entities.sector.Sector;
import com.github.bat333.stockroom.Domain.Exception.PartValidation;

import java.util.logging.Level;
import java.util.logging.Logger;

public class PartFactory {
    private static final Logger logger = Logger.getLogger(PartFactory.class.getName());

    public static Part createPart(Long id, Long cod, String name, byte[] image, double amount, boolean active, Sector sector) {
        try {
            logger.info("Starting the creation of the part with ID: " + id);
            PartValidator.validate(id, cod, name, image, amount, sector);
            Part part = new Part(id, cod, name, image, amount, active, sector);
            logger.info("Part created successfully: " + part);
            return part;
        }catch (PartValidation ex){
            logger.log(Level.SEVERE, "Error creating part with ID: " + id, ex);
            throw ex;
        }
    }

    public static Part createPartUpdate( Long cod, String name, byte[] image, double amount) {
        logger.info("Starting part update with ID: ");
        Part part = new Part();
        part.setCod(cod);
        part.setName(name);
        part.setImage(image);
        part.setAmount(amount);
        logger.info("Part update successfully: " + part);
        return part;
    }
}
