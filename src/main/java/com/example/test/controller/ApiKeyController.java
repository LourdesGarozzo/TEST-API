package com.example.test.controller;

import com.example.test.services.ApiKeyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api-key")
public class ApiKeyController {
    @Autowired
    ApiKeyService apiKeyService;

    @PostMapping("/generate")
    public ResponseEntity<?> generateKey() {

        Map<String, String> keys =  apiKeyService.generateApiKey();

        return ResponseEntity.ok(keys);
    }

}
