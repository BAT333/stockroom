package com.github.bat333.stockroom.Infra.Adapters.part;

import com.github.bat333.stockroom.Application.Gateways.Part.RepositoryPartGateways;
import com.github.bat333.stockroom.Domain.Entities.part.Part;
import com.github.bat333.stockroom.Infra.Persistence.part.PartEntity;
import com.github.bat333.stockroom.Infra.Persistence.part.PartRepository;
import com.github.bat333.stockroom.Infra.Persistence.sector.SectorRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RepositoryPartGatewaysJPA implements RepositoryPartGateways {

    private final PartRepository partRepository;
    private final SectorRepository sectorRepository;
    private final PartEntityMapper partEntityMapper;

    public RepositoryPartGatewaysJPA(PartRepository partRepository, SectorRepository sectorRepository, PartEntityMapper partEntityMapper) {
        this.partRepository = partRepository;
        this.sectorRepository = sectorRepository;
        this.partEntityMapper = partEntityMapper;
    }

    @Override
    public Part savePart(Part part, long id) {
        PartEntity partEntity = partEntityMapper.toEntity(part);
        partEntity.setSector(sectorRepository.findByIdAndActiveTrue(id).get());
        partRepository.save(partEntity);
        return partEntityMapper.toDomain(partEntity);
    }

    @Override
    public Part listActivePart(Long id) {
        PartEntity partEntity = partRepository.findByIdAndActiveTrue(id).get();
        return partEntityMapper.toDomain(partEntity);
    }

    @Override
    public List<Part> listAllActiveParts() {
        return partEntityMapper.toListDomain(partRepository.findByActiveTrue());
    }

    @Override
    public Part listPart(Long id) {
        PartEntity partEntity = partRepository.findById(id).get();
        return partEntityMapper.toDomain(partEntity);
    }

    @Override
    public List<Part> listAllParts() {
        return partEntityMapper.toListDomain(partRepository.findAll());
    }

    @Override
    public Part updatePart(long id, Part part) {
        System.out.println("3");
        PartEntity partEntity = partRepository.findByIdAndActiveTrue(id).get();
        System.out.println("4");
        partEntity.update(partEntityMapper.toEntity(part));
        System.out.println("5");
        partRepository.save(partEntity);
        System.out.println("6");
        return partEntityMapper.toDomain(partEntity);
    }

    @Override
    public void deletePart(Long id) {
        PartEntity partEntity = partRepository.findByIdAndActiveTrue(id).get();
        partEntity.delete();
        partRepository.save(partEntity);
    }

    @Override
    public boolean existsPartAndActive(Long id) {
        return partRepository.existsByIdAndActiveTrue(id);
    }

    @Override
    public List<Part> searchPart(String name, Long cod) {
        if(name == null && cod == null){
            return this.partEntityMapper.toListDomain(partRepository.findByActiveTrue());
        }
        return (name != null && cod != null) ?this.getByCodAndName(cod, name):
                (cod != null)? this.getByCod(cod) : this.getByName(name);
    }

    private List<Part> getByName(String name) {
        List<PartEntity> partEntity = this.partRepository.findByNameContainingIgnoreCaseAndActiveTrue(name);
        return partEntityMapper.toListDomain(partEntity);
    }

    private List<Part> getByCod(Long cod) {
        PartEntity partEntity = this.partRepository.findByCodAndActiveTrue(cod).orElseThrow();
        return List.of(partEntityMapper.toDomain(partEntity));
    }

    private List<Part> getByCodAndName(Long cod, String name) {
        return  this.partEntityMapper.toListDomain( this.partRepository.findByCodOrNameContainingIgnoreCaseAndActiveTrue(cod,name));
    }

    @Override
    public boolean existsByCodAndName(long cod, String name) {
        return partRepository.existsByCodAndName(cod,name);
    }
}
