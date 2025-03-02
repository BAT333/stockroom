package com.github.bat333.stockroom.start.Application.UseCases.Part.implementation;

import com.github.bat333.stockroom.start.Application.UseCases.Part.contract.CreatePart;
import com.github.bat333.stockroom.start.Domain.Entities.part.Part;

public class CreatePartImpl implements CreatePart {
    //example
    @Override
    public Part execute(Part part) throws RuntimeException {
        //business rule
        return null;
    }
}
