package com.github.bat333.stockroom.Adapters.inbound.controller.sector;



import com.github.bat333.stockroom.Application.Service.sector.SectorService;
import com.github.bat333.stockroom.Domain.Entities.sector.dto.DataAllSector;
import com.github.bat333.stockroom.Domain.Entities.sector.dto.DataSector;
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
public class ControllerSector {
    @Autowired
    private SectorService sectorService;


    @PostMapping
    @Transactional
    public ResponseEntity<DataAllSector> registerSector(@RequestBody DataSector dataSector){
        DataAllSector sector = sectorService.saveSector(dataSector);
        return ResponseEntity.created(URI.create("/"+sector.id())).body(sector);
    }

    @GetMapping
    public ResponseEntity<Page<DataAllSector>> getSectors(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(sectorService.listAllActiveSectors(page,size));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DataAllSector> getSector(@PathVariable Long id){
        DataAllSector sector = sectorService.listActiveSector(id);
        return ResponseEntity.ok(sector);
    }

    @PatchMapping("/{id}")
    @Transactional
    public ResponseEntity<DataAllSector> updateSector(@RequestBody DataSector dataSector ,@PathVariable(name = "id") Long id){
        DataAllSector update = sectorService.updateSector(id,dataSector);
        return ResponseEntity.ok(update);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> deleteSector(@PathVariable(name = "id") Long id){
        sectorService.deleteSector(id);
        return ResponseEntity.noContent().build();
    }

}
