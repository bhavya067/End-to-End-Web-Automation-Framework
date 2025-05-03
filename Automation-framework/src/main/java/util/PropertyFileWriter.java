package util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import base.DriverTestCase;

public class PropertyFileWriter extends DriverTestCase {
    
    private static final String PROPERTIES_FILE_PATH = "src/main/resources/runtime.properties";

    public String getProperty(String key) {
        Properties properties = new Properties();
        log4j.info("Retrieving value for key: '" + key + "' from runtime.properties file");
        getLogger().info("Retrieving value for key: '" + key + "' from runtime.properties file");
        
        try (FileInputStream in = new FileInputStream(PROPERTIES_FILE_PATH)) {
            properties.load(in);
            String value = properties.getProperty(key);

            if (value != null) {
                // Log pass if the property is successfully fetched
                log4j.pass("Successfully retrieved property for key: '" + key + "' with value: '" + value + "'");
                getLogger().pass("Successfully retrieved property for key: '" + key + "' with value: '" + value + "'");
                return value;
            } else {
                // Log fail if the property key does not exist and throw an exception
                log4j.fail("Failed to retrieve value for key: '" + key + "'from the runtime.properties file");
                getLogger().fail("Failed to retrieve value for key: '" + key + "'from the runtime.properties file");
                throw new RuntimeException("Value for key: '" + key + "' not retrieved in the runtime.properties file.");
            }
        } catch (IOException e) {
            // Log fail in case of an exception and throw an exception to stop execution
            log4j.fail("Runtime exception occurred while retrieving property for key: '" + key + "' from runtime.properties. Error: " + e.getMessage());
            getLogger().fail("Runtime exception occurred while retrieving property for key: '" + key + "' from runtime.properties. Error: " + e.getMessage());
            throw new RuntimeException("Runtime exception occurred while retrieving property for key: '" + key + "'", e);
        }
    }


    public void setProperty(String key, String value) {
        Properties properties = new Properties();
        try (FileInputStream in = new FileInputStream(PROPERTIES_FILE_PATH)) {
            properties.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (FileOutputStream out = new FileOutputStream(PROPERTIES_FILE_PATH)) {
            properties.setProperty(key, value);
            properties.store(out, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
