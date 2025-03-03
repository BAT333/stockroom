package com.github.bat333.stockroom.useful;


import com.github.bat333.stockroom.Domain.Entities.sector.Sector;
import com.github.bat333.stockroom.start.Infra.Adapters.EntityMapper;
import com.github.bat333.stockroom.start.Infra.Persistence.sector.SectorEntity;

import java.util.ArrayList;
import java.util.List;


public class SectorEntityMapper implements EntityMapper<Sector, SectorEntity> {
    @Override
    public SectorEntity toEntity(Sector domain) {
        return new SectorEntity(domain.getId(), domain.getSectors(), domain.getShelf(), domain.getColumn(), domain.getRow(), domain.isActive(),null,null);
    }

    @Override
    public Sector toDomain(SectorEntity entity) {
        return new Sector(entity.getId(), entity.getSectors(), entity.getShelf(), entity.getColumn(), entity.getRow(), entity.getActive(),null);
    }

    @Override
    public List<Sector> toListDomain(List<SectorEntity> entity) {
        List<Sector> sectors = new ArrayList<>();
        for (SectorEntity sectorEntity : entity) {
            sectors.add(toDomain(sectorEntity));
        }
        return  sectors;
    }
}
