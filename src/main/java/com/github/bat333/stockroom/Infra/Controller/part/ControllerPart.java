package com.github.bat333.stockroom.Infra.Controller.part;


import com.github.bat333.stockroom.Infra.Dto.part.DataAllPart;
import com.github.bat333.stockroom.Infra.Dto.part.DataPart;
import com.github.bat333.stockroom.Infra.Dto.part.DataUpdatePart;
import com.github.bat333.stockroom.Infra.Service.part.PartService;
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
@RequestMapping("api/part")
@Slf4j
public class ControllerPart {

    @Autowired
    private PartService partService;

    @PostMapping("/{id}")
    @Transactional
    public ResponseEntity<DataAllPart> registerPart(@RequestBody DataPart dataPart, @PathVariable(name = "id") Long id){
        log.info("POST entry for part registration");
        DataAllPart part = partService.register(dataPart, id);
        log.info("POST exit to register part ID: {}", part.id());
        return ResponseEntity.created(URI.create("/"+part.id())).body(part);
    }

    @GetMapping
    public ResponseEntity<Page<DataAllPart>> getSectors(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        log.info("GET entry for part list");
        Pageable pageable = PageRequest.of(page, size);
        log.info("GET exit to part list");
        return ResponseEntity.ok( partService.listAllPart(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DataAllPart> getPart(@PathVariable(name = "id") Long id){
        log.info("GET entry for part ID: {}", id);
        DataAllPart part = this.partService.get(id);
        log.info("GET exit for part ID: {}", id);
        return ResponseEntity.ok(part);
    }

    @PatchMapping ("/{id}")
    @Transactional
    public ResponseEntity<DataAllPart> updatePart(@PathVariable(name = "id") Long id, @RequestBody DataUpdatePart updatePart){
        log.info("PATCH entry for part update ID: {}", id);
        DataAllPart part = this.partService.update(id,updatePart);
        log.info("PATCH exit for part update ID: {}", id);
        return ResponseEntity.ok(part);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> deletePart(@PathVariable(name = "id")Long id){
        log.info("DELETE entry for part delete ID: {}", id);
        this.partService.delete(id);
        log.info("DELETE exit for part delete ID: {}", id);
        return  ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<Page<DataAllPart>> searchPart( @RequestParam(defaultValue = "0") int page,
                                                         @RequestParam(defaultValue = "10") int size,@RequestParam(name = "cod",required = false) Long cod,@RequestParam(name = "name",required = false) String name){
        log.info("GET entry for part search ");
        Pageable pageable = PageRequest.of(page, size);
        Page<DataAllPart> search = this.partService.search(cod,name,pageable);
        log.info("GET exit for part search ");
        return ResponseEntity.ok(search);
    }
}
