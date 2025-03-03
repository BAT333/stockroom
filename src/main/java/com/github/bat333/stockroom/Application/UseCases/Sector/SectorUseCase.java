package com.github.bat333.stockroom.Application.UseCases;

import com.github.bat333.stockroom.Domain.Entities.sector.Sector;

import java.util.List;

public interface SectorUseCase {

    public Sector saveSector(Sector sector);
    public Sector listActiveSector(Long id);
    public List<Sector> listAllActiveSectors();
    public Sector updateSector(long id, Sector sector);
    public void deleteSector(Long id);
}
