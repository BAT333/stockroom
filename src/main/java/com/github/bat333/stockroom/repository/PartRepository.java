package com.github.bat333.stockroom.repository;

import com.github.bat333.stockroom.domain.Part;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PartRepository extends JpaRepository<Part,Long> {
    boolean existsByName(String mail);

    Page<Part> findByActiveTrue(Pageable pageable);

    Optional<Part> findByIdAndActiveTrue(Long id);

    Page<Part> findByIdOrNameContainingIgnoreCaseAndActiveTrue(Long cod, String name, Pageable pageable);

    Page<Part> findByNameContainingIgnoreCaseAndActiveTrue(String name, Pageable pageable);
}
