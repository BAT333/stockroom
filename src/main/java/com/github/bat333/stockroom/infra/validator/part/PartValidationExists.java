package com.github.bat333.stockroom.infra.validator.part;

import com.github.bat333.stockroom.domain.Part;
import com.github.bat333.stockroom.infra.exceptions.SectorNotFoundException;
import com.github.bat333.stockroom.repository.PartRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PartValidationExists implements PartValidator {

    @Autowired
    private PartRepository repository;

    @Override
    public Part validator(Long id) {

        return repository.findByIdAndActiveTrue(id)
                .orElseThrow(() -> {
                    log.error("Part with ID {} not found or is inactive in the system.", id);
                    return new SectorNotFoundException("Reported Part Not Found ");
                });
    }
}
