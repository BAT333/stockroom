package com.github.bat333.stockroom.service;

import com.github.bat333.stockroom.domain.Sector;
import com.github.bat333.stockroom.model.DataAllSector;
import com.github.bat333.stockroom.model.DataSector;
import com.github.bat333.stockroom.repository.SectorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service("partB")
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
        return repository.findByIdAndActiveTrue(id).map(DataAllSector::new).orElseThrow(() -> new RuntimeException("Setor não encontrado"));
    }

    public DataAllSector update(Long id, DataSector dataSector) {
        Optional<Sector> sector = repository.findByIdAndActiveTrue(id);
        return sector.map(sector1 -> {
            sector1.update(dataSector);
            return new DataAllSector(sector1);
        }).orElseThrow(() -> new RuntimeException("Setor não encontrado"));

    }

    public void delete(Long id) {
        Optional<Sector> sector = repository.findByIdAndActiveTrue(id);
        sector.ifPresentOrElse(Sector::delete,() -> { throw new RuntimeException("Error"); }
        );
    }

    public List<DataAllSector> all() {
        return repository.findByActiveTrue().stream().map(DataAllSector::new).toList();
    }
}
