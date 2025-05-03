package util;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import base.CustomLogger;
import base.DriverTestCase;
import locators.LoginPageLocators;

public class DriverHelper extends DriverTestCase
{
    private static final CustomLogger log4j = CustomLogger.getLogger(DriverHelper.class);
	protected WebDriver driver;
	protected WebDriverWait wait;

	public DriverHelper(WebDriver driver)
	{
		this.driver = driver;	
		PageFactory.initElements(driver, this);
		
	}

	public WebDriver getWebDriver()
	{
		return driver;
	}

	public WebDriverWait getWait(int timeOut)
	{
		wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		wait.ignoring(StaleElementReferenceException.class);
		return wait;
	}

	public Boolean isElementPresent(WebElement locator, String locatorName) {
	    Boolean result = false;
	    String elementString = locator.toString();
	    String locatorPath = "";

	    // Check if the substring "->" is present in the element string
	    if (elementString.contains("->")) {
	        // Extract the relevant part of the element string
	        locatorPath = elementString.substring(elementString.indexOf("->"));
	    } else {
	        // Handle the case where "->" is not found in the element string
	        locatorPath = "Element locator not found";
            throw new NoSuchElementException("Element locator not found");

	    }

	    try {
	        if (locator.isDisplayed()) {
	            result = true;
	            log4j.pass("The element is visible: " + locatorName + " " + locatorPath);
	            getLogger().pass("The element is visible: " + locatorName + " " + locatorPath);
	        }
	    } catch (Exception e) {
	        log4j.fail("The element is not visible: " + locatorName + " " + locatorPath);
	        getLogger().fail("The element is not visible: " + locatorName + " " + locatorPath);
            throw new NoSuchElementException("Element is not visible: " + locatorName + " " + locatorPath);
	    }
	    return result;
	}


	public Boolean isElementNotPresent(WebElement locator, String locatorName) {
	    Boolean result = true;
	    String elementString = locator.toString();
	    // Extract the relevant part of the element string safely
	    int index = elementString.indexOf("->");
	    String locatorPath = (index != -1) ? elementString.substring(index) : "Unknown locator path";
	    
	    try {
	    	WebElement element = locator;
	        if (element != null && element.isDisplayed()) {
	            result = false;
	            log4j.fail("The element is unexpectedly visible: " + locatorName + ". " + locatorPath);
	            getLogger().fail("The element is unexpectedly visible: " + locatorName + ". " + locatorPath);
	            Assert.fail("The element is visible so the test case is failed");
	        } else {
	            log4j.pass("The element is not visible as expected: " + locatorName + " " + locatorPath);
	            getLogger().pass("The element is not visible: " + locatorName + " " + locatorPath);
	        }
	    } catch (NoSuchElementException e) {
	        // Handle specific exceptions indicating element not found
	        log4j.pass("The element is not present: " + locatorName + ". " + locatorPath + " (Exception: " + e.getMessage() + ")");
	        getLogger().pass("The element is not present: " + locatorName + ". " + locatorPath + " (Exception: " + e.getMessage() + ")");
	    } catch (StaleElementReferenceException e) {
	        // Handle specific exceptions indicating element reference is stale
	        log4j.pass("The element is not interactable: " + locatorName + ". " + locatorPath + " (Exception: " + e.getMessage() + ")");
	        getLogger().pass("The element is not interactable: " + locatorName + ". " + locatorPath + " (Exception: " + e.getMessage() + ")");
	    } catch (Exception e) {
	        // Handle other unexpected exceptions
	        log4j.fail("Unexpected error while checking element presence: " + locatorName + ". " + locatorPath);
	        getLogger().fail("Unexpected error while checking element presence: " + locatorName + ". " + locatorPath);
	    }
	    
	    return result;
	}

	// Method to convert a string into star code
	public String toStarCode(String input) {
		StringBuilder starCode = new StringBuilder();
		for (char c : input.toCharArray()) {
			starCode.append("*"); // Each character is replaced by 1 asterisk
		}
		return starCode.toString();
	}


