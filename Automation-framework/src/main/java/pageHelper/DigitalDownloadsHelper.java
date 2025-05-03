package pageHelper;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

import com.aventstack.extentreports.Status;

import base.DriverTestCase;
import locators.BooksLocators;
import locators.DigitalDownloadsLocators;
import locators.ElectronicsLocators;
import util.PropertyFIleReader;
import util.PropertyFileWriter;
import util.ReadDataFromPropertiesFile;

public class DigitalDownloadsHelper extends DriverTestCase{

	public DigitalDownloadsHelper(WebDriver driver) {
		super();
		setDriver(driver);
		PageFactory.initElements(new AjaxElementLocatorFactory(driver, 10), this);
		this.electronicsLocators= new ElectronicsLocators(driver);	
		this.propertyfilewriter= new PropertyFileWriter();
		this.commonHelper = new CommonHelper(driver);
		this.propertyFileReader = new PropertyFIleReader();
		this.properties = new ReadDataFromPropertiesFile();
		this.booksLocators = new BooksLocators(driver);

	}

	}
