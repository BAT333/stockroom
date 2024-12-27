package com.github.bat333.stockroom.service;

import com.github.bat333.stockroom.controller.exceptions.StockExceptions;
import com.github.bat333.stockroom.domain.Sector;
import com.github.bat333.stockroom.model.DataAllSector;
import com.github.bat333.stockroom.model.DataSector;
import com.github.bat333.stockroom.repository.SectorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SectorService {
    @Autowired
    private SectorRepository repository;
    public DataAllSector register(DataSector dataSector) {
        Sector sector =  repository.save(new Sector(dataSector));
        return new DataAllSector(sector);
    }

    public Page<DataAllSector> getAll(Pageable pageable) {
        return repository.findByActiveTrue(pageable).map(DataAllSector::new);
    }

    public DataAllSector getSector(Long id) {
        return repository.findByIdAndActiveTrue(id).map(DataAllSector::new).orElseThrow( () -> new StockExceptions("Reported Selector Not Found "));
    }

    public DataAllSector update(Long id, DataSector dataSector) {
        Optional<Sector> sector = repository.findByIdAndActiveTrue(id);
        return sector.map(sector1 -> {
            sector1.update(dataSector);
            return new DataAllSector(sector1);
        }).orElseThrow( () -> new StockExceptions("Reported Selector Not Found "));

    }

    public void delete(Long id) {
        Optional<Sector> sector = repository.findByIdAndActiveTrue(id);
        sector.ifPresentOrElse(Sector::delete,() -> { throw new StockExceptions("Reported Selector Not Found "); }
        );
    }
}
