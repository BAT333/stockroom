package com.github.bat333.stockroom.infra.validator.part;

import com.github.bat333.stockroom.domain.Sector;
import com.github.bat333.stockroom.infra.exceptions.StockExceptions;
import com.github.bat333.stockroom.model.DataPart;
import com.github.bat333.stockroom.repository.PartRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PartDuplicationValidator {

    @Autowired
    private PartRepository partRepository;

    public void validate(DataPart dataPart, Sector sector) {
        if(partRepository.existsByCodAndNameAndSector(dataPart.cod(),dataPart.name(),sector)){
            throw new StockExceptions(String.format("Part already registered: cod=%s, name=%s already registered in this sector.",dataPart.cod(),dataPart.name()));
        }
    }

}
