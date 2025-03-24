package com.github.bat333.stockroom.Application.Service.sector;

import com.github.bat333.stockroom.Application.UseCases.Sector.SectorUseCase;
import com.github.bat333.stockroom.Domain.Entities.sector.RepositorySectorGateways;
import com.github.bat333.stockroom.Domain.Entities.sector.Sector;
import com.github.bat333.stockroom.Domain.Entities.sector.SectorFactory;
import com.github.bat333.stockroom.Domain.Entities.sector.dto.DataAllSector;
import com.github.bat333.stockroom.Domain.Entities.sector.dto.DataSector;
import com.github.bat333.stockroom.Infrastructure.exception.SectorExists;
import com.github.bat333.stockroom.useful.SectorEntityMapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class SectorService implements SectorUseCase {
    private final RepositorySectorGateways sectorGateways;
    private final SectorEntityMapper sectorEntityMapper;

    public SectorService(RepositorySectorGateways sectorGateways, SectorEntityMapper sectorEntityMapper) {
        this.sectorGateways = sectorGateways;
        this.sectorEntityMapper = sectorEntityMapper;
    }

    @Override
    @CacheEvict(value = "sector", allEntries = true)
    public DataAllSector saveSector(DataSector sector) {
        if(sectorGateways.existsBySectorsAndShelfAndColumnAndRow(sector.sector(), sector.shelf(), sector.column(), sector.row())){
            throw new SectorExists(String.format("Sector with sector '%s' and Shelf '%s' and Column '%s' and Row '%s' already exists.",
                    sector.sector(),sector.shelf(),sector.column(),sector.row()));
        }

        Sector sectorCreate = SectorFactory.createSector(sector.sector(),sector.column(),sector.shelf(),sector.row());
        Sector sectorSave = sectorGateways.saveSector(sectorCreate);
        return sectorEntityMapper.toDTOSector(sectorSave);
    }

    @Override
    @Cacheable(value = "sector", key = "#id")
    public DataAllSector listActiveSector(Long id) {
        if(!sectorGateways.existsSectorAndActive(id)){
            throw new SectorExists("This sector does not exist");
        }
        Sector sector = sectorGateways.listActiveSector(id);
        return sectorEntityMapper.toDTOSector(sector);
    }

    @Override
    @Cacheable(value = "sector", key = "'sector:' + #page + ':' + #size")
    public Page<DataAllSector> listAllActiveSectors(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        //arrumar
        var sectors = sectorGateways.listAllSectors().stream().map(sectorEntityMapper::toDTOSector).toList();
        long totalElements = sectors.size();
        return new PageImpl<>(sectors,pageable,totalElements);
    }

    @Override
    @CachePut(value = "sector", key = "#id")
    public DataAllSector updateSector(long id, DataSector sector) {
        if(!this.sectorGateways.existsSectorAndActive(id)){
            throw new SectorExists("This sector does not exist");
        }
        if(sectorGateways.existsBySectorsAndShelfAndColumnAndRow(sector.sector(), sector.shelf(), sector.column(), sector.row())){
            throw new SectorExists(String.format("Sector with sector '%s' and Shelf '%s' and Column '%s' and Row '%s' already exists.",
                    sector.sector(),sector.shelf(),sector.column(),sector.row()));
        }

        Sector sectorUpdate = sectorGateways.updateSector(id, SectorFactory.createSector(sector.sector(),sector.column(),sector.shelf(),sector.row()));
        return sectorEntityMapper.toDTOSector(sectorUpdate);
    }

    @Override
    @CacheEvict(value = "sector", key = "#id")
    public void deleteSector(Long id) {
        if(!sectorGateways.existsSectorAndActive(id)){
            throw new SectorExists("This sector does not exist");
        }
        sectorGateways.deleteSector(id);
    }
}
