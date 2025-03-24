package com.github.bat333.stockroom.Infrastructure.exception;

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
