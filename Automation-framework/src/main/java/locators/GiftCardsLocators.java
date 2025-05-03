package locators;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

import base.DriverTestCase;

public class GiftCardsLocators extends DriverTestCase{

	public GiftCardsLocators(WebDriver driver)
	{
		PageFactory.initElements(new AjaxElementLocatorFactory(driver, 30), this);
	}

	@FindBy(xpath = "//ul[@class='top-menu']//a[normalize-space()='Gift Cards']")
    public WebElement giftCardsHeaderBtn;
	
	@FindBy(xpath = "//h1[normalize-space()='Gift Cards']")
    public WebElement giftCardsTileInGiftCardsTab;
	
	
	
	
}
