package com.github.bat333.stockroom.start.Domain.Exception;

public class PartValidation extends IllegalArgumentException{

    public PartValidation(String message) {
        super(message);
    }

    public PartValidation(String message, Throwable cause) {
        super(message, cause);
    }

    public PartValidation(Throwable cause) {
        super(cause);
    }
}
