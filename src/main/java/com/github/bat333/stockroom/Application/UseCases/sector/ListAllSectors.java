package com.github.bat333.stockroom.Application.UseCases.sector;

import com.github.bat333.stockroom.Application.Gateways.Sector.RepositorySectorGateways;
import com.github.bat333.stockroom.Domain.Entities.sector.Sector;

import java.util.List;
import java.util.logging.Logger;

public class ListAllSectors {
    private final RepositorySectorGateways repositorySectorGateways;
    private static final Logger logger = Logger.getLogger(ListAllSectors.class.getName());

    public ListAllSectors(RepositorySectorGateways repositorySectorGateways) {
        this.repositorySectorGateways = repositorySectorGateways;
    }

    public List<Sector> listAllSectors (){
        logger.info("sector list");
        return this.repositorySectorGateways.listAllActiveSectors();
    }
}
