package com.github.bat333.stockroom.repository;

import com.github.bat333.stockroom.domain.Part;
import com.github.bat333.stockroom.model.DataAllPart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PartRepository extends JpaRepository<Part,Long> {
    boolean existsByName(String mail);

    List<Part> findByActiveTrue();

    Optional<Part> findByIdAndActiveTrue(Long id);

    List<Part> findByIdOrNameContainingIgnoreCaseAndActiveTrue(Long cod, String name);

    List<Part> findByNameContainingIgnoreCaseAndActiveTrue(String name);
}
