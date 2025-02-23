package com.github.bat333.stockroom.Infra.Adapters.ValueObjects;

import com.github.bat333.stockroom.Application.Gateways.ValueObjects.ImageProcessing;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
@Service
public class ImageAdapters implements ImageProcessing {

    @Override
    public byte[] resizeAndCompressImage(byte[] imageBytes, int width, int height, float quality) throws IOException {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(imageBytes);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        Thumbnails.of(inputStream)
                .size(width, height)
                .outputQuality(quality)
                .toOutputStream(outputStream);

        return outputStream.toByteArray();
    }
}
