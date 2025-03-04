package com.github.bat333.stockroom.Application.UseCases.Part;

import com.github.bat333.stockroom.Application.Exception.PartExists;
import com.github.bat333.stockroom.Application.Exception.SectorExists;
import com.github.bat333.stockroom.Application.Gateways.Part.RepositoryPartGateways;
import com.github.bat333.stockroom.Application.Gateways.Sector.RepositorySectorGateways;
import com.github.bat333.stockroom.Application.Gateways.ValueObjects.ImageProcessing;
import com.github.bat333.stockroom.Domain.Entities.part.Part;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SavePart {
    private final RepositoryPartGateways repositoryPartGateways;
    private final RepositorySectorGateways sectorGateways;
    private final ImageProcessing image;
    private static final Logger logger = Logger.getLogger(SavePart.class.getName());


    public SavePart(RepositoryPartGateways repositoryPartGateways, RepositorySectorGateways sectorGateways, ImageProcessing image) {
        this.repositoryPartGateways = repositoryPartGateways;
        this.sectorGateways = sectorGateways;
        this.image = image;
    }

    public Part savePart(Part part, long id)  {
        logger.info("Starting part save");
        if(repositoryPartGateways.existsByCodAndName(part.getCod(), part.getName())){
            logger.log(Level.SEVERE,String.format("Error when registering part with code '%s' and name '%s' already exists.",
                    part.getCod(), part.getName()));

            throw new PartExists(String.format("Part with code '%s' and name '%s' already exists.",
                    part.getCod(), part.getName()));
        }
        if(!sectorGateways.existsSectorAndActive(id)){
            logger.log(Level.SEVERE,"Error when registering part with Sector ID " + id + " does not exist ");

            throw new SectorExists("This sector does not exist");

        }
        try {
            part.setImage(image.resizeAndCompressImage(part.getImage(),800, 800, 0.7f ));
        } catch (IOException e) {
            logger.log(Level.SEVERE,"Error registering part when compressing image");
            throw new RuntimeException(e);
        }
        logger.info("successfully registering part: " + part);
        return this.repositoryPartGateways.savePart(part,id);
    }
}
