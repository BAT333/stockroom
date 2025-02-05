package com.github.bat333.stockroom.infra.validator.sector;

import com.github.bat333.stockroom.domain.Sector;
import com.github.bat333.stockroom.infra.exceptions.SectorNotFoundException;
import com.github.bat333.stockroom.repository.SectorRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SectorValidationExists implements SectorValidator {

    @Autowired
    private SectorRepository repository;

    @Override
    public Sector validator(Long id) {
        return repository.findByIdAndActiveTrue(id)
                .orElseThrow(() -> {
                    log.error("Sector with ID {} not found or is inactive in the system.", id);
                    return new SectorNotFoundException("Reported Sector with ID " + id + " not found or is inactive.");
                });
    }
}
