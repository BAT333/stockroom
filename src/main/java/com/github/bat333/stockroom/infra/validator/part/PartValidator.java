package com.github.bat333.stockroom.infra.validator.part;

import com.github.bat333.stockroom.domain.Part;

public interface PartValidator {
    Part validator(Long id);
}
