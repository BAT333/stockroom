package com.github.bat333.stockroom.Application.UseCases.Part.implementation;

import com.github.bat333.stockroom.Application.UseCases.Part.contract.CreatePart;
import com.github.bat333.stockroom.Domain.Entities.part.Part;

public class CreatePartImpl implements CreatePart {
    //example
    @Override
    public Part execute(Part part) throws RuntimeException {
        //business rule
        return null;
    }
}
