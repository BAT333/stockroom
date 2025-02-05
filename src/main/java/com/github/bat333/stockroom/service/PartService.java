package com.github.bat333.stockroom.service;

import com.github.bat333.stockroom.domain.Part;
import com.github.bat333.stockroom.domain.Sector;
import com.github.bat333.stockroom.infra.exceptions.SectorNotFoundException;
import com.github.bat333.stockroom.infra.validator.part.PartDuplicationValidator;
import com.github.bat333.stockroom.infra.validator.part.PartValidator;
import com.github.bat333.stockroom.infra.validator.sector.SectorValidator;
import com.github.bat333.stockroom.model.DataAllPart;
import com.github.bat333.stockroom.model.DataPart;
import com.github.bat333.stockroom.model.DataUpdatePart;
import com.github.bat333.stockroom.repository.PartRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
@Service
@Slf4j
@RequiredArgsConstructor

public class PartService {

    private final PartRepository partRepository;
    private final  ImageService imageService;
    private final SectorValidator sectorValidator;
    private final PartDuplicationValidator duplicationValidator;
    private final PartValidator partValidator;

    @CacheEvict(value = "part", allEntries = true)
    public DataAllPart registration(@Valid DataPart dataPart, Long id) throws IOException {
        Sector sector = sectorValidator.validator(id);
        byte[] img = imageService.resizeAndCompressImage(dataPart.image(), 800, 800, 0.7f);
        duplicationValidator.validate(dataPart,sector);
        Part part =partRepository.save(new Part(dataPart,sector,img));
        log.info("Part with ID {} successfully registered. Data: {}, Sector: {}", part.getId(), dataPart, sector);
        return new DataAllPart(part);
    }



    @Cacheable(value = "part")
    public Page<DataAllPart> getAll(Pageable pageable) {
        log.info("Made parts search" );
        return partRepository.findByActiveTrue(pageable).map(DataAllPart::new);
    }
    @Cacheable(value = "part", key = "#id")
    public DataAllPart get(Long id) {

        Part part = partValidator.validator(id);
        return new DataAllPart(part);
    }

    @CacheEvict(value = "part", allEntries = true)
    public DataAllPart update(Long id, DataUpdatePart part) {
        Part partReturn = partValidator.validator(id);
        try{
            return this.updateAndSector(part,partReturn);
        }catch(SectorNotFoundException e){
            partReturn.update(part,null);
            return new DataAllPart(partReturn);
        }
    }

    private DataAllPart updateAndSector(DataUpdatePart part, Part partReturn) {

        Sector sector = sectorValidator.validator(part.sector());
        partReturn.update(part,sector);
        return new DataAllPart(partReturn);
    }

    @CacheEvict(value = "part", allEntries = true)
    public void delete(Long id) {

        Part part= partValidator.validator(id);
        part.delete();
        this.partRepository.save(part);

    }

    @Cacheable(value = "part", key = "'search:' + #cod + ':' + #name + ':' + #pageable.pageNumber + ':' + #pageable.pageSize")
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
