package com.github.bat333.stockroom.start.Infra.Service.sector;

import com.github.bat333.stockroom.Application.UseCases.sector.*;
import com.github.bat333.stockroom.start.Application.UseCases.sector.*;
import com.github.bat333.stockroom.start.Domain.Entities.sector.Sector;
import com.github.bat333.stockroom.start.Infra.Adapters.sector.SectorEntityMapper;
import com.github.bat333.stockroom.start.Infra.Dto.sector.DataAllSector;
import com.github.bat333.stockroom.start.Infra.Dto.sector.DataSector;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
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
        var sector = saveSector.saveSector(new Sector(dataSector.sector(), dataSector.column(), dataSector.shelf(), dataSector.row()));
        return new DataAllSector(sectorEntityMapper.toEntity(sector));
    }

    @Cacheable(value = "sector")
    public Page<DataAllSector> listAllSectors(Pageable pageable) {
        var sectors = allSectors.listAllSectors().stream().map(sector -> new DataAllSector(sectorEntityMapper.toEntity(sector))).toList();
        long totalElements = sectors.size();
        return new PageImpl<>(sectors,pageable,totalElements);
    }
    @Cacheable(value = "sector", key = "#id")
    public DataAllSector getSector(@NotNull Long id) {
        var sector = listSector.listSector(id);
        return new DataAllSector(sectorEntityMapper.toEntity(sector));
    }

    @CachePut(value = "sector", key = "#id")
    public DataAllSector update(@NotNull Long id,@NotNull  DataSector dataSector) {
        var sector = updateSector.updateSector(id,new Sector(dataSector.sector(), dataSector.column(), dataSector.shelf(), dataSector.row()));
        return new DataAllSector(sectorEntityMapper.toEntity(sector));
    }
    @CacheEvict(value = "sector", key = "#id")
    public void delete(@NotNull Long id) {
        deleteSector.deleteSector(id);
    }
}
