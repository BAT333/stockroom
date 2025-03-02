package com.github.bat333.stockroom.start.Infra.Dto.part;

import com.github.bat333.stockroom.start.Infra.Dto.sector.DataSector;
import com.github.bat333.stockroom.start.Infra.Persistence.part.PartEntity;

import java.util.Base64;

public record DataAllPart(
        Long id,
        Long cod,
        String name,
        String image,
        double amount,
        DataSector sector
) {
    public DataAllPart(PartEntity part) {
        this(part.getId(), part.getCod(), part.getName(), Base64.getEncoder().encodeToString(part.getImage()),part.getAmount(),new DataSector(part.getSector()));
    }
}
