package testCases.digitalDownloads;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import com.aventstack.extentreports.Status;
import base.DriverTestCase;

public class DigitalDownloads extends DriverTestCase{

	@Parameters({""})
	@Test(description = "Verify that user is able to navigate to 'Digital Downloads' section" ,groups = {"Digital Downloads"}, priority = 1)
	public void validateDigitalDownloadsSection() throws InterruptedException {
		getLogger().assignAuthor("Bhavya");
		loginToPlatform();
		commonHelper.verifyElementPresent(digitalDownloadsLocators.digitalDownloadsHeaderBtn, "digitalDownloadsHeaderBtn");	
		commonHelper.click(digitalDownloadsLocators.digitalDownloadsHeaderBtn, "digitalDownloadsHeaderBtn", "Clicking on 'Books' header button");	
		commonHelper.verifyElementPresent(digitalDownloadsLocators.digitalDownloadsHeaderInTab, "digitalDownloadsHeaderInTab");
		logoutFromPlatform();
		getLogger().pass("Successfully navigated to 'Digital Downloads' section and validated the header");
	}
	
	@Parameters({""})
	@Test(description = "Verify that user is able to navigate to other sections from 'Digital Downloads' section" ,groups = {"Digital Downloads"}, priority = 2)
	public void validateOtherSectionsNavigationFromDownloads() throws InterruptedException {
		getLogger().assignAuthor("Bhavya");
		loginToPlatform();
		commonHelper.verifyElementPresent(digitalDownloadsLocators.digitalDownloadsHeaderBtn, "digitalDownloadsHeaderBtn");	
		commonHelper.click(digitalDownloadsLocators.digitalDownloadsHeaderBtn, "digitalDownloadsHeaderBtn", "Clicking on 'Digital Downloads' header button");	
		commonHelper.verifyElementPresent(digitalDownloadsLocators.digitalDownloadsHeaderInTab, "digitalDownloadsHeaderInTab");
		
		commonHelper.verifyElementPresent(computersLocators.computersHeaderBtn, "computersHeaderBtn");	
		commonHelper.click(computersLocators.computersHeaderBtn, "computersHeaderBtn", "Clicking on 'Computers' header button");	
		commonHelper.verifyElementPresent(computersLocators.computersTileInComputersTab, "computersTileInComputersTab");
		
		commonHelper.verifyElementPresent(electronicsLocators.electronicsHeaderBtn, "electronicsHeaderBtn");	
		commonHelper.click(electronicsLocators.electronicsHeaderBtn, "electronicsHeaderBtn", "Clicking on 'Electronics' header button");	
		commonHelper.verifyElementPresent(electronicsLocators.electronicsTileInElectronicsTab, "electronicsTileInElectronicsTab");
		
		commonHelper.verifyElementPresent(booksLocators.booksHeaderBtn, "booksHeaderBtn");	
		commonHelper.click(booksLocators.booksHeaderBtn, "booksHeaderBtn", "Clicking on 'Books' header button");	
		commonHelper.verifyElementPresent(booksLocators.booksTileInBooksTab, "booksTileInBooksTab");
		
		commonHelper.verifyElementPresent(giftCardsLocators.giftCardsHeaderBtn, "giftCardsHeaderBtn");	
		commonHelper.click(giftCardsLocators.giftCardsHeaderBtn, "giftCardsHeaderBtn", "Clicking on 'Gift Cards' header button");	
		commonHelper.verifyElementPresent(giftCardsLocators.giftCardsTileInGiftCardsTab, "giftCardsTileInGiftCardsTab");
		logoutFromPlatform();
		getLogger().pass("Successfully navigated to different sections from 'Digital Downloads' section.");
	}
}