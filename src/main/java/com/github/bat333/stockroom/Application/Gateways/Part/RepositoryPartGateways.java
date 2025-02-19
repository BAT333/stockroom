package com.github.bat333.stockroom.Application.Gateways.Part;

import com.github.bat333.stockroom.Domain.Entities.part.Part;

import java.util.List;

public interface RepositoryPartGateways {

    public Part savePart(Part part, long id);
    public Part listActivePart(Long id);
    public List<Part> listAllActiveParts();
    public Part listPart(Long id);
    public List<Part> listAllParts();
    public Part updatePart(long id, Part part);
    public void deletePart(Long id);
    public boolean existsPartAndActive(Long id);
    public List<Part> searchPart(String name, Long cod);
    public  boolean existsByCodAndName(long cod,String name);
}
