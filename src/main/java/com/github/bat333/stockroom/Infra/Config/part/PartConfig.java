package com.github.bat333.stockroom.Infra.Config.part;

import com.github.bat333.stockroom.Application.Gateways.Part.RepositoryPartGateways;
import com.github.bat333.stockroom.Application.Gateways.Sector.RepositorySectorGateways;
import com.github.bat333.stockroom.Application.Gateways.ValueObjects.ImageProcessing;
import com.github.bat333.stockroom.Application.UseCases.Part.*;
import com.github.bat333.stockroom.Infra.Adapters.part.PartEntityMapper;
import com.github.bat333.stockroom.Infra.Adapters.sector.SectorEntityMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PartConfig {

    @Bean
    public SavePart createUser(RepositoryPartGateways repositoryPart, RepositorySectorGateways sectorGateways, ImageProcessing image){
        return new SavePart(repositoryPart,sectorGateways,image);
    }

    @Bean
    public DeletePart deletePart(RepositoryPartGateways repositoryPart){
        return new DeletePart(repositoryPart);
    }
    @Bean
    public ListAllParts listAllParts(RepositoryPartGateways repositoryPart){
        return new ListAllParts(repositoryPart);
    }
    @Bean
    public ListPart listPart(RepositoryPartGateways repositoryPart){
        return new ListPart(repositoryPart);
    }

    @Bean
    public UpdatePart updatePart(RepositoryPartGateways repositoryPart, RepositorySectorGateways sectorGateways){
        return new UpdatePart(repositoryPart,sectorGateways);
    }
    @Bean
    public SearchByPart searchByPart(RepositoryPartGateways repositoryPart){
        return new SearchByPart(repositoryPart);
    }



    @Bean
    public PartEntityMapper partEntityMapper(SectorEntityMapper entityMapper){
        return new PartEntityMapper(entityMapper);
    }
  /*
    @Bean
    public RepositoryPartGatewaysJPA repositoryPartGatewaysJPA(PartRepository partRepository){
        return new RepositoryPartGatewaysJPA(partRepository);
    }

   */
}
