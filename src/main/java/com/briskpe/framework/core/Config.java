package com.briskpe.framework.core;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

public class Config {
    // Holds all key-value pairs loaded from the config file
    private static final Properties props = new Properties();

    // Current config file name (default to 'config.properties')
    private static String configFileName = "config.properties";

    // Lock object for thread safety during writes and reloads
    private static final Object lock = new Object();

    // Static block loads the default config file initially
    static {
        load();
    }

    /**
     * Load or reload the properties from the current config file.
     */
    public static void load() {
        synchronized (lock) {
            props.clear();
            try (InputStream in = Config.class.getClassLoader().getResourceAsStream(configFileName)) {
                if (in != null) {
                    props.load(in);
                } else {
                    System.err.println("⚠️ Config file not found: " + configFileName);
                }
            } catch (IOException e) {
                throw new RuntimeException("Cannot load config file: " + configFileName, e);
            }
        }
    }

    /**
     * Change the config profile (e.g., 'android', 'web', 'ios') by setting a new config file to load.
     * Example: 'android' loads 'config.android.properties'
     */
    public static void setConfigProfile(String profile) {
        if (profile == null || profile.trim().isEmpty()) {
            throw new IllegalArgumentException("Profile name cannot be null or empty");
        }
        configFileName = "config." + profile.toLowerCase() + ".properties";
        load();
    }

    /**
     * Get the property value; system property overrides the config file value.
     */
    public static String get(String key) {
        return System.getProperty(key, props.getProperty(key));
    }

    /**
     * Get the property value with a default fallback; system property has highest priority.
     */
    public static String get(String key, String defaultValue) {
        return System.getProperty(key, props.getProperty(key, defaultValue));
    }

    /**
     * Set or update a property value in memory and optionally save it back to the config file.
     * Writing to the actual config file is not recommended for parallel runs or CI.
     * Use with caution.
     */
    public static void set(String key, String value) {
        synchronized (lock) {
            props.setProperty(key, value);
            // Optional: persist changes to the config file
            try (OutputStream output = new FileOutputStream("src/test/resources/" + configFileName)) {
                props.store(output, "Updated property " + key);
                System.out.println("✅ Property saved: " + key + "=" + value);
            } catch (IOException e) {
                throw new RuntimeException("Unable to write to " + configFileName, e);
            }
        }
    }

    /**
     * Force reload config file from disk.
     */
    public static void reload() {
        load();
    }
}
