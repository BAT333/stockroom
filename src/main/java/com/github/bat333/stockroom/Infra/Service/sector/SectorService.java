package com.github.bat333.stockroom.Infra.Service.sector;

import com.github.bat333.stockroom.Application.UseCases.sector.*;
import com.github.bat333.stockroom.Domain.Entities.sector.Sector;
import com.github.bat333.stockroom.Infra.Adapters.sector.SectorEntityMapper;
import com.github.bat333.stockroom.Infra.Dto.sector.DataAllSector;
import com.github.bat333.stockroom.Infra.Dto.sector.DataSector;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SectorService {
    private final SaveSector saveSector;
    private final ListAllSectors allSectors;
    private final SectorEntityMapper sectorEntityMapper;
    private final ListSector listSector;
    private final UpdateSector updateSector;
    private final DeleteSector deleteSector;

    public SectorService(SaveSector saveSector, ListAllSectors allSectors, SectorEntityMapper sectorEntityMapper, ListSector listSector, UpdateSector updateSector, DeleteSector deleteSector) {
        this.saveSector = saveSector;
        this.allSectors = allSectors;
        this.sectorEntityMapper = sectorEntityMapper;
        this.listSector = listSector;
        this.updateSector = updateSector;
        this.deleteSector = deleteSector;
    }

    @CacheEvict(value = "sector", allEntries = true)
    public DataAllSector register(@Valid DataSector dataSector) {
        log.info("Service entering to register sector");
        var sector = saveSector.saveSector(new Sector(dataSector.sector(), dataSector.column(), dataSector.shelf(), dataSector.row()));
        log.info("Service leaving to register sector");
        return new DataAllSector(sectorEntityMapper.toEntity(sector));
    }

    @Cacheable(value = "sector")
    public Page<DataAllSector> listAllSectors(Pageable pageable) {
        log.info("Service entering to list sector");
        var sectors = allSectors.listAllSectors().stream().map(sector -> new DataAllSector(sectorEntityMapper.toEntity(sector))).toList();
        long totalElements = sectors.size();
        log.info("Service leaving to list sector");
        return new PageImpl<>(sectors,pageable,totalElements);
    }
    @Cacheable(value = "sector", key = "#id")
    public DataAllSector getSector(@NotNull Long id) {
        log.info("Service entering to sector ID: {}", id);
        var sector = listSector.listSector(id);
        log.info("Service leaving to sector ID: {}", id);
        return new DataAllSector(sectorEntityMapper.toEntity(sector));
    }

    @CachePut(value = "sector", key = "#id")
    public DataAllSector update(@NotNull Long id,@NotNull  DataSector dataSector) {
        log.info("Service entering to update sector ID: {}", id);
        var sector = updateSector.updateSector(id,new Sector(dataSector.sector(), dataSector.column(), dataSector.shelf(), dataSector.row()));
        log.info("Service leaving to update sector ID: {}", id);
        return new DataAllSector(sectorEntityMapper.toEntity(sector));
    }
    @CacheEvict(value = "sector", key = "#id")
    public void delete(@NotNull Long id) {
        log.info("Service entering to delete sector ID: {}", id);
        deleteSector.deleteSector(id);
        log.info("Service leaving to delete sector ID: {}", id);
    }
}
