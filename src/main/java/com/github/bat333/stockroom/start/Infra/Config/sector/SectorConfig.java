package com.github.bat333.stockroom.start.Infra.Config.sector;


import com.github.bat333.stockroom.start.Application.Gateways.Sector.RepositorySectorGateways;
import com.github.bat333.stockroom.Application.UseCases.sector.*;
import com.github.bat333.stockroom.start.Application.UseCases.sector.*;
import com.github.bat333.stockroom.start.Infra.Adapters.sector.SectorEntityMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SectorConfig {


    @Bean
    public SaveSector createSector(RepositorySectorGateways repositorySector){
        return new SaveSector(repositorySector);
    }

    @Bean
    public DeleteSector deleteSector(RepositorySectorGateways repositorySector){
        return new DeleteSector(repositorySector);
    }
    @Bean
    public ListAllSectors listAllSectors(RepositorySectorGateways repositorySector){
        return new ListAllSectors(repositorySector);
    }
    @Bean
    public ListSector listSector(RepositorySectorGateways repositorySector){
        return new ListSector(repositorySector);
    }
    @Bean
    public UpdateSector updateSector(RepositorySectorGateways repositorySector){
        return new UpdateSector(repositorySector);
    }


    @Bean
    public SectorEntityMapper sectorEntityMapper(){
        return new SectorEntityMapper();
    }


    /*
    @Bean
    public RepositorySectorGatewaysJPA repositorySectorGatewaysJPA(SectorRepository sectorRepository){
        return new RepositorySectorGatewaysJPA(sectorRepository);
    }

     */
}
