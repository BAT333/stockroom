package com.github.bat333.stockroom.controller;

import com.github.bat333.stockroom.domain.Sector;
import com.github.bat333.stockroom.model.DataAllSector;
import com.github.bat333.stockroom.model.DataSector;
import com.github.bat333.stockroom.service.SectorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/sector")
public class ControllerSector {
    @Autowired
    private SectorService sectorService;


    @PostMapping
    public ResponseEntity<DataAllSector> registerSector(@RequestBody @Valid DataSector dataSector){
        DataAllSector sector = sectorService.register(dataSector);
        return ResponseEntity.created(URI.create("/"+sector.id())).body(sector);
    }

    @GetMapping
    public ResponseEntity<List<DataAllSector>> getAllSector(){
        return ResponseEntity.ok(sectorService.getAll());
    }
    @GetMapping("/{id}")
    public ResponseEntity<DataAllSector> getSector(@PathVariable Long id){
        return ResponseEntity.ok(sectorService.getSector(id));
    }
    @PatchMapping("/{id}")
    public ResponseEntity<DataAllSector> updateSector(@RequestBody DataSector dataSector ,@PathVariable(name = "id") Long id){
        return ResponseEntity.ok(sectorService.update(id,dataSector));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSector(@PathVariable(name = "id") Long id){
        sectorService.delete(id);
        return ResponseEntity.noContent().build();
    }
}