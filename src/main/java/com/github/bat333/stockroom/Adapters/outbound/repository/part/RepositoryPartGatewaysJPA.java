package com.github.bat333.stockroom.Adapters.outbound.repository.part;

import com.github.bat333.stockroom.Adapters.outbound.entities.part.PartEntity;
import com.github.bat333.stockroom.Adapters.outbound.entities.sector.SectorEntity;
import com.github.bat333.stockroom.Adapters.outbound.repository.sector.SectorRepository;
import com.github.bat333.stockroom.Domain.Entities.part.Part;
import com.github.bat333.stockroom.Domain.Entities.part.RepositoryPartGateways;
import com.github.bat333.stockroom.useful.PartEntityMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    public List<Part> listAllActiveParts(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return partEntityMapper.toListDomain(partRepository.findByActiveTrue(pageable).toList());
    }

    @Override
    public Part listPart(Long id) {
        PartEntity partEntity = partRepository.findById(id).get();
        return partEntityMapper.toDomain(partEntity);
    }

    @Override
    public List<Part> listAllParts(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        return partEntityMapper.toListDomain(partRepository.findAll(pageable).toList());
    }

    @Override
    public Part updatePart(long id, Part part, Long sector) {
        SectorEntity sectorEntity = sectorRepository.findByIdAndActiveTrue(sector).orElse(null);
        PartEntity partEntity = partRepository.findByIdAndActiveTrue(id).get();
        PartEntity partEntityUpdate = partEntityMapper.toEntity(part);
        partEntityUpdate.setSector(sectorEntity);
        partEntity.update(partEntityUpdate);
        partRepository.save(partEntity);
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
    public List<Part> searchPart(String name, Long cod, int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        if(name == null && cod == null){
            return this.partEntityMapper.toListDomain(partRepository.findByActiveTrue(pageable).toList());
        }
        return (name != null && cod != null) ?this.getByCodAndName(cod, name,pageable):
                (cod != null)? this.getByCod(cod,pageable) : this.getByName(name,pageable);
    }

    private List<Part> getByName(String name, Pageable pageable) {
        List<PartEntity> partEntity = this.partRepository.findByNameContainingIgnoreCaseAndActiveTrue(name,pageable).toList();
        return partEntityMapper.toListDomain(partEntity);
    }

    private List<Part> getByCod(Long cod, Pageable pageable) {
        return partEntityMapper.toListDomain(this.partRepository.findByCodAndActiveTrue(cod,pageable).toList());

    }

    private List<Part> getByCodAndName(Long cod, String name, Pageable pageable) {
        return  this.partEntityMapper.toListDomain( this.partRepository.findByCodOrNameContainingIgnoreCaseAndActiveTrue(cod,name,pageable).toList());
    }

    @Override
    public boolean existsByCodAndName(long cod, String name) {
        return partRepository.existsByCodAndName(cod,name);
    }
}
