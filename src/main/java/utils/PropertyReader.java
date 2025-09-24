package utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Utility to load properties from src/main/resources/config.properties.
 */
public class PropertyReader {

    private static final Properties props = new Properties();

    static {
        try (InputStream input = PropertyReader.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                throw new RuntimeException("config.properties not found in classpath");
            }
            props.load(input);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load config.properties", e);
        }
    }

    public static String getProperty(String key) {
        return props.getProperty(key);
    }
}