	public void WaitForElementSelected(WebElement locator, int timeout)
	{
		getWait(timeout).until(ExpectedConditions.elementToBeSelected(locator));
	}

	public void WaitForElementNotVisible(WebElement locator, int timeout)
	{
		getWait(timeout).until(ExpectedConditions.invisibilityOf(locator));
	}

	public void WaitForElementVisible(WebElement locator, int timeout)
	{
		getWait(timeout).until(ExpectedConditions.visibilityOf(locator));
	}

	public void WaitForElementClickable(WebElement locator, int timeout) 
	{
		getWait(timeout).until(ExpectedConditions.elementToBeClickable(locator));
	}

	public void waitForTitle(String title)
	{
		getWait(20).until(ExpectedConditions.titleContains(title));
	}

	public void mouseOver(WebElement locator)
	{		
		this.WaitForElementVisible(locator, 20);		
		WebElement el = locator;
		Actions builder = new Actions(getWebDriver());    
		builder.moveToElement(el).build().perform();		
	}

	public void mouseDoubleClick(WebElement locator)
	{
		this.WaitForElementVisible(locator, 20);		
		WebElement el = locator;
		Actions builder = new Actions(getWebDriver());    
		builder.doubleClick(el).perform();
	}

	public void dragAndDrop(WebElement draggable, WebElement to)
	{
		WebElement elDraggable = draggable;
		WebElement todrag = to;
		Actions builder = new Actions(getWebDriver());   
		builder.dragAndDrop(elDraggable, todrag).build().perform();	

	}

	public void clickOn(WebElement locator, String locatorName, String message) {
		String locatorPath = "";

	    try {
	        // Check if locator is null to avoid NullPointerException
	        if (locator == null) {
	            throw new NullPointerException("Locator is null for element: " + locatorName);
	        }

	        WaitForElementClickable(locator, 20);
	        Assert.assertTrue(isElementPresent(locator, locatorName), "Element Locator :" + locator + " Not found");

	        String elementString = locator.toString();
	        locatorPath = elementString.contains("->") ? elementString.substring(elementString.indexOf("->")) : "Locator path could not be determined.";

	        try {
	            locator.click();
	            log4j.pass("Successfully clicked on element: " + locatorName + " " + locatorPath);
	            getLogger().pass("Successfully clicked on element: " + locatorName + " " + locatorPath);
	        } catch (StaleElementReferenceException e1) {
	            // Handle stale element exception and retry click
	            log4j.info("StaleElementReferenceException encountered, retrying click for: " + locatorName + " " + locatorPath);
	            getLogger().info("StaleElementReferenceException encountered, retrying click for: " + locatorName + " " + locatorPath);
	            // Re-fetch the locator
	            locator = driver.findElement(By.xpath(locator.toString())); 
	            locator.click();
	            log4j.pass("Successfully clicked on element after retry: " + locatorName + " " + locatorPath);
	            getLogger().pass("Successfully clicked on element after retry: " + locatorName + " " + locatorPath);
	        } catch (Exception e1) {
	            log4j.info("Normal click failed, attempting JavaScript click on element: " + locatorName + " " + locatorPath);
	            getLogger().info("Normal click failed, attempting JavaScript click on element: " + locatorName + " " + locatorPath);
	            try {
	                JavascriptExecutor js = (JavascriptExecutor) driver;
	                js.executeScript("arguments[0].click();", locator);
	                log4j.pass("Successfully clicked using JavaScript: " + locatorName + " " + locatorPath);
	                getLogger().pass("Successfully clicked using JavaScript: " + locatorName + " " + locatorPath);
	            } catch (Exception e2) {
	                log4j.fail("Unable to click on the element using both normal and JavaScript click: " + locatorName + " " + locatorPath);
	                getLogger().fail("Unable to click on the element using both normal and JavaScript click: " + locatorName + " " + locatorPath);
	                throw e2; // Re-throw exception to stop the getLogger() execution
	            }
	        }
	    } catch (NullPointerException npe) {
	        log4j.fail("NullPointerException: " + npe.getMessage());
	        getLogger().fail("NullPointerException: " + npe.getMessage());
	        throw npe;  // Re-throw the NullPointerException
	    } catch (Exception e) {     
	        log4j.fail("Unable to click on element: " + locatorName + " " + locatorPath);
	        getLogger().fail("Unable to click on element: " + locatorName + " " + locatorPath);
	        log4j.fail(e.getMessage());
	        getLogger().fail(e.getMessage());
	        throw e;
	    }
	}

