package com.github.bat333.stockroom.model;

import com.github.bat333.stockroom.domain.Part;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.web.multipart.MultipartFile;

public record DataPart(
        @NotNull
        String name,
        @NotNull
        MultipartFile imageData,
        @NotNull
        @Positive
        double amount
) {
}
