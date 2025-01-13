package com.example.test.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class ApiKey {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "api_key", nullable = false, unique = true)
    private String key;

    @Column(name = "secret_key", nullable = false, unique = true)
    private String secretKey;

    @Column(nullable = false)
    private boolean enabled;

    public ApiKey() {}

    public ApiKey(String key, boolean enabled) {
        this.key = key;
        this.enabled = enabled;
    }

    public Long getId() {
        return id;
    }

    public String getApiKey() {
        return key;
    }

    public void setApiKey(String key) {
        this.key = key;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

}
