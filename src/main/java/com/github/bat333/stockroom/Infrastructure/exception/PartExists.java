package com.github.bat333.stockroom.Infrastructure.exception;

public class PartExists extends RuntimeException {

    public PartExists(String message) {
        super(message);
    }

    public PartExists(String message, Throwable cause) {
        super(message, cause);
    }

    public PartExists(Throwable cause) {
        super(cause);
    }
}
