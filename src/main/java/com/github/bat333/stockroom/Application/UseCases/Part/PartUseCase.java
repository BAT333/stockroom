package com.github.bat333.stockroom.Application.UseCases.Part;

import com.github.bat333.stockroom.Domain.Entities.part.Part;

import java.util.List;

public interface PartUseCase {

    public Part savePart(Part part, long id);
    public Part listActivePart(Long id);
    public Part listPart(Long id);
    public Part updatePart(long id, Part part, Long sector);
    public void deletePart(Long id);
    public List<Part> searchPart(String name, Long cod);
}
