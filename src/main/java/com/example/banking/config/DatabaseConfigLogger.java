package com.example.banking.config;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
public class DatabaseConfigLogger {

    @Value("${spring.datasource.url}")
    private String dbUrl;

    @Value("${spring.datasource.username}")
    private String dbUser;

    @Value("${spring.datasource.password}")
    private String dbPassword;

    @PostConstruct
    public void printDbConfig() {
        System.out.println("=== DATABASE CONFIG ===");
        System.out.println("URL      : " + dbUrl);
        System.out.println("Username : " + dbUser);
    }
}
