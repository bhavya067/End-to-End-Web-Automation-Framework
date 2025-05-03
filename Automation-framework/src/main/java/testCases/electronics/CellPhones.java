package testCases.electronics;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;

import base.DriverTestCase;
import util.PropertyFileWriter;

public class CellPhones extends DriverTestCase {

	@Parameters({""})
	@Test(description = "Verify that user is able to navigate to 'Camera Photos' section in 'Electronics'" ,groups = {"Electronics"}, priority = 3)
	public void validateCellPhonesSectioninElectronics() throws InterruptedException {
		getLogger().assignAuthor("Bhavya");
		loginToPlatform();
		commonHelper.verifyElementPresent(booksLocators.booksHeaderBtn, "booksHeaderBtn");	
		commonHelper.click(booksLocators.booksHeaderBtn, "booksHeaderBtn", "Clicking on 'Books' header button");	
		commonHelper.verifyElementPresent(booksLocators.booksTileInBooksTab, "booksTileInBooksTab");
		logoutFromPlatform();
		getLogger().pass("Successfully navigated to 'Cell Phones' section in 'Electronics' and validated the header");
	}
	
	@Parameters({""})
	@Test(description = "Verify that user is able to navigate to other sections from 'Camera Photos' section in 'Electronics'" ,groups = {"Electronics"}, priority = 4)
	public void validateOtherSectionsNavigationFromCellPhones() throws InterruptedException {
		getLogger().assignAuthor("Bhavya");
		loginToPlatform();
		commonHelper.verifyElementPresent(booksLocators.booksHeaderBtn, "booksHeaderBtn");	
		commonHelper.click(booksLocators.booksHeaderBtn, "booksHeaderBtn", "Clicking on 'Books' header button");	
		commonHelper.verifyElementPresent(booksLocators.booksTileInBooksTab, "booksTileInBooksTab");
		
		commonHelper.verifyElementPresent(computersLocators.computersHeaderBtn, "computersHeaderBtn");	
		commonHelper.click(computersLocators.computersHeaderBtn, "computersHeaderBtn", "Clicking on 'Computers' header button");	
		commonHelper.verifyElementPresent(computersLocators.computersTileInComputersTab, "computersTileInComputersTab");
		
		commonHelper.verifyElementPresent(electronicsLocators.electronicsHeaderBtn, "electronicsHeaderBtn");	
		commonHelper.click(electronicsLocators.electronicsHeaderBtn, "electronicsHeaderBtn", "Clicking on 'Electronics' header button");	
		commonHelper.verifyElementPresent(electronicsLocators.electronicsTileInElectronicsTab, "electronicsTileInElectronicsTab");
		
		commonHelper.verifyElementPresent(digitalDownloadsLocators.digitalDownloadsHeaderBtn, "digitalDownloadsHeaderBtn");	
		commonHelper.click(digitalDownloadsLocators.digitalDownloadsHeaderBtn, "digitalDownloadsHeaderBtn", "Clicking on 'Digital Downloads' header button");	
		commonHelper.verifyElementPresent(digitalDownloadsLocators.digitalDownloadsHeaderInTab, "digitalDownloadsHeaderInTab");
		
		commonHelper.verifyElementPresent(giftCardsLocators.giftCardsHeaderBtn, "giftCardsHeaderBtn");	
		commonHelper.click(giftCardsLocators.giftCardsHeaderBtn, "giftCardsHeaderBtn", "Clicking on 'Gift Cards' header button");	
		commonHelper.verifyElementPresent(giftCardsLocators.giftCardsTileInGiftCardsTab, "giftCardsTileInGiftCardsTab");
		logoutFromPlatform();
		getLogger().pass("Successfully navigated to different sections from 'Cell Phones' section in 'Electronics'.");
	}
}
