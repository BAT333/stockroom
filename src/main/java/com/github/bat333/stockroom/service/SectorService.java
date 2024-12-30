package com.github.bat333.stockroom.service;

import com.github.bat333.stockroom.controller.exceptions.SectorNotFoundException;
import com.github.bat333.stockroom.controller.exceptions.StockExceptions;
import com.github.bat333.stockroom.domain.Sector;
import com.github.bat333.stockroom.model.DataAllSector;
import com.github.bat333.stockroom.model.DataSector;
import com.github.bat333.stockroom.repository.SectorRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class SectorService {
    @Autowired
    private SectorRepository repository;


    @CacheEvict(value = "sector", allEntries = true)
    public DataAllSector register(DataSector dataSector) {
        if(repository.existsBySectorsAndShelfAndColumnAndRow(dataSector.sector(),dataSector.shelf(),dataSector.column(),dataSector.row())){
            log.error("Sector already registered with sector: {}, shelf: {}, column: {}, row: {}",
                    dataSector.sector(), dataSector.shelf(), dataSector.column(), dataSector.row());
            throw new StockExceptions("Sector already registered");
        }
        Sector sector =  repository.save(new Sector(dataSector));
        return new DataAllSector(sector);
    }


    @Cacheable(value = "sector")
    public Page<DataAllSector> getAll(Pageable pageable) {
        log.info("Made sectors search" );
        return repository.findByActiveTrue(pageable).map(DataAllSector::new);
    }

    public DataAllSector getSector(Long id) {
        return repository.findByIdAndActiveTrue(id)
                .map(DataAllSector::new)
                .orElseThrow(() -> {
                    log.error("Sector with ID {} not found or is inactive in the system.", id);
                    return new SectorNotFoundException("Reported Sector with ID " + id + " not found or is inactive.");
                });
    }

    @CachePut(value = "sector", key = "#id")
    public DataAllSector update(Long id, DataSector dataSector) {
        Optional<Sector> sector = repository.findByIdAndActiveTrue(id);
        return sector.map(sector1 -> {
            sector1.update(dataSector);
            return new DataAllSector(sector1);
        }).orElseThrow( () -> {
            log.error("Sector with ID {} not found or is inactive in the system.", id);
            return new SectorNotFoundException("Reported Sector with ID " + id + " not found or is inactive.");
        });

    }

    @CacheEvict(value = "sector", key = "#id")
    public void delete(Long id) {
        Optional<Sector> sector = repository.findByIdAndActiveTrue(id);
        sector.ifPresentOrElse(Sector::delete,() -> {
            log.error("Sector with ID {} not found or is inactive in the system.", id);
            throw  new SectorNotFoundException("Reported Sector with ID " + id + " not found or is inactive."); }
        );
    }
}
