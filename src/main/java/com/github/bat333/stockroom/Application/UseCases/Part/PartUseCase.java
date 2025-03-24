package com.github.bat333.stockroom.Application.UseCases.Part;

import com.github.bat333.stockroom.Domain.Entities.part.Part;
import com.github.bat333.stockroom.Domain.Entities.part.dto.DataAllPart;
import com.github.bat333.stockroom.Domain.Entities.part.dto.DataPart;
import com.github.bat333.stockroom.Domain.Entities.part.dto.DataUpdatePart;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PartUseCase {

    public DataAllPart savePart(DataPart part, long id);
    public DataAllPart listActivePart(Long id);
    public Page<DataAllPart> listAllPart(Pageable pageable);
    public DataAllPart updatePart(long id, DataUpdatePart part);
    public void deletePart(Long id);
    public Page<DataAllPart> searchPart(String name, Long cod,Pageable pageable);
}
