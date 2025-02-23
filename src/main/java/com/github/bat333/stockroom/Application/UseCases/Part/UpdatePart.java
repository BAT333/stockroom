package com.github.bat333.stockroom.Application.UseCases.Part;

import com.github.bat333.stockroom.Application.Gateways.Part.RepositoryPartGateways;
import com.github.bat333.stockroom.Application.Gateways.Sector.RepositorySectorGateways;
import com.github.bat333.stockroom.Domain.Entities.part.Part;

public class UpdatePart {
    private final RepositoryPartGateways repositoryPartGateways;
    private final RepositorySectorGateways sectorGateways;

    public UpdatePart(RepositoryPartGateways repositoryPartGateways, RepositorySectorGateways sectorGateways) {
        this.repositoryPartGateways = repositoryPartGateways;
        this.sectorGateways = sectorGateways;
    }

    public Part updatePart(long id, Part part,Long sector){

        if (part.getCod() != null && part.getName() != null && !part.getName().isEmpty()) {
            if (repositoryPartGateways.existsByCodAndName(part.getCod(), part.getName())) {
                throw new RuntimeException();
            }
        }
        if (sector != null && !sectorGateways.existsSectorAndActive(sector)) {
            throw new RuntimeException();
        }
        if (!repositoryPartGateways.existsPartAndActive(id)) {
            throw new RuntimeException();
        }
        return repositoryPartGateways.updatePart(id,part,sector);

    }
}
