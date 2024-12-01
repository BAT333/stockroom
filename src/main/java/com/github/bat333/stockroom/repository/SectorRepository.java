package com.github.bat333.stockroom.repository;

import com.github.bat333.stockroom.domain.Sector;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SectorRepository extends JpaRepository<Sector , Long> {
   List<Sector> findByActiveTrue();

    Optional<Sector> findByIdActiveTrue(Long id);
}
