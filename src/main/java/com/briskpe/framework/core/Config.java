package com.briskpe.framework.core;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

public class Config {
    // Holds all key-value pairs from config.properties
    private static final Properties props = new Properties();

    static {
        try (
                // Load config.properties from classpath (usually src/test/resources/)
                InputStream in = Config.class.getClassLoader().getResourceAsStream("config.properties")
        ) {
            if (in != null) props.load(in); // Load properties into memory
        } catch (IOException e) {
            throw new RuntimeException("Cannot load config.properties", e); // Fail fast if file not found or unreadable
        }
    }

    // Returns the property value; system property overrides the config file
    public static String get(String key) {
        return System.getProperty(key, props.getProperty(key));
    }

    // Returns value with fallback default; also considers system property override
    public static String get(String key, String defaultValue) {
        return System.getProperty(key, props.getProperty(key, defaultValue));
    }
    public static void set(String key, String value) {
        try {
            // Load existing properties
            Properties existingProps = new Properties();
            InputStream input = Config.class.getClassLoader().getResourceAsStream("config.properties");
            if (input != null) {
                existingProps.load(input);
                input.close();
            }

            // Set or update the key-value
            existingProps.setProperty(key, value);

            // Write the updated properties back (overwrite the file)
            try (OutputStream output = new FileOutputStream("src/test/resources/config.properties")) {
                existingProps.store(output, null);
                System.out.println("✅ Property saved: " + key + "=" + value);
            }
        } catch (IOException io) {
            throw new RuntimeException("❌ Unable to write to config.properties", io);
        }
    }


}
