package com.example.test.utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Base64;

public class EncodeToBase64 {

    public static String encodeToBase64(String imageUrl) {
        try (InputStream inputStream = new URL(imageUrl).openStream()) {

            byte[] imageBytes = inputStream.readAllBytes();
            return Base64.getEncoder().encodeToString(imageBytes);

        } catch (IOException e) {
            throw new RuntimeException("Error al codificar la imagen", e);
        }
    }

}
