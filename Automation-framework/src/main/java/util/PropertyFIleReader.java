package util;


import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

import base.DriverTestCase;

public class PropertyFIleReader extends DriverTestCase
{
	String path =  getPath();
	
	private Properties properties;
	private String filePath;

	public String readApplicationFile(String key, String fileName) { 
	    String value = "";    
	    //log4j.info("Attempting to read key: '" + key + "' from file: '" + fileName + ".properties'");
	   // test.info("Attempting to read key: '" + key + "' from file: '" + fileName + ".properties'");

	    try {  
	        Properties prop = new Properties();          
	        File f = new File(path + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + fileName + ".properties");         
	       
	        if (f.exists()) {
	            prop.load(new FileInputStream(f));
	            value = prop.getProperty(key);
	            
	            if (value != null) {
	                // Log pass if the key is successfully found and the value retrieved
	               // test.pass("Successfully retrieved key: '" + key + "' with value: '" + value + "' from file: '" + fileName + ".properties'");
	               // log4j.pass("Successfully retrieved key: '" + key + "' with value: '" + value + "' from file: '" + fileName + ".properties'");
	            } else {
	                // Log fail if the key is not found in the properties file and throw an exception
	                log4j.fail("Key: '" + key + "' not found in file: '" + fileName + ".properties'");
	                getLogger().fail("Key: '" + key + "' not found in file: '" + fileName + ".properties'");
	                throw new RuntimeException("Key: '" + key + "' not found in file: '" + fileName + ".properties'");
	            }
	        } else {
	            // Log fail if the file does not exist and throw an exception
	            log4j.fail("File: '" + fileName + ".properties' does not exist in the specified path.");
	            getLogger().fail("File: '" + fileName + ".properties' does not exist in the specified path.");
	            throw new RuntimeException("File: '" + fileName + ".properties' does not exist in the specified path.");
	        }
	    } catch (Exception e) {  
	        // Log failure if there’s an exception and throw a runtime exception to stop execution
	        log4j.fail("Exception occurred while reading key: '" + key + "' from file: '" + fileName + ".properties'. Error: " + e.getMessage());
	        getLogger().fail("Exception occurred while reading key: '" + key + "' from file: '" + fileName + ".properties'. Error: " + e.getMessage());
	        throw new RuntimeException("Exception occurred while reading key: '" + key + "' from file: '" + fileName + ".properties'", e);
	    }
	    return value;
	}



	public String getPath()
	{		
		String path =" ";		
		File file = new File("");
		String absolutePathOfFirstFile = file.getAbsolutePath();
		path = absolutePathOfFirstFile.replaceAll("\\\\+", "/");	
		return path;
	} 


}