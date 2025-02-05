package com.github.bat333.stockroom.infra.validator.sector;

import com.github.bat333.stockroom.domain.Sector;

public interface SectorValidator {
    Sector validator(Long id);
}
