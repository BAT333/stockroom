package com.github.bat333.stockroom.Infra.Service.part;

import com.github.bat333.stockroom.Application.UseCases.Part.*;
import com.github.bat333.stockroom.Domain.Entities.part.Part;
import com.github.bat333.stockroom.Domain.Entities.part.PartFactory;
import com.github.bat333.stockroom.Infra.Adapters.part.PartEntityMapper;
import com.github.bat333.stockroom.Infra.Dto.part.DataAllPart;
import com.github.bat333.stockroom.Infra.Dto.part.DataPart;
import com.github.bat333.stockroom.Infra.Dto.part.DataUpdatePart;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PartService {
    private final PartEntityMapper partEntityMapper;
    private final SavePart savePart;
    private final ListAllParts allParts;
    private final ListPart listPart;
    private final UpdatePart updatePart;
    private final DeletePart deletePart;
    private final SearchByPart searchByPart;
    public PartService(PartEntityMapper partEntityMapper, SavePart savePart, ListAllParts allParts, ListPart listPart, UpdatePart updatePart, DeletePart deletePart, SearchByPart searchByPart) {
        this.partEntityMapper = partEntityMapper;
        this.savePart = savePart;
        this.allParts = allParts;
        this.listPart = listPart;
        this.updatePart = updatePart;
        this.deletePart = deletePart;
        this.searchByPart = searchByPart;
    }

    @CacheEvict(value = "part", allEntries = true)
    public DataAllPart register(@Valid DataPart dataPart, @NotNull Long id) {
        log.info("Service entering to register part");
        Part part = savePart.savePart(new Part(dataPart.cod(),dataPart.name(),dataPart.image(), dataPart.amount()),id);
        log.info("Service leaving to register part");
        return new DataAllPart(partEntityMapper.toEntity(part));

    }

    @Cacheable(value = "part")
    public Page<DataAllPart> listAllPart(@NotNull Pageable pageable) {
        log.info("Service entering to list part");
        var parts=allParts.listAllParts().stream().map(part->new DataAllPart(partEntityMapper.toEntity(part))).toList();
        long totalElements = parts.size();
        log.info("Service leaving to list part");
        return new PageImpl<>(parts, pageable, totalElements);
    }
    @Cacheable(value = "part", key = "#id")
    public DataAllPart get(@NotNull Long id) {
        log.info("Service entering to part ID: {}", id);
        var part = listPart.listPart(id);
        log.info("Service leaving to part ID: {}", id);
        return new DataAllPart(partEntityMapper.toEntity(part));
    }

    @CacheEvict(value = "part", allEntries = true)
    public DataAllPart update(@NotNull Long id, DataUpdatePart dataUpdatePart) {
        log.info("Service entering to update part ID: {}", id);
        var part = updatePart.updatePart(id, PartFactory.createPartUpdate(dataUpdatePart.cod(),dataUpdatePart.name(),dataUpdatePart.image(), dataUpdatePart.amount()),dataUpdatePart.sector());
        log.info("Service leaving to update part ID: {}", id);
        return new DataAllPart(partEntityMapper.toEntity(part));
    }

    @CacheEvict(value = "part", allEntries = true)
    public void delete(@NotNull Long id) {
        log.info("Service entering to delete part ID: {}", id);
        deletePart.deletePart(id);
        log.info("Service leaving to delete part ID: {}", id);
    }

    @Cacheable(value = "part", key = "'search:' + #cod + ':' + #name + ':' + #pageable.pageNumber + ':' + #pageable.pageSize")
    public Page<DataAllPart> search(Long cod, String name, Pageable pageable) {
        log.info("Service entering to search part");
        var parts=searchByPart.searchByPart(name,cod).stream().map(part->new DataAllPart(partEntityMapper.toEntity(part))).toList();
        long totalElements = parts.size();
        log.info("Service leaving to search part");
        return new PageImpl<>(parts, pageable, totalElements);
    }
}
