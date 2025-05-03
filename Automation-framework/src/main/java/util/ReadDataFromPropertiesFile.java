package util;

public class ReadDataFromPropertiesFile {
	
	PropertyFIleReader propertyFileReader = new PropertyFIleReader();
	
	
	//application
	public String webUrl = propertyFileReader.readApplicationFile("webUrl","application");
	public String userName = propertyFileReader.readApplicationFile("userName","application");
	public String password = propertyFileReader.readApplicationFile("password","application");


	
	
}

