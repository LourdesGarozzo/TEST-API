package com.example.test.services;

import com.example.test.model.ApiKey;
import com.example.test.repository.ApiKeyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Map;
import java.util.UUID;


@Service
public class ApiKeyServiceImpl implements ApiKeyService{

    @Autowired
    ApiKeyRepository apiKeyRepository;

    public Map<String, String> generateApiKey() {
        String apiKey;
        String secretKey;
        do {
            apiKey = UUID.randomUUID().toString();
        } while (apiKeyRepository.existsByKey(apiKey));

        secretKey = generateSecretKey();

        ApiKey newApiKey = new ApiKey();
        newApiKey.setKey(apiKey);
        newApiKey.setSecretKey(secretKey);
        newApiKey.setEnabled(true);

        apiKeyRepository.save(newApiKey);

        return Map.of("apiKey", apiKey, "secretKey", secretKey);
    }

    private String generateSecretKey() {
        byte[] randomBytes = new byte[32];
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.nextBytes(randomBytes);
        return Base64.getEncoder().encodeToString(randomBytes);
    }

    public String generateSignature(String apiKey, String secretKey) {
        try {

            String dataToSign = apiKey;
            Mac mac = Mac.getInstance("HmacSHA256");
            SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
            mac.init(secretKeySpec);
            byte[] rawSignature = mac.doFinal(dataToSign.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(rawSignature);

        } catch (Exception e) {
            throw new RuntimeException("Error al generar la firma", e);
        }
    }

}
