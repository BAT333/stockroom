package com.github.bat333.stockroom.service;

import com.github.bat333.stockroom.domain.Part;
import com.github.bat333.stockroom.domain.Sector;
import com.github.bat333.stockroom.model.DataAllPart;
import com.github.bat333.stockroom.model.DataPart;
import com.github.bat333.stockroom.model.DataUpdatePart;
import com.github.bat333.stockroom.repository.PartRepository;
import com.github.bat333.stockroom.repository.SectorRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
//fazer autenticação e jogar os erros
@Service("part")
public class PartService {
    @Autowired
    private PartRepository partRepository;

    @Autowired
    private SectorRepository sectorRepository;

    public DataAllPart registration(@Valid DataPart dataPart, Long id) throws IOException {
        Sector sector = sectorRepository.findById(id).get();
        Part part =partRepository.save(new Part(dataPart,sector));
        return new DataAllPart(part);
    }

    public Page<DataAllPart> getAll(Pageable pageable) {
        return partRepository.findByActiveTrue(pageable).map(DataAllPart::new);
    }

    public DataAllPart get(Long id) {
        Optional<Part> part = partRepository.findByIdAndActiveTrue(id);
        return part.map(DataAllPart::new).orElse(null);
    }

    public DataAllPart update(Long id, DataUpdatePart part) {
        Sector sector = sectorRepository.getReferenceById(id);
        return this.partRepository.findById(id)
                .map(existingPart -> {
                    existingPart.update(part,sector);
                    Part savedPart = this.partRepository.save(existingPart);
                    return new DataAllPart(savedPart);
                }).orElse(null);

    }

    public void delete(Long id) {
        this.partRepository.findById(id).ifPresentOrElse(part -> {
                    part.delete();
                    this.partRepository.save(part);
                },
                () -> { throw new RuntimeException("Error"); }
        );
    }

    public Page<DataAllPart> search(Long cod, String name, Pageable pageable) {
        if(name == null && cod == null){
            return this.getAll(pageable);
        }
        return (name != null && cod != null) ?this.getByCodAndName(cod, name,pageable):
                (cod != null)? this.getByCod(cod,pageable) : this.getByName(name,pageable);
    }

    private Page<DataAllPart> getByName(String name, Pageable pageable) {

        return partRepository.findByNameContainingIgnoreCaseAndActiveTrue(name,pageable).map(DataAllPart::new);
    }

    private Page<DataAllPart> getByCod(Long cod, Pageable pageable) {
        return this.partRepository.findByIdAndActiveTrue(cod).map(existingPart -> {
            List<DataAllPart> parts = new ArrayList<>();
            parts.add(new DataAllPart(existingPart));
            return new PageImpl<>(parts,pageable,1);
        }).orElse(new PageImpl<>(List.of(), pageable, 0L));
    }


    private Page<DataAllPart> getByCodAndName(Long cod, String name, Pageable pageable) {
        return this.partRepository.findByIdOrNameContainingIgnoreCaseAndActiveTrue(cod,name,pageable).map(DataAllPart::new);
    }
}
