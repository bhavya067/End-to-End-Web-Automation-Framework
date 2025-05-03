package pageHelper;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.aventstack.extentreports.Status;

import base.CustomLogger;
import locators.LoginPageLocators;
import util.DriverHelper;
import util.ExcelFileReader;
import util.PropertyFileWriter;



public class CommonHelper extends DriverHelper
{

	//public static Logger log4j = LogManager.getLogger(CommonHelper.class);
	private static final CustomLogger log4j = CustomLogger.getLogger(CommonHelper.class);

	public CommonHelper(WebDriver driver) 
	{
		super(driver);	
	}

	public void verifyText(WebElement element,String elementName, String text)
	{
		Assert.assertTrue(getText(element,elementName).contains(text));
	}

	public void waitTillPageLoads(Integer time) {
		try {
			Thread.sleep(time); // Adjust the sleep time based on the download speed
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	public void click(WebElement element, String elementName, String message) {
		String elementString = element.toString();
		String locatorPath = "";

		// Check if the element string contains "->"
		if (elementString.contains("->")) {
			// Extract the relevant part of the element string
			locatorPath = elementString.substring(elementString.indexOf("->"));
		}

		log4j.info(message + ". Element: " + elementName + " " + locatorPath);
		getLogger().info(message + ". Element: " + elementName + " " + locatorPath);
		clickOn(element, elementName, message);
	}

	public void clearTextUsingKeyboardControls() {
		Actions actions = new Actions(driver);
		actions.keyDown(Keys.CONTROL);
		actions.sendKeys("a");
		actions.keyUp(Keys.CONTROL);
		actions.keyDown(Keys.BACK_SPACE);
		actions.keyUp(Keys.BACK_SPACE);
		actions.perform();
	}


	public void enterText(WebElement element, String elementName, String text, String message) {
		String elementString = element.toString();
		String locatorPath = "";

		// Check if the substring "->" is present in the element string
		if (elementString.contains("->")) {
			// Extract the relevant part of the element string
			locatorPath = elementString.substring(elementString.indexOf("->"));
		} else {
			// Handle the case where "->" is not found in the element string
			locatorPath = "Locator path not found";
		}

		log4j.info(message + ". Element: " + elementName + " " + locatorPath);
		getLogger().info(message + ". Element: " + elementName + " " + locatorPath);
		sendKeys(element, elementName, text);
	}


	public void getCss(WebElement locator, String locatorName, String attr)
	{
		getCSSValue(locator,locatorName, attr);
	}

	// Method to perform mouse hover action
	public void hoverOnElement(WebElement element, String elementName) {
		String elementString = element.toString();
		String locatorPath = "";

		// Check if the element string contains "->"
		if (elementString.contains("->")) {
			// Extract the relevant part of the element string
			locatorPath = elementString.substring(elementString.indexOf("->"));
		}

		// Log the message before starting the hover action
		log4j.info("Hovering the mouse over the element: " + elementName + " " + locatorPath);
		getLogger().info("Hovering the mouse over the element: " + elementName + " " + locatorPath);

		try {
			Actions actions = new Actions(driver);
			actions.moveToElement(element).perform();
			log4j.pass("Successfully hovered the mouse over the element: " + elementName + " " + locatorPath);
			getLogger().pass("Successfully hovered the mouse over the element: " + elementName + " " + locatorPath);
		} catch (Exception e) {
			log4j.fail("Unable to hover the mouse over the element: " + elementName + " " + locatorPath);
			getLogger().fail("Unable to hover the mouse over the element: " + elementName + " " + locatorPath);
			throw new RuntimeException("Failed to hover over the element: " + elementName, e);
		}
	}

	public void verifyElementPresent(WebElement element, String elementName) {
		// Convert the WebElement to a string
		String elementString = element.toString();

		// Check if the substring "->" is present in the element string
		if (elementString.contains("->")) {
			// Extract the relevant part of the element string
			String locatorPath = elementString.substring(elementString.indexOf("->"));

			log4j.info("Waiting for element visible: " + elementName + " " + locatorPath);
			getLogger().info("Waiting for element visible: " + elementName + " " + locatorPath);
		} else {
			// Handle the case where "->" is not found in the element string
			log4j.fail("Unable to find the element: " + elementName);
			getLogger().fail("Unable to find the element: " + elementName);
			throw new NoSuchElementException("Element is not visible: " + elementName);
		}

		// Proceed with waiting for the element to be visible
		//WaitForElementVisible(element, 10);
		isElementPresent(element, elementName);
	}


	public void verifyElementNotPresent(WebElement element, String elementName)
	{
		isElementNotPresent(element, elementName);
	}

	public boolean isElementClickable(WebDriver driver, WebElement element, String elementName) {
		try {
			// Convert the WebElement to a string
			String elementString = element.toString();

			// Check if the substring "->" is present in the element string
			if (elementString.contains("->")) {
				// Extract the relevant part of the element string
				String locatorPath = elementString.substring(elementString.indexOf("->"));

				log4j.info("Waiting for element to be clickable: " + elementName + " " + locatorPath);
				getLogger().info("Waiting for element to be clickable: " + elementName + " " + locatorPath);
			} else {
				// Handle the case where "->" is not found in the element string
				log4j.fail("Unable to find the element: " + elementName);
				getLogger().fail("Unable to find the element: " + elementName);
				throw new RuntimeException("Unable to find the element: " + elementName);
			}

			// Wait until the element is clickable
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			wait.until(ExpectedConditions.elementToBeClickable(element));

			// If no exception is thrown, the element is clickable
			log4j.pass("Element is clickable: " + elementName);
			getLogger().pass("Element is clickable: " + elementName);
			return true;
		} catch (Exception e) {
			// If an exception is thrown, the element is not clickable
			log4j.fail("Element is not clickable: " + elementName);
			getLogger().fail("Element is not clickable: " + elementName);
			throw e;
		}
	}

	public boolean waitUntilElementIsNotVisible(WebElement element, String elementName) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		boolean isInvisible = wait.until(ExpectedConditions.invisibilityOf(element));
		if(isInvisible) {
			log4j.pass("Element '" + elementName + "' is no longer visible on the UI.");
			getLogger().log(Status.PASS,"Element '" + elementName + "' is no longer visible on the UI.");
			return isInvisible;
		}
		else {
			log4j.fail("Element '" + elementName + "' is still visible after 30 seconds.");
			getLogger().log(Status.FAIL,"Element '" + elementName + "' is still visible after 30 seconds.");
			return false;
		}
	}



	// Method to clear text in a field
	public void clearTextField(WebElement element, String elementName, String message) {
		String elementString = element.toString();
		String locatorPath = "";

		// Check if the element string contains "->"
		if (elementString.contains("->")) {
			// Extract the relevant part of the element string
			locatorPath = elementString.substring(elementString.indexOf("->"));
		}

		try {
			log4j.info(message + ". Element: " + elementName + " " + locatorPath);
			getLogger().info(message + ". Element: " + elementName + " " + locatorPath); 
			element.click();
			element.clear();
			Thread.sleep(2000);
			log4j.pass("Successfully cleared text in Element: " + elementName + " " + locatorPath);
			getLogger().pass("Successfully cleared text in Element: " + elementName + " " + locatorPath);

		} catch (Exception e) {
			log4j.fail("Failed to clear text in element: " + elementName + " " + locatorPath);
			getLogger().fail("Failed to clear text in element: " + elementName + " " + locatorPath);
			throw new RuntimeException("Unable to clear text in element: " + elementName + " " + locatorPath);
		}
	}

	public void clickOnMandatoryFields(WebDriver driver, WebElement[] mandatoryFields, String[] mandatoryFieldNames) {
	    Actions actions = new Actions(driver);

	    for (int i = 0; i < mandatoryFields.length; i++) {
	        WebElement field = mandatoryFields[i];
	        String fieldName = mandatoryFieldNames[i];  // Get corresponding field name

	        try {
	            // Extract the locator path from the WebElement
	            String fieldString = field.toString();
	            String locatorPath = fieldString.contains("->") ? fieldString.substring(fieldString.indexOf("->")) : ".Locator path not found";

	            // Log the action with the locator path and field name
	            log4j.info("Clicking on mandatory field: " + fieldName);
	            getLogger().info("Clicking on mandatory field: " + fieldName);

	            // Click on the field
	            actions.click(field).perform();

	            // Log success with the locator path and field name
	            log4j.pass("Successfully clicked on mandatory field: " + fieldName + " " + locatorPath);
	            getLogger().pass("Successfully clicked on mandatory field: " + fieldName + " " + locatorPath);

	            // Move to the next field
	            actions.sendKeys(Keys.TAB).perform();
	        } catch (Exception e) {
	            // Extract the locator path from the WebElement
	            String fieldString = field.toString();
	            String locatorPath = fieldString.contains("->") ? fieldString.substring(fieldString.indexOf("->")) : "Locator path not found";

	            // Log failure with the locator path and field name
	            log4j.fail("Failed to click on mandatory field: " + fieldName + " " + locatorPath);
	            getLogger().fail("Failed to click on mandatory field: " + fieldName + " " + locatorPath);

	            // Throw the exception
	            throw new RuntimeException(e);
	        }
	    }
	}
	
	public void scrollToBottom(WebDriver driver) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		long initialHeight = (long) js.executeScript("return document.body.scrollHeight");

		while (true) {
			// Scroll down to the bottom of the page
			js.executeScript("window.scrollTo(0, document.body.scrollHeight);");

			try {
				// Wait for the page to load new content
				Thread.sleep(2000); // Adjust the sleep time as needed
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			// Calculate the new scroll height and compare it with the initial height
			long newHeight = (long) js.executeScript("return document.body.scrollHeight");
			if (newHeight == initialHeight) {
				// If the height has not changed, we have reached the bottom
				break;
			}

			initialHeight = newHeight;
		}
	}

	public void compareAndAssertEquals(String actual, String expected, String customMessage) {
		// Log the comparison with the expected and actual values
		log4j.info(customMessage+ ". Expected: '" + expected + "' with Actual: '" + actual + "'");
		getLogger().info(customMessage+ ". Expected: '" + expected + "' with Actual: '" + actual + "'");

		try {
			// Perform the assertion with a custom failure message
			Assert.assertEquals(actual, expected, "Assertion failed. Expected '" + expected + "' but found '" + actual + "'");

			// Log pass if the values match
			log4j.pass("Assertion successful. Expected: '" + expected + "' and Actual: '" + actual + "'");
			getLogger().pass("Assertion successful. Expected: '" + expected + "' and Actual: '" + actual + "'");
		} catch (AssertionError e) {
			// Log fail if the assertion fails
			log4j.fail("Assertion Failed. Expected '" + expected + "' but found '" + actual + "'");
			getLogger().fail("Assertion Failed. Expected '" + expected + "' but found '" + actual + "'");

			// Rethrow the exception to ensure the getLogger() fails
			throw e;
		}
	}


	public void getPageTitle(String text) 
	{
		Assert.assertTrue(getTitle().contains(text));
	}

	public void getCurrentURL(String text) 
	{
		Assert.assertTrue(getPageURL().contains(text));
	}

	public void scrollDown()
	{
		scrollingDown();
	}

	public void scrollUp()
	{
		scrollingup();
	}

	public void scrollToElement(WebElement element)
	{
		scrollingElement(element);
	}

	public String getElementText(WebElement element, String elementName)
	{
		return getText(element, elementName).trim();
	}

	public void selectDropDownByText(WebElement locator,String locatorName, String targetValue)
	{ 
		selectDropDownByVisibleText(locator,locatorName, targetValue);
	}

	public void selectDropDownByVal(WebElement locator,String locatorName, String targetValue)
	{ 
		selectDropDownByValue(locator,locatorName, targetValue);
	}

	public void WaitForElementToBeClickable(WebElement locator, int timeout) 
	{
		WaitForElementClickable(locator, timeout);
	}

	public void WaitForElementToBeSelected(WebElement locator, int timeout) 
	{
		WaitForElementSelected(locator, timeout);
	}


	public void WaitForElementToBeVisible(WebElement locator, int timeout) 
	{
		WaitForElementVisible(locator, timeout);
	}

	public void waittoPageLoad()
	{
		waitForPageLoad();
	}

	public void previousDate()
	{
		getPrevDate();
	}

	public void waitToPageLoadAjax()
	{
		waitForPageLoadAjax();
	}

	//	public void closeOtherWindows()
	//	{
	//		String mainWindow = driver.getWindowHandle();
	//		Set<String> handles = driver.getWindowHandles();
	//		for(String handle : handles)
	//		{
	//			if(handle !=mainWindow)
	//			{
	//				driver.switchTo().window(handle);
	//				driver.close();
	//			}
	//		}
	//	}

	//	public void selectUser(String userName) 
	//	{
	//		WebElement el = driver.findElement(By.xpath("//li/a[contains(text(),'"+userName+"')]"));
	//		WaitForElementClickable(el, 10);
	//		clickOn(el);		
	//	}
	public String getCurrentDate()
	{
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		return sdf.format(new Date()).toString();
	}
	public String randomNumber()
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddmmss");
		return sdf.format(new Date()).toString();
	}
	public void multiSelect(WebElement locator, String locatorName, String targetValue1, String targetValue2){
		select2DropDownByVisibleText(locator,locatorName, targetValue1, targetValue2);
	}

