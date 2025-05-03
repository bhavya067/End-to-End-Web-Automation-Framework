package pageHelper;
import java.awt.AWTException;
import java.awt.Frame;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.Parameters;

import com.aventstack.extentreports.Status;

import base.DriverTestCase;
import locators.BooksLocators;
import locators.ElectronicsLocators;
import util.DriverHelper;
import util.PropertyFIleReader;
import util.PropertyFileWriter;
import util.ReadDataFromPropertiesFile;
public class ElectronicsHelper extends DriverTestCase{
	public ElectronicsHelper(WebDriver driver) {
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
