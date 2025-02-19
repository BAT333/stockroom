package com.github.bat333.stockroom.Infra.Controller.part;


import com.github.bat333.stockroom.Infra.Dto.part.DataAllPart;
import com.github.bat333.stockroom.Infra.Dto.part.DataPart;
import com.github.bat333.stockroom.Infra.Dto.part.DataUpdatePart;
import com.github.bat333.stockroom.Infra.Service.part.PartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.net.URI;


@RestController
@RequestMapping("api/part")
public class ControllerPart {

    @Autowired
    private PartService partService;

    @PostMapping
    @Transactional
    public ResponseEntity<DataAllPart> registerPart(@RequestBody DataPart dataPart, @PathVariable(name = "id") Long id){
        DataAllPart part = partService.register(dataPart, id);
        return ResponseEntity.created(URI.create("/"+part.id())).body(part);
    }

    @GetMapping
    public ResponseEntity<Page<DataAllPart>> getSectors(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok( partService.listAllPart(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DataAllPart> getPart(@PathVariable(name = "id") Long id){
        DataAllPart part = this.partService.get(id);
        return ResponseEntity.ok(part);
    }

    @PatchMapping ("/{id}")
    @Transactional
    public ResponseEntity<DataAllPart> updatePart(@PathVariable(name = "id") Long id, @RequestBody DataUpdatePart updatePart){
        DataAllPart part = this.partService.update(id,updatePart);
        return ResponseEntity.ok(part);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> deletePart(@PathVariable(name = "id")Long id){
        this.partService.delete(id);
        return  ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<Page<DataAllPart>> searchPart( @RequestParam(defaultValue = "0") int page,
                                                         @RequestParam(defaultValue = "10") int size,@RequestParam(name = "cod",required = false) Long cod,@RequestParam(name = "name",required = false) String name){
        Pageable pageable = PageRequest.of(page, size);
        Page<DataAllPart> search = this.partService.search(cod,name,pageable);
        return ResponseEntity.ok(search);
    }


    /*







    @GetMapping("/search")
    public ResponseEntity<Page<DataAllPart>> searchPart(@RequestParam(name = "cod",required = false) Long cod,@RequestParam(name = "name",required = false) String name ,@PageableDefault(sort = {"id"}) Pageable pageable){
        Page<DataAllPart> allParts = this.service.search(cod,name,pageable);
        return ResponseEntity.ok(allParts);
    }

     */


}
