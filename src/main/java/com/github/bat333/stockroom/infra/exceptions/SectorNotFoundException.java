package com.github.bat333.stockroom.infra.exceptions;

public class SectorNotFoundException extends RuntimeException{
    public SectorNotFoundException() {
        super();
    }


    public SectorNotFoundException(String message) {
        super(message);
    }

    public SectorNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public SectorNotFoundException(Throwable cause) {
        super(cause);
    }
}
