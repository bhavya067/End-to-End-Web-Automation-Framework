package pageHelper;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.testng.Assert;

import com.aventstack.extentreports.Status;

import base.DriverTestCase;
import locators.BooksLocators;
import locators.ElectronicsLocators;
import locators.GiftCardsLocators;
import util.DriverHelper;
import util.PropertyFIleReader;
import util.PropertyFileWriter;
import util.ReadDataFromPropertiesFile;

public class ComputersHelper extends DriverTestCase{

	public ComputersHelper(WebDriver driver) {
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
