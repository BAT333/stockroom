package com.github.bat333.stockroom.repository;

import com.github.bat333.stockroom.domain.Sector;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SectorRepository extends JpaRepository<Sector , Long> {
    Page<Sector> findByActiveTrue(Pageable pageable);

    Optional<Sector> findByIdAndActiveTrue(Long id);

    boolean existsBySectorsAndShelfAndColumnAndRow( String sector,  String shelf,  String column,  String row);
}
