package com.github.bat333.stockroom.Application.Service.part;

import com.github.bat333.stockroom.Adapters.outbound.repository.part.RepositoryPartGatewaysJPA;
import com.github.bat333.stockroom.Application.UseCases.Part.PartUseCase;
import com.github.bat333.stockroom.Domain.Entities.part.Part;
import com.github.bat333.stockroom.useful.PartEntityMapper;
import com.github.bat333.stockroom.Domain.Entities.part.dto.DataAllPart;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PartService implements PartUseCase {
    //se usar sector usar o usecase sempre for usar algo usar usecase
    private final RepositoryPartGatewaysJPA partGateways;
    private final PartEntityMapper partEntityMapper;

    public PartService(RepositoryPartGatewaysJPA partGateways, PartEntityMapper partEntityMapper) {
        this.partGateways = partGateways;
        this.partEntityMapper = partEntityMapper;
    }

    @Override
    @CacheEvict(value = "part", allEntries = true)
    public Part savePart(Part part, long id) {
        //new Part(dataPart.cod(),dataPart.name(),dataPart.image(), dataPart.amount())
        return partGateways.savePart(part,id);
    }

    @Override
    @Cacheable(value = "part")
    public Part listActivePart(Long id) {
        var parts=allParts.listAllParts().stream().map(part->new DataAllPart(partEntityMapper.toEntity(part))).toList();
        long totalElements = parts.size();
        return new PageImpl<>(parts, pageable, totalElements);
    }

    @Override
    @Cacheable(value = "part", key = "#id")
    public Part listPart(Long id) {
        return null;
    }

    @Override
    @CacheEvict(value = "part", allEntries = true)
    public Part updatePart(long id, Part part, Long sector) {
        return null;
    }

    @Override
    @CacheEvict(value = "part", allEntries = true)
    public void deletePart(Long id) {

    }

    @Override
    @Cacheable(value = "part", key = "'search:' + #cod + ':' + #name + ':' + #pageable.pageNumber + ':' + #pageable.pageSize")
    public List<Part> searchPart(String name, Long cod) {
        return List.of();
    }

//
//    @Cacheable(value = "part")
//    public Page<DataAllPart> listAllPart(@NotNull Pageable pageable) {
//        var parts=allParts.listAllParts().stream().map(part->new DataAllPart(partEntityMapper.toEntity(part))).toList();
//        long totalElements = parts.size();
//        return new PageImpl<>(parts, pageable, totalElements);
//    }
//    @Cacheable(value = "part", key = "#id")
//    public DataAllPart get(@NotNull Long id) {
//        var part = listPart.listPart(id);
//        return new DataAllPart(partEntityMapper.toEntity(part));
//    }
//
//    @CacheEvict(value = "part", allEntries = true)
//    public DataAllPart update(@NotNull Long id, DataUpdatePart dataUpdatePart) {
//        var part = updatePart.updatePart(id, PartFactory.createPartUpdate(dataUpdatePart.cod(),dataUpdatePart.name(),dataUpdatePart.image(), dataUpdatePart.amount()),dataUpdatePart.sector());
//        return new DataAllPart(partEntityMapper.toEntity(part));
//    }
//
//    @CacheEvict(value = "part", allEntries = true)
//    public void delete(@NotNull Long id) {
//        deletePart.deletePart(id);
//    }
//
//    @Cacheable(value = "part", key = "'search:' + #cod + ':' + #name + ':' + #pageable.pageNumber + ':' + #pageable.pageSize")
//    public Page<DataAllPart> search(Long cod, String name, Pageable pageable) {
//        var parts=searchByPart.searchByPart(name,cod).stream().map(part->new DataAllPart(partEntityMapper.toEntity(part))).toList();
//        long totalElements = parts.size();
//        return new PageImpl<>(parts, pageable, totalElements);
//    }
}
