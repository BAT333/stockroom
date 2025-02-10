package com.github.bat333.stockroom.Application.Gateways.Sector;

import com.github.bat333.stockroom.Domain.Entities.sector.Sector;

import java.util.List;

public interface RepositorySector {

    public Sector saveSector(Sector sector);
    public Sector listActiveSector(Long id);
    public List<Sector> listAllActiveSectors();
    public Sector listSector(Long id);
    public List<Sector> listAllSectors();
    public Sector updateSector(long id, Sector sector);
    public void deleteSector(Long id);
    public boolean existsSectorAndActive(Long id);
    public boolean existsBySectorsAndShelfAndColumnAndRow( String sector,  String shelf,  String column,  String row);
}
