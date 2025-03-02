package com.github.bat333.stockroom.start.Infra.Controller.sector;


import com.github.bat333.stockroom.start.Infra.Dto.sector.DataAllSector;
import com.github.bat333.stockroom.start.Infra.Dto.sector.DataSector;
import com.github.bat333.stockroom.start.Infra.Service.sector.SectorService;
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
        DataAllSector sector = sectorService.register(dataSector);
        return ResponseEntity.created(URI.create("/"+sector.id())).body(sector);
    }

    @GetMapping
    public ResponseEntity<Page<DataAllSector>> getSectors(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(sectorService.listAllSectors(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DataAllSector> getSector(@PathVariable Long id){
        DataAllSector sector = sectorService.getSector(id);
        return ResponseEntity.ok(sector);
    }

    @PatchMapping("/{id}")
    @Transactional
    public ResponseEntity<DataAllSector> updateSector(@RequestBody DataSector dataSector ,@PathVariable(name = "id") Long id){
        DataAllSector update = sectorService.update(id,dataSector);
        return ResponseEntity.ok(update);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> deleteSector(@PathVariable(name = "id") Long id){
        sectorService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
