package com.github.bat333.stockroom.Adapters.outbound.repository.sector;


import com.github.bat333.stockroom.Adapters.outbound.entities.sector.SectorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SectorRepository extends JpaRepository<SectorEntity, Long> {
    List<SectorEntity> findByActiveTrue();

    Optional<SectorEntity> findByIdAndActiveTrue(Long id);

    boolean existsBySectorsAndShelfAndColumnAndRow( String sector,  String shelf,  String column,  String row);

    boolean existsByIdAndActiveTrue(Long id);
}
