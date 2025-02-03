package com.newsletter;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Config {
    private static final Properties props = new Properties();

    static {
        try (FileInputStream fis = new FileInputStream("config.properties")) {
            props.load(fis);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load configuration from config.properties", e);
        }
    }

    public static String get(String key) {
        return props.getProperty(key);
    }
}
