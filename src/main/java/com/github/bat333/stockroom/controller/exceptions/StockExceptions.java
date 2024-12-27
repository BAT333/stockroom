package com.github.bat333.stockroom.controller.exceptions;

public class StockExceptions extends RuntimeException {

    public StockExceptions() {
        super();
    }


    public StockExceptions(String message) {
        super(message);
    }

    public StockExceptions(String message, Throwable cause) {
        super(message, cause);
    }

    public StockExceptions(Throwable cause) {
        super(cause);
    }
}
