package com.github.bat333.stockroom.service;

import com.github.bat333.stockroom.domain.Sector;
import com.github.bat333.stockroom.infra.validator.sector.SectorDuplicationValidator;
import com.github.bat333.stockroom.infra.validator.sector.SectorValidator;
import com.github.bat333.stockroom.model.DataAllSector;
import com.github.bat333.stockroom.model.DataSector;
import com.github.bat333.stockroom.repository.SectorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class SectorService {

    private final SectorRepository repository;


    private final SectorValidator validationService;

    private final SectorDuplicationValidator duplicationValidator;


    @CacheEvict(value = "sector", allEntries = true)
    public DataAllSector register(DataSector dataSector) {
        log.info("Registering new sector: {}", dataSector);
        duplicationValidator.validate(dataSector);
        Sector sector = repository.save(new Sector(dataSector));
        log.info("Sector registered successfully with ID: {}", sector.getId());
        return new DataAllSector(sector);
    }


    @Cacheable(value = "sector")
    public Page<DataAllSector> getAll(Pageable pageable) {
        log.info("Made sectors search");
        return repository.findByActiveTrue(pageable).map(DataAllSector::new);
    }

    public DataAllSector getSector(Long id) {
        return new DataAllSector(validationService.validator(id));
    }

    @CachePut(value = "sector", key = "#id")
    public DataAllSector update(Long id, DataSector dataSector) {
        Sector sector = validationService.validator(id);
        sector.update(dataSector);
        repository.save(sector);
        return new DataAllSector(sector);
    }

    @CacheEvict(value = "sector", key = "#id")
    public void delete(Long id) {
        Sector sector = validationService.validator(id);
        sector.delete();
        repository.save(sector);
    }
}
