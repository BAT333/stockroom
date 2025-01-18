package com.github.bat333.stockroom.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class ImageServiceTest {

    @InjectMocks
    private ImageService service;

    @Test
    @DisplayName("Test resizing and compressing an image to 200x100 with quality 1")
    void resizeAndCompressImage() throws IOException {
        // ARRANGE:
        var img = this.img();

        // ACT:
        var imgs = this.service.resizeAndCompressImage(img, 200, 100, 1);

        // ASSERT:
        assertNotNull(imgs);
    }



    private byte[] img() throws IOException {
        File imageFile = new File("src/test/java/com/github/bat333/stockroom/controller/baixados.jpg");
        return Files.readAllBytes(imageFile.toPath());
    }
}