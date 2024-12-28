package com.github.bat333.stockroom.service;

import com.github.bat333.stockroom.controller.exceptions.SectorNotFoundException;
import com.github.bat333.stockroom.controller.exceptions.StockExceptions;
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
@Service
public class PartService {
    @Autowired
    private PartRepository partRepository;

    @Autowired
    private SectorRepository sectorRepository;

    @Autowired
    private  ImageService imageService;

    public DataAllPart registration(@Valid DataPart dataPart, Long id){
        Sector sector = sectorRepository.findById(id).orElseThrow( () -> new SectorNotFoundException("Reported Sector Not Found "));
        byte[] img;
        if(partRepository.existsByCodAndNameAndSector(dataPart.cod(),dataPart.name(),sector)){
            throw new StockExceptions("Part with code " + dataPart.cod() + " and name " + dataPart.name() + " already registered in this sector.");
        }
        try {
            img = imageService.resizeAndCompressImage(dataPart.image(), 800, 800, 0.7f);
        } catch (IOException e) {
            throw new StockExceptions("Unrederized image",e);
        }
        Part part =partRepository.save(new Part(dataPart,sector,img));
        return new DataAllPart(part);
    }

    public Page<DataAllPart> getAll(Pageable pageable) {
        return partRepository.findByActiveTrue(pageable).map(DataAllPart::new);
    }

    public DataAllPart get(Long id) {
        Optional<Part> part = partRepository.findByIdAndActiveTrue(id);
        return part.map(DataAllPart::new).orElseThrow( () -> new SectorNotFoundException("Reported Part Not Found "));
    }

    public DataAllPart update(Long id, DataUpdatePart part) {
        System.out.println(part.cod());
        return this.partRepository.findById(id)
                .map(existingPart -> {
                    sectorRepository.findByIdAndActiveTrue(part.sector()).ifPresentOrElse(
                            isPresent-> {
                                existingPart.update(part, isPresent);
                                this.partRepository.save(existingPart);
                            },
                            () -> {
                                existingPart.update(part, null);
                                this.partRepository.save(existingPart);}
                    );
                    return new DataAllPart(existingPart);
                }).orElseThrow( () -> new SectorNotFoundException("Reported Part Not Found "));

    }

    public void delete(Long id) {
        this.partRepository.findById(id).ifPresentOrElse( part -> {
                    part.delete();
                    this.partRepository.save(part);
                },
                () -> { throw new SectorNotFoundException("Reported Part Not Found"); }
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
        return this.partRepository.findByCodAndActiveTrue(cod).map(existingPart -> {
            List<DataAllPart> parts = new ArrayList<>();
            parts.add(new DataAllPart(existingPart));
            return new PageImpl<>(parts,pageable,1);
        }).orElse(new PageImpl<>(List.of(), pageable, 0L));
    }


    private Page<DataAllPart> getByCodAndName(Long cod, String name, Pageable pageable) {
        return this.partRepository.findByCodOrNameContainingIgnoreCaseAndActiveTrue(cod,name,pageable).map(DataAllPart::new);
    }
}
