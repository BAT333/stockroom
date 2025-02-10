package com.github.bat333.stockroom.Application.UseCases.Part.contract;

import com.github.bat333.stockroom.Domain.Entities.part.Part;

public interface CreatePart {
    //example
    Part execute(Part part) throws RuntimeException;
}
