package com.github.bat333.stockroom.Application.Service.part;

import com.github.bat333.stockroom.Adapters.outbound.storage.ImageProcessing;
import com.github.bat333.stockroom.Application.UseCases.Part.PartUseCase;
import com.github.bat333.stockroom.Domain.Entities.part.Part;
import com.github.bat333.stockroom.Domain.Entities.part.PartFactory;
import com.github.bat333.stockroom.Domain.Entities.part.RepositoryPartGateways;
import com.github.bat333.stockroom.Domain.Entities.part.dto.DataAllPart;
import com.github.bat333.stockroom.Domain.Entities.part.dto.DataPart;
import com.github.bat333.stockroom.Domain.Entities.part.dto.DataUpdatePart;
import com.github.bat333.stockroom.Domain.Entities.sector.RepositorySectorGateways;
import com.github.bat333.stockroom.Infrastructure.exception.ImageException;
import com.github.bat333.stockroom.Infrastructure.exception.PartExists;
import com.github.bat333.stockroom.Infrastructure.exception.SectorExists;
import com.github.bat333.stockroom.useful.PartEntityMapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class PartService implements PartUseCase {
    //if you use sector, use usecase, always use something, use usecase
    private final RepositoryPartGateways partGateways;
    private final RepositorySectorGateways sectorGateways;
    private final PartEntityMapper partEntityMapper;
    private final ImageProcessing imageProcessing;

    public PartService(RepositoryPartGateways partGateways, RepositorySectorGateways sectorGateways, PartEntityMapper partEntityMapper, ImageProcessing imageProcessing) {
        this.partGateways = partGateways;
        this.sectorGateways = sectorGateways;
        this.partEntityMapper = partEntityMapper;
        this.imageProcessing = imageProcessing;
    }

    @Override
    @CacheEvict(value = "part", allEntries = true)
    public DataAllPart savePart(DataPart part, long id) {
        if(partGateways.existsByCodAndName(part.cod(), part.name())){
            throw new PartExists(String.format("Part with code '%s' and name '%s' already exists.",
                    part.cod(), part.name()));
        }
        if(!sectorGateways.existsSectorAndActive(id)){
            throw new SectorExists("This sector does not exist");

        }
        byte[] img = this.imageCompress(part.image());
        Part partSave = partGateways.savePart(PartFactory.createPart(part.cod(),part.name(),img,part.amount()),id);
        return partEntityMapper.toDTOPart(partSave);
    }

    @Override
    @Cacheable(value = "part", key = "#id")
    public DataAllPart listActivePart(Long id) {
        if(!partGateways.existsPartAndActive(id)){
            throw new PartExists("This part does not exist");
        }
        var part = partGateways.listActivePart(id);
        return partEntityMapper.toDTOPart(part);
    }


    @Override
    @Cacheable(value = "part",key = "'part:' + #pageable.getPageNumber() + ':' + #pageable.getPageSize()")
    public Page<DataAllPart> listAllPart(Pageable pageable) {
        var parts = partGateways.listAllParts(pageable.getPageNumber(),pageable.getPageSize()).stream().map(partEntityMapper::toDTOPart).toList();
        var totalElements = parts.size();
        return new PageImpl<>(parts,pageable,totalElements);
    }

    @Override
    @CacheEvict(value = "part", allEntries = true)
    public DataAllPart updatePart(long id, DataUpdatePart part) {
        if(partGateways.existsByCodAndName(part.cod(),part.name())||!partGateways.existsPartAndActive(id)){
            throw new RuntimeException();
        }
        Part parts = null;
        if(part.image() !=null){
            byte[] img = this.imageCompress(part.image());
            parts = partGateways.updatePart(id,PartFactory.createPartUpdate(part.cod(),part.name(),img,part.amount()),part.sector());

        }else{
            parts = partGateways.updatePart(id,PartFactory.createPartUpdate(part.cod(),part.name(), null,part.amount()),part.sector());
        }
        return  partEntityMapper.toDTOPart(parts);
    }



    @Override
    @CacheEvict(value = "part", allEntries = true)
    public void deletePart(Long id) {
        if(!partGateways.existsPartAndActive(id)){
            throw new PartExists("This part does not exist");
        }
        partGateways.deletePart(id);
    }

    @Override
    @Cacheable(value = "part", key = "'search:' + #cod + ':' + #name + ':' + #pageable.getPageNumber() + ':' + #pageable.getPageSize()")
    public Page<DataAllPart> searchPart(String name, Long cod,Pageable pageable) {
        var parts =  partGateways.searchPart(name,cod,pageable.getPageNumber(),pageable.getPageSize()).stream().map(partEntityMapper::toDTOPart).toList();
        var totalElements = parts.size();
        return new PageImpl<>(parts,pageable,totalElements);
    }

    private byte[] imageCompress(byte[] image)  {
        try {
            return imageProcessing.resizeAndCompressImage(image,800,800,0.1f);
        } catch (IOException e) {
            throw new ImageException("Error rendering image");
        }
    }
}
