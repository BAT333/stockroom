package com.github.bat333.stockroom.Infra.Adapters.sector;

import com.github.bat333.stockroom.Application.Gateways.Sector.RepositorySectorGateways;
import com.github.bat333.stockroom.Domain.Entities.sector.Sector;
import com.github.bat333.stockroom.Infra.Persistence.sector.SectorEntity;
import com.github.bat333.stockroom.Infra.Persistence.sector.SectorRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class RepositorySectorGatewaysJPA implements RepositorySectorGateways {
    private final SectorRepository sectorRepository;
    private final SectorEntityMapper sectorEntityMapper;

    public RepositorySectorGatewaysJPA(SectorRepository sectorRepository, SectorEntityMapper sectorEntityMapper) {
        this.sectorRepository = sectorRepository;
        this.sectorEntityMapper = sectorEntityMapper;
    }

    @Override
    public Sector saveSector(Sector sector) {
        log.info("starting the registration sector {}", sector);
        SectorEntity sectorEntity = sectorRepository.save(sectorEntityMapper.toEntity(sector));
        log.info("successful registration sector {}", sector);
        return sectorEntityMapper.toDomain(sectorEntity);
    }

    @Override
    public Sector listActiveSector(Long id) {
        log.info("starting the list sector with ID: {}", id);
        SectorEntity sectorEntity= sectorRepository.findByIdAndActiveTrue(id).get();
        log.info("successful list sector with ID: {}", id);
        return sectorEntityMapper.toDomain(sectorEntity);
    }

    @Override
    public List<Sector> listAllActiveSectors() {
        log.info("starting the list sector");
        return sectorEntityMapper.toListDomain(sectorRepository.findByActiveTrue());
    }

    @Override
    public Sector listSector(Long id) {
        log.info("starting the list sector with ID: {}", id);
        SectorEntity sectorEntity= sectorRepository.findById(id).get();
        log.info("successful list sector with ID: {}", id);
        return sectorEntityMapper.toDomain(sectorEntity);
    }

    @Override
    public List<Sector> listAllSectors() {
        log.info("starting the list");
        return sectorEntityMapper.toListDomain(sectorRepository.findAll());
    }

    @Override
    public Sector updateSector(long id, Sector sector) {
        log.info("starting the update sector with ID: {}", id);
        SectorEntity sectorEntity = sectorRepository.findByIdAndActiveTrue(id).get();
        sectorEntity.update(sectorEntityMapper.toEntity(sector));
        SectorEntity  sectorUpdate=  sectorRepository.save(sectorEntity);
        log.info("successful the update sector with ID: {}", id);
        return sectorEntityMapper.toDomain(sectorUpdate);
    }

    @Override
    public void deleteSector(Long id) {
        log.info("starting the delete sector with ID: {}", id);
        SectorEntity sector = sectorRepository.findByIdAndActiveTrue(id).get();
        sector.delete();
        log.info("successful the delete sector with ID: {}", id);
        sectorRepository.save(sector);
    }

    @Override
    public boolean existsSectorAndActive(Long id) {
        log.info("starting the exist sector ID: {}", id);
        return sectorRepository.existsByIdAndActiveTrue(id);
    }

    @Override
    public boolean existsBySectorsAndShelfAndColumnAndRow(String sector, String shelf, String column, String row) {
        log.info("starting the exist sector with sector , shelf , column , row : {},{},{},{}", sector,shelf,column,row);
        return sectorRepository.existsBySectorsAndShelfAndColumnAndRow(sector,shelf,column,row);
    }
}
