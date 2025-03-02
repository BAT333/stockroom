package com.github.bat333.stockroom.start.Infra.Persistence.part;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PartRepository extends JpaRepository<PartEntity,Long> {
    boolean existsByName(String mail);

    List<PartEntity> findByActiveTrue();
    Optional<PartEntity> findByCodAndActiveTrue(Long cod);
    Optional<PartEntity> findByIdAndActiveTrue(Long id);

    List<PartEntity> findByCodOrNameContainingIgnoreCaseAndActiveTrue(Long cod, String name);

    List<PartEntity> findByNameContainingIgnoreCaseAndActiveTrue(String name);

    boolean existsByCodAndName( Long cod, String name);

    boolean existsByIdAndActiveTrue(Long id);
}
