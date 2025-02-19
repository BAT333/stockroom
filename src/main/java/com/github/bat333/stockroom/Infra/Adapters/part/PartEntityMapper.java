package com.github.bat333.stockroom.Infra.Adapters.part;

import com.github.bat333.stockroom.Domain.Entities.part.Part;
import com.github.bat333.stockroom.Infra.Adapters.EntityMapper;
import com.github.bat333.stockroom.Infra.Adapters.sector.SectorEntityMapper;
import com.github.bat333.stockroom.Infra.Persistence.part.PartEntity;

import java.util.ArrayList;
import java.util.List;

public class PartEntityMapper implements EntityMapper<Part, PartEntity> {
    private final SectorEntityMapper sectorEntityMapper;

    public PartEntityMapper(SectorEntityMapper sectorEntityMapper) {
        this.sectorEntityMapper = sectorEntityMapper;
    }

    @Override
    public PartEntity toEntity(Part domain) {
        return new PartEntity(domain.getId(), domain.getCod(), domain.getName(), domain.getImage(), domain.getAmount(), domain.isActive(), sectorEntityMapper.toEntity(domain.getSector()),null);
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
}
