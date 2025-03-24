package com.github.bat333.stockroom.Application.UseCases.Sector;

import com.github.bat333.stockroom.Domain.Entities.sector.dto.DataAllSector;
import com.github.bat333.stockroom.Domain.Entities.sector.dto.DataSector;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SectorUseCase {

    public DataAllSector saveSector(DataSector sector);
    public DataAllSector listActiveSector(Long id);
    public Page<DataAllSector> listAllActiveSectors(Pageable pageable);
    public DataAllSector updateSector(long id, DataSector sector);
    public void deleteSector(Long id);
}
