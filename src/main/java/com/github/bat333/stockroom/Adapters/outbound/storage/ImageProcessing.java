package com.github.bat333.stockroom.Adapters.outbound.storage;

import java.io.IOException;

public interface ImageProcessing {
    byte[] resizeAndCompressImage(byte[] imageBytes, int width, int height, float quality) throws IOException;
}
