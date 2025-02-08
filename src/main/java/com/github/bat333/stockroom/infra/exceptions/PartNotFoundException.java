package com.github.bat333.stockroom.infra.exceptions;

public class PartNotFoundException extends RuntimeException{
    public PartNotFoundException() {
        super();
    }


    public PartNotFoundException(String message) {
        super(message);
    }

    public PartNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public PartNotFoundException(Throwable cause) {
        super(cause);
    }
}
