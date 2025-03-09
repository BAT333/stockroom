package com.github.bat333.stockroom.useful;

import com.github.bat333.stockroom.Adapters.outbound.entities.part.PartEntity;
import com.github.bat333.stockroom.Domain.Entities.part.Part;
import com.github.bat333.stockroom.Domain.Entities.part.dto.DataAllPart;
import com.github.bat333.stockroom.Domain.Entities.sector.dto.DataSector;


import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class PartEntityMapper implements EntityMapper<Part, PartEntity> {
    private final SectorEntityMapper sectorEntityMapper;

    public PartEntityMapper(SectorEntityMapper sectorEntityMapper) {
        this.sectorEntityMapper = sectorEntityMapper;
    }

    @Override
    public PartEntity toEntity(Part domain) {
        return new PartEntity(
                domain.getId(),
                domain.getCod(),
                domain.getName(),
                domain.getImage(),
                domain.getAmount(),
                domain.isActive(),
                domain.getSector() != null ? sectorEntityMapper.toEntity(domain.getSector()) : null,
                null
        );
    }

    @Override
    public Part toDomain(PartEntity entity) {
        return new Part(entity.getId(), entity.getCod(), entity.getName(), entity.getImage(), entity.getAmount(),entity.isActive(),sectorEntityMapper.toDomain(entity.getSector()));
    }

    @Override
    public List<Part> toListDomain(List<PartEntity> entity) {
        List<Part> parts = new ArrayList<>();
        for (PartEntity partEntity : entity) {
            parts.add(toDomain(partEntity));
        }
        return  parts;
    }

    public DataAllPart toDTOPart(Part domain) {
        return new DataAllPart(domain.getId(),domain.getCod(), domain.getName(), Base64.getEncoder().encodeToString(domain.getImage()),domain.getAmount()
                ,new DataSector(domain.getSector().getSectors(),domain.getSector().getShelf(),domain.getSector().getColumn(),domain.getSector().getRow()));
    }
}
