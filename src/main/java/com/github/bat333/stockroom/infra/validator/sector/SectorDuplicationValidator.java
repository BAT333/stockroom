package com.github.bat333.stockroom.infra.validator.sector;

import com.github.bat333.stockroom.infra.exceptions.StockExceptions;
import com.github.bat333.stockroom.model.DataSector;
import com.github.bat333.stockroom.repository.SectorRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SectorDuplicationValidator {

    @Autowired
    private SectorRepository repository;

    public void validate(DataSector dataSector) {
        if (repository.existsBySectorsAndShelfAndColumnAndRow(
                dataSector.sector(), dataSector.shelf(), dataSector.column(), dataSector.row())) {
            throw new StockExceptions(String.format(
                    "Sector already registered: sector=%s, shelf=%s, column=%s, row=%s",
                    dataSector.sector(), dataSector.shelf(), dataSector.column(), dataSector.row()
            ));
        }
    }

}
