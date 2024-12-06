package com.github.bat333.stockroom.controller;

import com.github.bat333.stockroom.domain.Part;
import com.github.bat333.stockroom.model.DataAllPart;
import com.github.bat333.stockroom.model.DataPart;
import com.github.bat333.stockroom.model.DataUpdatePart;
import com.github.bat333.stockroom.service.PartService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URI;
import java.util.List;


@RestController
@RequestMapping("api/part")
public class ControllerPart {

    private final PartService service;
    @Autowired
    public ControllerPart(@Qualifier("part") PartService service) {
        this.service = service;
    }


    @PostMapping("{id}")
    public ResponseEntity<DataAllPart> partRegistration(@Valid @ModelAttribute DataPart dataPart, @PathVariable(name = "id") Long id) throws IOException {
        DataAllPart part = this.service.registration(dataPart, id);
        return ResponseEntity.created(URI.create("/"+part.id())).body(part) ;
    }

    @GetMapping("/{id}")
    public ResponseEntity<DataAllPart> getPart(@PathVariable(name = "id") Long id){
        return ResponseEntity.ok(this.service.get(id));
    }

    @PatchMapping ("/{id}")
    public ResponseEntity<DataAllPart> updatePart(@PathVariable(name = "id") Long id, @RequestBody DataUpdatePart part){
        return ResponseEntity.ok(this.service.update(id,part));

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePart(@PathVariable(name = "id")Long id){
        this.service.delete(id);
        return  ResponseEntity.noContent().build();
    }
    @GetMapping()
    public ResponseEntity<Page<DataAllPart>> getAllPart( @PageableDefault(sort = {"id"}) Pageable pageable){
        return ResponseEntity.ok(this.service.getAll(pageable));
    }

    @GetMapping("/search")
    public ResponseEntity<Page<DataAllPart>> searchPart(@RequestParam(name = "cod",required = false) Long cod,@RequestParam(name = "name",required = false) String name ,@PageableDefault(sort = {"id"}) Pageable pageable){
        return ResponseEntity.ok(this.service.search(cod,name,pageable));
    }


}
