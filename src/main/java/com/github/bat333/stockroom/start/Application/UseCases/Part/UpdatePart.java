package com.github.bat333.stockroom.start.Application.UseCases.Part;

import com.github.bat333.stockroom.start.Application.Exception.PartExists;
import com.github.bat333.stockroom.start.Application.Exception.SectorExists;
import com.github.bat333.stockroom.start.Application.Gateways.Part.RepositoryPartGateways;
import com.github.bat333.stockroom.start.Application.Gateways.Sector.RepositorySectorGateways;
import com.github.bat333.stockroom.start.Domain.Entities.part.Part;

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
                throw new PartExists(String.format("Part with code '%s' and name '%s' already exists.",
                        part.getCod(), part.getName()));
            }
        }
        if (sector != null && !sectorGateways.existsSectorAndActive(sector)) {
            throw new SectorExists("This sector does not exist");
        }
        if (!repositoryPartGateways.existsPartAndActive(id)) {
            throw new PartExists("This part does not exist");
        }
        return repositoryPartGateways.updatePart(id,part,sector);

    }
}
