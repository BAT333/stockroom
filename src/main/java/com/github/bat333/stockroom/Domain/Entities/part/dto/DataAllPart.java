package com.github.bat333.stockroom.Domain.Entities.part.dto;



import com.github.bat333.stockroom.Adapters.outbound.entities.part.PartEntity;
import com.github.bat333.stockroom.Domain.Entities.sector.dto.DataSector;

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
