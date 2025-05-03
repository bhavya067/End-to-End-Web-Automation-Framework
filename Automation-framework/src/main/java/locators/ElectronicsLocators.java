package locators;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

public class ElectronicsLocators {

	
	public ElectronicsLocators(WebDriver driver)
	{
		PageFactory.initElements(new AjaxElementLocatorFactory(driver, 30), this);
	}
	
	@FindBy(xpath = "//ul[@class='top-menu']//a[normalize-space()='Electronics']")
    public WebElement electronicsHeaderBtn;
	
	@FindBy(xpath = "//h1[normalize-space()='Electronics']")
    public WebElement electronicsTileInElectronicsTab;
	
	}