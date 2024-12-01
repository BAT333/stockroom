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
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
//fazer autenticação e jogar os erros
@Service
public class PartService {
    @Autowired
    private PartRepository partRepository;

    @Autowired
    private SectorRepository sectorRepository;

    public DataAllPart registration(@Valid DataPart dataPart, Long id) {
        Sector sector = sectorRepository.findById(id).get();
        Part part =partRepository.save(new Part(dataPart,sector));
        return new DataAllPart(part);
    }

    public List<DataAllPart> getAll() {
        return partRepository.findByActiveTrue().stream().map(DataAllPart::new).toList();
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
        this.partRepository.findById(id).ifPresentOrElse(
                Part::delete,
                () -> { throw new RuntimeException("Error"); }
        );
    }

    public List<DataAllPart> search(Long cod, String name) {
        System.out.println("cod: "+ cod +"  name:"+ name);
         if(name == null && cod == null){
             return this.getAll();
         }
         return (name != null && cod != null) ?this.getByCodAndName(cod, name):
                 (cod != null)? this.getByCod(cod) : this.getByName(name);
    }

    private List<DataAllPart> getByName(String name) {

        return partRepository.findByNameContainingIgnoreCaseAndActiveTrue(name).stream().map(DataAllPart::new).toList();
    }

    private List<DataAllPart> getByCod(Long cod) {
        return this.partRepository.findByIdAndActiveTrue(cod).map(existingPart -> {
            List<DataAllPart> parts = new ArrayList<>();
            parts.add(new DataAllPart(existingPart));
            return parts;
        }).orElse(null);
    }


    private List<DataAllPart> getByCodAndName(Long cod, String name) {
        return this.partRepository.findByIdOrNameContainingIgnoreCaseAndActiveTrue(cod,name).stream().map(DataAllPart::new).toList();
    }
}
