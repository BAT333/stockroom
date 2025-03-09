package com.github.bat333.stockroom.Application.UseCases.Part;

import com.github.bat333.stockroom.Domain.Entities.part.Part;
import com.github.bat333.stockroom.Domain.Entities.part.dto.DataAllPart;
import com.github.bat333.stockroom.Domain.Entities.part.dto.DataPart;
import com.github.bat333.stockroom.Domain.Entities.part.dto.DataUpdatePart;
import org.springframework.data.domain.Page;

import java.util.List;

public interface PartUseCase {

    public DataAllPart savePart(DataPart part, long id);
    public DataAllPart listActivePart(Long id);
    public Page<DataAllPart> listAllPart(int page , int size);
    public DataAllPart updatePart(long id, DataUpdatePart part);
    public void deletePart(Long id);
    public Page<DataAllPart> searchPart(String name, Long cod,int page , int size);
}
