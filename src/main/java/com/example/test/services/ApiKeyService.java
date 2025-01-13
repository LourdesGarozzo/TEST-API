package com.example.test.services;

import java.util.Map;

public interface ApiKeyService {

    Map<String, String> generateApiKey();

    String generateSignature(String apiKey, String secretKey);

}
