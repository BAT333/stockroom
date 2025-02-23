package com.github.bat333.stockroom.Application.UseCases.Part;

import com.github.bat333.stockroom.Application.Gateways.Part.RepositoryPartGateways;
import com.github.bat333.stockroom.Application.Gateways.Sector.RepositorySectorGateways;
import com.github.bat333.stockroom.Application.Gateways.ValueObjects.ImageProcessing;
import com.github.bat333.stockroom.Domain.Entities.part.Part;

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
        if(repositoryPartGateways.existsByCodAndName(part.getCod(), part.getName())|| !sectorGateways.existsSectorAndActive(id)){
            throw new RuntimeException();
        }
        try {
            part.setImage(image.resizeAndCompressImage(part.getImage(),800, 800, 0.7f ));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return this.repositoryPartGateways.savePart(part,id);
    }
}
