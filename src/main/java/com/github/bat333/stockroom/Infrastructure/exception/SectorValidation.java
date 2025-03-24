package com.github.bat333.stockroom.Infrastructure.exception;

public class SectorValidation extends IllegalArgumentException{

    public SectorValidation(String message) {
        super(message);
    }

    public SectorValidation(String message, Throwable cause) {
        super(message, cause);
    }

    public SectorValidation(Throwable cause) {
        super(cause);
    }
}
