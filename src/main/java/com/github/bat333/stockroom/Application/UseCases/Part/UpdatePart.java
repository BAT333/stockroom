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

public class UpdatePart {
    private final RepositoryPartGateways repositoryPartGateways;
    private final RepositorySectorGateways sectorGateways;
    private static final Logger logger = Logger.getLogger(UpdatePart.class.getName());
    private final ImageProcessing image;

    public UpdatePart(RepositoryPartGateways repositoryPartGateways, RepositorySectorGateways sectorGateways, ImageProcessing image) {
        this.repositoryPartGateways = repositoryPartGateways;
        this.sectorGateways = sectorGateways;
        this.image = image;
    }

    public Part updatePart(long id, Part part,Long sector){
        logger.info("Starting part update with part: "+part);
        if (part.getCod() != null && part.getName() != null && !part.getName().isEmpty()) {
            if (repositoryPartGateways.existsByCodAndName(part.getCod(), part.getName())) {
                logger.log(Level.SEVERE,String.format("Error when update part with code '%s' and name '%s' already exists.",
                        part.getCod(), part.getName()));
                throw new PartExists(String.format("Part with code '%s' and name '%s' already exists.",
                        part.getCod(), part.getName()));
            }
        }
        if (sector != null && !sectorGateways.existsSectorAndActive(sector)) {
            logger.log(Level.SEVERE,"Error when update part with Sector ID " + id + " does not exist ");

            throw new SectorExists("This sector does not exist");
        }
        if (!repositoryPartGateways.existsPartAndActive(id)) {
            logger.log(Level.SEVERE,"Error when update part does not exist ID " + id + " does not exist ");

            throw new PartExists("This part does not exist");
        }
        try {
            part.setImage(image.resizeAndCompressImage(part.getImage(),800, 800, 0.7f ));
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error registering part when compressing image");
            throw new RuntimeException(e);
        }
        logger.info("successfully update part: " + part);
        return repositoryPartGateways.updatePart(id,part,sector);

    }
}