	public void clearTextField(WebElement locator, String locatorName)
	{		
		//this.WaitForElementVisible(locator, 20);
		Assert.assertTrue(isElementPresent(locator,locatorName),"Element Locator :"+locator+" Not found");
		locator.clear();
	}

	public String getPrevDate()
	{
		Date date = new Date();
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -7);
		date = cal.getTime();
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		return dateFormat.format(date).toString();
	}

	public void sendKeys(WebElement locator, String locatorName, String text) {
	    String elementString = locator.toString();
	    String locatorPath = elementString.substring(elementString.indexOf("->"));
		//wait = new WebDriverWait(driver, Duration.ofSeconds(30));

	    try {
	        //WaitForElementVisible(locator, 20);
	        Assert.assertTrue(isElementPresent(locator, locatorName), "Element Locator :" + locator + " Not found");
	        locator.clear();
	        locator.sendKeys(text);
	        log4j.pass("Successfully Entered the text: '" + text + "' in element: " + locatorName + " " + locatorPath); 
	        getLogger().pass("Successfully Entered the text: '" + text + "' in element: " + locatorName + " " + locatorPath); 
	    } catch (AssertionError ae) {
	        log4j.fail("Assertion failed for element: " + locatorName + " " + locatorPath);
	        getLogger().fail("Assertion failed for element: " + locatorName + " " + locatorPath);
	        throw ae; // Rethrow to ensure the getLogger() fails
	    } catch (Exception e) {
	        log4j.fail("Failed to enter the text: '" + text + "' in element: " + locatorName + " " + locatorPath);
	        getLogger().fail("Failed to enter the text: '" + text + "' in element: " + locatorName + " " + locatorPath);
	        throw e; // Rethrow to ensure the getLogger() fails
	    }
	}


	public void uploadFile(WebElement locator,String locatorName, String text) 
	{	
		WaitForElementVisible(locator, 20);
		Assert.assertTrue(isElementPresent(locator,locatorName), "Element Locator :"+locator+" Not found");
		System.out.println(text);
		locator.sendKeys(text);
	}

	public void selectDropDownByVisibleText(WebElement locator, String locatorName, String targetValue)
	{ 
		Assert.assertTrue(isElementPresent(locator,locatorName),"Element Locator :"+locator+" Not found");
		this.WaitForElementVisible(locator, 20);
		new Select(locator).selectByVisibleText(targetValue);		
	}

	public void selectDropDownByValue(WebElement locator, String locatorName, String targetValue)
	{ 
		Assert.assertTrue(isElementPresent(locator, locatorName),"Element Locator :"+locator+" Not found");
		this.WaitForElementVisible(locator, 20);
		new Select(locator).selectByValue(targetValue);
	}

	public boolean isTextPresent(WebElement locator,String locatorName, String str)
	{
		WaitForElementVisible(locator, 20);
		Assert.assertTrue(isElementPresent(locator,locatorName) ,"Element Locator :"+locator+" Not found");
		String message = locator.getText();		
		System.out.println("VALUE : " + message);
		if(message.contains(str))
		{
			return true;
		}
		else 
		{	
			return false; 
		}
	}

	public String getText(WebElement locator,String locatorName)
	{
		WaitForElementVisible(locator, 30);
		Assert.assertTrue(isElementPresent(locator,locatorName),"Element Locator :"+locator+" Not found");
		String text = locator.getText();			
		return text;
	}

	public String getTitle()
	{
		String text = getWebDriver().getTitle();		
		return text;
	}

	public String getPageURL()
	{
		String text = getWebDriver().getCurrentUrl();		
		return text;
	}

	public void switchToFrame(WebElement locator)
	{
		WaitForElementVisible(locator, 20);
		driver.switchTo().frame(locator);
	}

	public boolean isChecked(WebElement locator, String locatorNmae)
	{
		boolean checkStatus = false;
		WaitForElementVisible(locator, 20);
		Assert.assertTrue(isElementPresent(locator,locatorNmae),"Element Locator :"+locator+" Not found");
		WebElement el = locator;	    
		checkStatus = el.isSelected();	    
		return checkStatus;
	}

	public String getAttribute(WebElement locator, String locatorName, String attribute)
	{
		WaitForElementVisible(locator, 20);
		Assert.assertTrue(isElementPresent(locator, locatorName),"Element Locator :"+locator+" Not found");
		String text = locator.getAttribute(attribute);	
		return text;
	}

	public void javaScriptExecute(String javascrpt)
	{
		JavascriptExecutor js = (JavascriptExecutor) getWebDriver();
		js.executeScript(javascrpt);
	}

	public void scrollingElement(WebElement locator)
	{
		JavascriptExecutor jse = (JavascriptExecutor)driver;
		jse.executeScript("arguments[0].scrollIntoView();", locator);
	}

	public void clickByActionClass(WebElement locator)
	{
		Actions action = new Actions(driver);
		WaitForElementVisible(locator, 20);
		action.moveToElement(locator).click().build().perform();
	}

	public void scrollingDown()
	{
		JavascriptExecutor jse = (JavascriptExecutor)driver;
		jse.executeScript("window.scrollBy(0,150)", "");
		//((JavascriptExecutor) d).executeScript("window.scrollTo(0, document.body.scrollHeight)");
	}

	public void scrollingup()
	{
		JavascriptExecutor jse = (JavascriptExecutor)driver;
		jse.executeScript("window.scrollTo(0, -document.body.scrollHeight);");
	}


	public String getCSSValue(WebElement locator, String locatorName, String attribute)
	{
		WaitForElementVisible(locator, 20);
		Assert.assertTrue(isElementPresent(locator,locatorName),"Element Locator :"+locator+" Not found");
		return locator.getCssValue(attribute);
	}

	public void javascriptSendKeys(WebElement locator, String text) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].value='" + text + "'", locator);
	}

	public String getCurrentDate()
	{
		Date date = new Date();
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		return dateFormat.format(date).toString();
	}


	public int randomNumberInfinite()
	{
		return (int) System.currentTimeMillis();
	}

	public void waitForPageLoad()
	{
		driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
	}

	public int tableRowCount(String tblLocator)
	{
		int rowNumber =driver.findElements(By.xpath(tblLocator)).size();
		return rowNumber;				
	}

	public void acceptPopup()
	{
		Alert alert =driver.switchTo().alert();
		alert.accept();
	}

	public void setAttributeVisible(WebElement locator)
	{
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].removeAttribute('style', 'visibility: visible;');", locator);

	}

	public void waitForPageLoadAjax()
	{
		while(true)
		{
			String value = ((JavascriptExecutor) driver).executeScript("return document.readyState").toString();
			if(value.equals("complete"))
			{
				break;
			}
		}
	}

	public Boolean DropDownexist(WebElement locator) 
	{	
		boolean result=false;
		boolean dropdownPresence,dropdownEnabled = false;
		dropdownPresence = locator.isDisplayed();
		dropdownEnabled = locator.isEnabled();   
		try 
		{	if (dropdownPresence==true && dropdownEnabled==true)
		{
			result = true;
		}
		} 
		catch (Exception ex){}
		return result;	
	}

	public void select2DropDownByVisibleText(WebElement locator, String locatorName, String targetValue1, String targetValue2)
	{ 
		Assert.assertTrue(isElementPresent(locator, locatorName),"Element Locator :"+locator+" Not found");
		this.WaitForElementVisible(locator, 20);
		new Select(locator).selectByVisibleText(targetValue1);	
		new Select(locator).selectByVisibleText(targetValue2);

	}

}