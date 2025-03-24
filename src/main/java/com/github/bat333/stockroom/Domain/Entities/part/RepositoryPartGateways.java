package com.github.bat333.stockroom.Domain.Entities.part;

import java.util.List;

public interface RepositoryPartGateways {

    public Part savePart(Part part, long id);
    public Part listActivePart(Long id);
    public List<Part> listAllActiveParts(int pageNumber, int pageSize);
    public Part listPart(Long id);
    public List<Part> listAllParts(int pageNumber, int pageSize);
    public Part updatePart(long id, Part part, Long sector);
    public void deletePart(Long id);
    public boolean existsPartAndActive(Long id);
    public List<Part> searchPart(String name, Long cod, int pageNumber, int pageSize);
    public  boolean existsByCodAndName(long cod,String name);
}
