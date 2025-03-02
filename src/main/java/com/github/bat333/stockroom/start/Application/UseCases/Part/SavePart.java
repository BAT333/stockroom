package com.github.bat333.stockroom.start.Application.UseCases.Part;

import com.github.bat333.stockroom.start.Application.Exception.PartExists;
import com.github.bat333.stockroom.start.Application.Exception.SectorExists;
import com.github.bat333.stockroom.start.Application.Gateways.Part.RepositoryPartGateways;
import com.github.bat333.stockroom.start.Application.Gateways.Sector.RepositorySectorGateways;
import com.github.bat333.stockroom.start.Application.Gateways.ValueObjects.ImageProcessing;
import com.github.bat333.stockroom.start.Domain.Entities.part.Part;

import java.io.IOException;

public class SavePart {
    private final RepositoryPartGateways repositoryPartGateways;
    private final RepositorySectorGateways sectorGateways;
    private final ImageProcessing image;

    public SavePart(RepositoryPartGateways repositoryPartGateways, RepositorySectorGateways sectorGateways, ImageProcessing image) {
        this.repositoryPartGateways = repositoryPartGateways;
        this.sectorGateways = sectorGateways;
        this.image = image;
    }

    public Part savePart(Part part, long id)  {
        if(repositoryPartGateways.existsByCodAndName(part.getCod(), part.getName())){
            throw new PartExists(String.format("Part with code '%s' and name '%s' already exists.",
                    part.getCod(), part.getName()));
        }
        if(!sectorGateways.existsSectorAndActive(id)){
            throw new SectorExists("This sector does not exist");

        }
        try {
            part.setImage(image.resizeAndCompressImage(part.getImage(),800, 800, 0.7f ));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return this.repositoryPartGateways.savePart(part,id);
    }
}
