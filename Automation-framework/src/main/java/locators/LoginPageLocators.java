package locators;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aventstack.extentreports.Status;

import base.CustomLogger;
import base.DriverTestCase;
import testCases.electronics.CameraPhotos;

public class LoginPageLocators extends DriverTestCase {
	private static final CustomLogger log4j = CustomLogger.getLogger(LoginPageLocators.class);
	public LoginPageLocators(WebDriver driver)
	{
		PageFactory.initElements(new AjaxElementLocatorFactory(driver, 30), this);
	}

	@FindBy(xpath = "//input[@class='email']")
    public WebElement usernameInput;

    @FindBy(xpath = "//input[@class='password']")
    public WebElement passwordInput;

    @FindBy(xpath = "//input[@type='submit'][@value='Log in']")
    public WebElement loginButton;
    
    @FindBy(xpath = "//a[normalize-space()='Log in']")
    public WebElement loginButtonAfterLogout;
   
    @FindBy(xpath = "//a[normalize-space()='Log out']")
    public WebElement logOutButton;

    @FindBy(xpath = "//a[text()='Log in']")
    public WebElement loginHeaderBtn;
    
    @FindBy(xpath = "//strong[text()='Returning Customer']")
    public WebElement loginPage;
    
    @FindBy(xpath = "//img[@alt='Tricentis Demo Web Shop']")
    public WebElement platformHeaderTitle;
    
    public void setUserName(String un) 
    { 
    	String elementString = usernameInput.toString();
    	// Extract the relevant part of the element string
        String locatorPath = elementString.substring(elementString.indexOf("->"));
        try {
            usernameInput.sendKeys(un); 
            log4j.pass("Successfully inserted Username '"+ un+"' to element : usernameInput "+locatorPath);
        } catch (Exception e) {
            log4j.fail("Failed to insert Username '"+ un+"' to element : usernameInput "+locatorPath+ ". Exception: " + e.getMessage());
           // throw new RuntimeException("Setting username failed", e);
        }
    }

    public void setPassword(String pw) {
    	String elementString = passwordInput.toString();
    	// Extract the relevant part of the element string
        String locatorPath = elementString.substring(elementString.indexOf("->"));
    
    StringBuilder starCode = new StringBuilder();
	for (char c : pw.toCharArray()) {
		starCode.append("*"); // Each character is replaced by 1 asterisk
	}
	//return starCode.toString();
	String starCodedPassword=starCode.toString();
    try {
    	passwordInput.sendKeys(pw);
        log4j.pass("Successfully inserted Password '"+ starCodedPassword+"' to element : passwordInput "+locatorPath);
        getLogger().log(Status.PASS, "Successfully inserted Password '"+ starCodedPassword+"' to element : passwordInput "+locatorPath);
    }
    catch (Exception e) {
    	log4j.fail("Failed to insert Password '"+ starCodedPassword+"' to element : passwordInput "+locatorPath+ ". Exception: " + e.getMessage());
        getLogger().log(Status.FAIL, "Failed to insert Password '"+ starCodedPassword+"' to element : passwordInput "+locatorPath+ ". Exception: " + e.getMessage());

        //throw new RuntimeException("Setting username failed", e);
    }
    }
}
