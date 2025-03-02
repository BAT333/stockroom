package com.github.bat333.stockroom.start.Application.Exception;

public class SectorExists extends RuntimeException {

    public SectorExists(String message) {
        super(message);
    }

    public SectorExists(String message, Throwable cause) {
        super(message, cause);
    }

    public SectorExists(Throwable cause) {
        super(cause);
    }
}
