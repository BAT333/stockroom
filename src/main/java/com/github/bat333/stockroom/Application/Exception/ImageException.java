package com.github.bat333.stockroom.Application.Exception;

import java.io.IOException;

public class ImageException extends IOException {

    public ImageException(String message) {
        super(message);
    }

    public ImageException(String message, Throwable cause) {
        super(message, cause);
    }

    public ImageException(Throwable cause) {
        super(cause);
    }
}
