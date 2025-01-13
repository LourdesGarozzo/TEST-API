package com.example.test.repository;
import com.example.test.model.ApiKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApiKeyRepository extends JpaRepository<ApiKey, Long> {

    boolean existsByKey(String key);

    ApiKey findByKey(String key);
}
