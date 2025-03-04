package com.github.bat333.stockroom.Infra.Controller.sector;


import com.github.bat333.stockroom.Infra.Dto.sector.DataAllSector;
import com.github.bat333.stockroom.Infra.Dto.sector.DataSector;
import com.github.bat333.stockroom.Infra.Service.sector.SectorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/sector")
@Slf4j
public class ControllerSector {
    @Autowired
    private SectorService sectorService;


    @PostMapping
    @Transactional
    public ResponseEntity<DataAllSector> registerSector(@RequestBody DataSector dataSector){
        log.info("POST entry for sector registration");
        DataAllSector sector = sectorService.register(dataSector);
        log.info("POST exit to register sector ID: {}", sector.id());
        return ResponseEntity.created(URI.create("/"+sector.id())).body(sector);
    }

    @GetMapping
    public ResponseEntity<Page<DataAllSector>> getSectors(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        log.info("GET entry for sector list");
        Pageable pageable = PageRequest.of(page, size);
        log.info("GET exit to sector list");
        return ResponseEntity.ok(sectorService.listAllSectors(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DataAllSector> getSector(@PathVariable Long id){
        log.info("GET entry for sector ID: {}", id);
        DataAllSector sector = sectorService.getSector(id);
        log.info("GET exit for sector ID: {}", id);
        return ResponseEntity.ok(sector);
    }

    @PatchMapping("/{id}")
    @Transactional
    public ResponseEntity<DataAllSector> updateSector(@RequestBody DataSector dataSector ,@PathVariable(name = "id") Long id){
        log.info("PATCH entry for sector update ID: {}", id);
        DataAllSector update = sectorService.update(id,dataSector);
        log.info("PATCH exit for sector update ID: {}", id);
        return ResponseEntity.ok(update);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> deleteSector(@PathVariable(name = "id") Long id){
        log.info("DELETE entry for sector delete ID: {}", id);
        sectorService.delete(id);
        log.info("DELETE exit for sector delete ID: {}", id);
        return ResponseEntity.noContent().build();
    }

}
