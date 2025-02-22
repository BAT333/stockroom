package com.github.bat333.stockroom.Infra.Adapters.sector;

import com.github.bat333.stockroom.Application.Gateways.Sector.RepositorySectorGateways;
import com.github.bat333.stockroom.Domain.Entities.sector.Sector;
import com.github.bat333.stockroom.Infra.Persistence.sector.SectorEntity;
import com.github.bat333.stockroom.Infra.Persistence.sector.SectorRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RepositorySectorGatewaysJPA implements RepositorySectorGateways {
    private final SectorRepository sectorRepository;
    private final SectorEntityMapper sectorEntityMapper;

    public RepositorySectorGatewaysJPA(SectorRepository sectorRepository, SectorEntityMapper sectorEntityMapper) {
        this.sectorRepository = sectorRepository;
        this.sectorEntityMapper = sectorEntityMapper;
    }

    @Override
    public Sector saveSector(Sector sector) {
        SectorEntity sectorEntity = sectorRepository.save(sectorEntityMapper.toEntity(sector));
        return sectorEntityMapper.toDomain(sectorEntity);
    }

    @Override
    public Sector listActiveSector(Long id) {
        SectorEntity sectorEntity= sectorRepository.findByIdAndActiveTrue(id).get();
        return sectorEntityMapper.toDomain(sectorEntity);
    }

    @Override
    public List<Sector> listAllActiveSectors() {
        return sectorEntityMapper.toListDomain(sectorRepository.findByActiveTrue());
    }

    @Override
    public Sector listSector(Long id) {
        SectorEntity sectorEntity= sectorRepository.findById(id).get();
        return sectorEntityMapper.toDomain(sectorEntity);
    }

    @Override
    public List<Sector> listAllSectors() {
        return sectorEntityMapper.toListDomain(sectorRepository.findAll());
    }

    @Override
    public Sector updateSector(long id, Sector sector) {
        SectorEntity sectorEntity = sectorRepository.findByIdAndActiveTrue(id).get();
        sectorEntity.update(sectorEntityMapper.toEntity(sector));
        SectorEntity  sectorUpdate=  sectorRepository.save(sectorEntity);
        return sectorEntityMapper.toDomain(sectorUpdate);
    }

    @Override
    public void deleteSector(Long id) {
        SectorEntity sector = sectorRepository.findByIdAndActiveTrue(id).get();
        sector.delete();
        sectorRepository.save(sector);
    }

    @Override
    public boolean existsSectorAndActive(Long id) {
        return sectorRepository.existsByIdAndActiveTrue(id);
    }

    @Override
    public boolean existsBySectorsAndShelfAndColumnAndRow(String sector, String shelf, String column, String row) {
        return sectorRepository.existsBySectorsAndShelfAndColumnAndRow(sector,shelf,column,row);
    }
}
