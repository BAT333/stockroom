package com.github.bat333.stockroom.Adapters.inbound.controller.part;


import com.github.bat333.stockroom.Application.Service.part.PartService;
import com.github.bat333.stockroom.Domain.Entities.part.dto.DataAllPart;
import com.github.bat333.stockroom.Domain.Entities.part.dto.DataPart;
import com.github.bat333.stockroom.Domain.Entities.part.dto.DataUpdatePart;
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
public class ControllerPart {

    @Autowired
    private PartService partService;

    @PostMapping("/{id}")
    @Transactional
    public ResponseEntity<DataAllPart> registerPart(@RequestBody DataPart dataPart, @PathVariable(name = "id") Long id){
        DataAllPart part = partService.savePart(dataPart, id);
        return ResponseEntity.created(URI.create("/"+part.id())).body(part);
    }

    @GetMapping
    public ResponseEntity<Page<DataAllPart>> getSectors(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok( partService.listAllPart(page,size));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DataAllPart> getPart(@PathVariable(name = "id") Long id){
        DataAllPart part = this.partService.listActivePart(id);
        return ResponseEntity.ok(part);
    }

    @PatchMapping ("/{id}")
    @Transactional
    public ResponseEntity<DataAllPart> updatePart(@PathVariable(name = "id") Long id, @RequestBody DataUpdatePart updatePart){
        DataAllPart part = this.partService.updatePart(id,updatePart);
        return ResponseEntity.ok(part);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> deletePart(@PathVariable(name = "id")Long id){
        this.partService.deletePart(id);
        return  ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<Page<DataAllPart>> searchPart( @RequestParam(defaultValue = "0") int page,
                                                         @RequestParam(defaultValue = "10") int size,@RequestParam(name = "cod",required = false) Long cod,@RequestParam(name = "name",required = false) String name){

        Page<DataAllPart> search = this.partService.searchPart(name,cod,page,size);
        return ResponseEntity.ok(search);
    }
}
