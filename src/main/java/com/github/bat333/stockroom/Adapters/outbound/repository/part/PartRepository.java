package com.github.bat333.stockroom.Adapters.outbound.repository.part;


import com.github.bat333.stockroom.Adapters.outbound.entities.part.PartEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PartRepository extends JpaRepository<PartEntity,Long> {
    boolean existsByName(String mail);

    Page<PartEntity> findByActiveTrue(Pageable pageable);
    Page<PartEntity> findByCodAndActiveTrue(Long cod,Pageable pageable);
    Optional<PartEntity> findByIdAndActiveTrue(Long id);

    Page<PartEntity> findByCodOrNameContainingIgnoreCaseAndActiveTrue(Long cod, String name, Pageable pageable);

    Page<PartEntity> findByNameContainingIgnoreCaseAndActiveTrue(String name, Pageable pageable);

    boolean existsByCodAndName( Long cod, String name);

    boolean existsByIdAndActiveTrue(Long id);
}