	public static void scrollElementToRight(WebDriver driver, WebElement element, int distance) {
		try {
			// Cast WebDriver instance to JavascriptExecutor
			JavascriptExecutor js = (JavascriptExecutor) driver;

			// Execute JavaScript to perform horizontal scroll
			js.executeScript("arguments[0].scrollLeft += " + distance + ";", element);

			// Optional: Add a small delay to see the effect
			Thread.sleep(1000); // Adjust delay as needed

		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}



	public static void scrollElementDown(WebDriver driver, WebElement element, int distance) {
		try {
			// Cast WebDriver instance to JavascriptExecutor
			JavascriptExecutor js = (JavascriptExecutor) driver;

			// Execute JavaScript to perform vertical scroll
			js.executeScript("arguments[0].scrollTop += " + distance + ";", element);

			// Optional: Add a small delay to see the effect
			Thread.sleep(1000); // Adjust delay as needed

		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void scrollToExtremeRightInContainer(WebDriver driver, WebElement container) {
		// Use JavaScriptExecutor to scroll horizontally to the target element
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].scrollLeft = arguments[0].scrollWidth;", container);
	}

	public void validateColumnNames(String fileName, List<String> expectedColumnNames) throws IOException {
		log4j.info("Going inside validate Column Names method");
		getLogger().info("Going inside validate Column Names method");

		//String downloadFolder = System.getProperty("user.dir") + "\\downloads\\";
		File latestFile = ExcelFileReader.getLatestFilefromDir(downloadFolderWithTimestamp, fileName);

		if (latestFile != null) {
			String filePath = latestFile.getAbsolutePath();
			String sheetName = "Sheet1";
			List<String> columnNames = ExcelFileReader.readExcel(filePath, sheetName);

			// Example: Compare column names with expected names

			ExcelFileReader.compareColumnNames(columnNames, expectedColumnNames);
		} else {
			log4j.fail("No files found in the download folder.");
			getLogger().fail("No files found in the download folder.");
			Assert.fail("No files found in the download folder.");

		}

	}

	public void validateColumnValues(String fileName,String connectionName,  List<String> expectedColumnValues) throws IOException {
		log4j.info("Going inside validate Column values method");
		getLogger().info("Going inside validate Column values method");

		//String downloadFolder = System.getProperty("user.dir") + "\\downloads\\";
		File latestFile = ExcelFileReader.getLatestFilefromDir(downloadFolderWithTimestamp, fileName);

		if (latestFile != null) {

			String filePath = latestFile.getAbsolutePath();
			String sheetName = "Sheet1";

			List<String> record = ExcelFileReader.readFilteredRecord(filePath, sheetName, connectionName);

			ExcelFileReader.compareColumnValues(record, expectedColumnValues);
			
		} else {
			log4j.fail("No files found in the download folder.");
			getLogger().fail("No files found in the download folder.");
			Assert.fail("No files found in the download folder.");
		}

	}

	// Use a helper function to wait for the download to complete
	public boolean waitForDownloadToComplete(String downloadDirPath, String fileName) {
		File file = new File(downloadDirPath + File.separator + fileName);
		int waitTime = 60; // seconds
		while (waitTime > 0) {
			if (file.exists()) {
				getLogger().pass("File downloaded successfully: " + file.getAbsolutePath());
				log4j.pass("File downloaded successfully: " + file.getAbsolutePath());
				return true;
			} else {
				try {
					Thread.sleep(1000);
					waitTime--;
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		log4j.fail("File not downloaded: " + file.getAbsolutePath());
		getLogger().fail("File not downloaded: " + file.getAbsolutePath());
		Assert.fail("File not downloaded: " + file.getAbsolutePath());
		return false;
	}


	public String getDownloadedFileName(String downloadPath, String prefix) {
		File downloadDir = new File(downloadPath);
		File[] files = downloadDir.listFiles();
		if (files != null) {
			for (File file : files) {
				if (file.isFile() && file.getName().startsWith(prefix)) {
					return file.getName(); // Return the name of the file that starts with the prefix
				}
			}
		}
		return null; // Return null if no file with the given prefix is found
	}
	

}