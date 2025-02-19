package com.github.bat333.stockroom.Infra.Service.sector;

import com.github.bat333.stockroom.Application.UseCases.sector.*;
import com.github.bat333.stockroom.Domain.Entities.sector.Sector;
import com.github.bat333.stockroom.Infra.Adapters.sector.SectorEntityMapper;
import com.github.bat333.stockroom.Infra.Dto.sector.DataAllSector;
import com.github.bat333.stockroom.Infra.Dto.sector.DataSector;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.NoArgsConstructor;
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

    public DataAllSector register(@Valid DataSector dataSector) {
        var sector = saveSector.saveSector(new Sector(dataSector.sector(), dataSector.column(), dataSector.shelf(), dataSector.row()));
        return new DataAllSector(sectorEntityMapper.toEntity(sector));
    }

    public Page<DataAllSector> listAllSectors(Pageable pageable) {
        var sectors = allSectors.listAllSectors().stream().map(sector -> new DataAllSector(sectorEntityMapper.toEntity(sector))).toList();
        long totalElements = sectors.size();
        return new PageImpl<>(sectors,pageable,totalElements);
    }

    public DataAllSector getSector(@NotNull Long id) {
        var sector = listSector.listSector(id);
        return new DataAllSector(sectorEntityMapper.toEntity(sector));
    }


    public DataAllSector update(@NotNull Long id,@NotNull  DataSector dataSector) {
        var sector = updateSector.updateSector(id,new Sector(dataSector.sector(), dataSector.column(), dataSector.shelf(), dataSector.row()));
        return new DataAllSector(sectorEntityMapper.toEntity(sector));
    }

    public void delete(@NotNull Long id) {
        deleteSector.deleteSector(id);
    }
}
