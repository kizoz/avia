package com.example.demo.config;

import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.security.Key;

@Configuration
public class SecretKey {

    @Value("${secret.key.string}")
    private String jwtKey;

    @Bean()
    public Key jwtKey() {
        return Keys.hmacShaKeyFor(jwtKey.getBytes());
    }
}
