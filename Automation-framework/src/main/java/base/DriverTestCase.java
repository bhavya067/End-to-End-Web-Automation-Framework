package base;
import java.io.File;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import io.github.bonigarcia.wdm.WebDriverManager;
import locators.BooksLocators;
import locators.ComputersLocators;
import locators.ElectronicsLocators;
import locators.DigitalDownloadsLocators;
import locators.LoginPageLocators;
import locators.GiftCardsLocators;
import pageHelper.BooksHelper;
import pageHelper.CommonHelper;
import pageHelper.ElectronicsHelper;
import pageHelper.DigitalDownloadsHelper;
import testCases.digitalDownloads.DigitalDownloads;
import pageHelper.ComputersHelper;
import pageHelper.GiftCardsHelper;
import util.DriverHelper;
import util.PropertyFIleReader;
import util.PropertyFileWriter;
import util.ReadDataFromPropertiesFile;


public abstract class DriverTestCase
{
	public ThreadLocal<WebDriver> driver = new ThreadLocal<>();
	public static ExtentReports extent;
    private static ThreadLocal<ExtentTest> testThread = new ThreadLocal<>();
	public static PropertyFIleReader propertyFileReader;
	public static String applicationUrl = null;
	public static String browsername = null;			
	public static String usernameClient =  null;
	public static String passwordClient= null;
	public String tag = "";
	public static String id=null;
	public String userName=null;
	public String password=null;
	public DriverHelper driverHelper;
	public PropertyFileWriter propertyfilewriter;
	public ReadDataFromPropertiesFile properties;
	public static ExtentSparkReporter sparkReporter;
	public LoginPageLocators loginPageLocators;
	public BooksLocators booksLocators;
	public ElectronicsLocators electronicsLocators;
	public GiftCardsLocators giftCardsLocators ;
	public ComputersLocators computersLocators;
	public DigitalDownloadsLocators digitalDownloadsLocators;
	public ElectronicsHelper electronicsHelper;
	public CommonHelper commonHelper;
	public BooksHelper booksHelper;
	public DigitalDownloadsHelper digitalDownloadsHelper;
	public GiftCardsHelper giftCardsHelper;
	public ComputersHelper computersHelper;
	
	private void initializeLocators(WebDriver driver) {
		properties = new ReadDataFromPropertiesFile();
		driverHelper = new DriverHelper(driver);
		propertyfilewriter = new PropertyFileWriter();

		electronicsHelper = new ElectronicsHelper(driver);
		commonHelper = new CommonHelper(driver);
		digitalDownloadsHelper = new DigitalDownloadsHelper(driver);
		booksHelper = new BooksHelper(driver);
		giftCardsHelper = new GiftCardsHelper(driver);
		computersHelper = new ComputersHelper(driver);

		loginPageLocators = new LoginPageLocators(driver);
		booksLocators = new BooksLocators(driver);
		computersLocators = new ComputersLocators(driver);
		electronicsLocators = new ElectronicsLocators(driver);
		giftCardsLocators = new GiftCardsLocators(driver);
		digitalDownloadsLocators = new DigitalDownloadsLocators(driver);
		giftCardsLocators = new GiftCardsLocators(driver);
	}
	
	public void setDriver(WebDriver driver) {
		this.driver.set(driver);
	}
	
	public WebDriver getDriver() {
		return this.driver.get();
	}
	
	public static ExtentTest getLogger() {
	    return testThread.get();
	}

	enum DriverType
	{
		Firefox, Edge, Chrome, Safari
	}

	static String projectBasePath = System.getProperty("user.dir");
	String reportsPath = projectBasePath + File.separator +"Reports";
	String screenshotsPath = projectBasePath + File.separator + "Screenshots";
	String testOutputPath = projectBasePath + File.separator + "files";
	static String downloadFolder = projectBasePath + File.separator + "downloads";
	public static String timeStamp=timeStamp();
	public static String currentDate=currentDate();
	protected static final CustomLogger log4j = CustomLogger.getLogger(DriverTestCase.class);
	protected static final String downloadFolderWithTimestamp = downloadFolder + File.separator + currentDate + File.separator + timeStamp ;
	protected static String downloadPath;

	@BeforeSuite
    public void beforeSuit() {
        try {
            // Delete existing directories if present
            FileUtils.deleteDirectory(new File(reportsPath + "/recentRunReport"));

            // Create new directories
            FileUtils.forceMkdir(new File(screenshotsPath + "/" + currentDate));
            FileUtils.forceMkdir(new File(screenshotsPath + "/" + currentDate + "/" + timeStamp));
            FileUtils.forceMkdir(new File(reportsPath + "/ExtentReport_" + currentDate));
            FileUtils.forceMkdir(new File(reportsPath + "/ExtentReport_" + currentDate + "/" + timeStamp));
            FileUtils.forceMkdir(new File(downloadFolder + "/" + currentDate));
            FileUtils.forceMkdir(new File(downloadFolderWithTimestamp));

            // Initialize ExtentReports and configure reporters
            extent = new ExtentReports();

            ExtentSparkReporter dynamicPathReporter = new ExtentSparkReporter(
                    reportsPath + "/ExtentReport_" + currentDate + "/" + timeStamp + "/ExtentReport" + timeStamp + ".html"
            );
            dynamicPathReporter.loadXMLConfig(new File(testOutputPath + "/ExtentReport.xml"));
            dynamicPathReporter.config().setCss(".badge-primary{background-color:#08111c; font-size: 13px;}");

            ExtentSparkReporter recentRunReporter = new ExtentSparkReporter(
                    reportsPath + "/recentRunReport/ExtentReport_" + timeStamp + ".html"
            );
            recentRunReporter.loadXMLConfig(new File(testOutputPath + "/ExtentReport.xml"));
            recentRunReporter.config().setCss(".badge-primary{background-color:#08111c; font-size: 13px;}");

            extent.attachReporter(dynamicPathReporter, recentRunReporter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


	public void addPackageTable(List<String> packageNames) {
		String tableHtml = "<table>" +
				"<tr><th>Package Name</th></tr>";
		for (String packageName : packageNames) {
			tableHtml += "<tr><td>" + packageName + "</td></tr>";
		}
		tableHtml += "</table>";
		getLogger().info(tableHtml);
	}

	@Parameters({"browser","machine"})
	@BeforeMethod(alwaysRun = true)
	public void beforeMethod(Method method, String browser, String machine) throws Exception {
		String testName = method.getName();
		Test testAnnotation = method.getAnnotation(Test.class);
		String testDescription = (testAnnotation != null && !testAnnotation.description().isEmpty())
				? testAnnotation.description()
						: "No Description Provided";
		String formattedTestName = "<b>" + testName + "()" + "</b>";
		//test = extent.createTest(testDescription, formattedTestName);
		 ExtentTest test = extent.createTest(testDescription,formattedTestName);
	        testThread.set(test);
		if (testAnnotation != null) {
			String[] groups = testAnnotation.groups();
			if (groups.length > 0) {
				test.assignCategory(groups);
			}
		}
		System.out.println("\n---------------------------------****************************------------------------------------");
		log4j.info("Executing testcase: " + testName + "()");
		log4j.info("Testcase description: " + testDescription);
		browsername = browser;


		// Define the file download location
		File downloadDir = new File(downloadFolderWithTimestamp);

		// Ensure the directory exists
		if (!downloadDir.exists()) {
			if (downloadDir.mkdirs()) {
				log4j.info("Download directory created: " + downloadDir.getAbsolutePath());
			} else {
				log4j.fail("Failed to create download directory: " + downloadDir.getAbsolutePath());
				throw new RuntimeException("Could not create download directory.");
			}
		}

		try {
			if (DriverType.Firefox.toString().equals(browsername)) {
				log4j.info("Opening Firefox browser");
				getLogger().info("Opening Firefox browser");
				// WebDriverManager.firefoxdriver().setup();
				WebDriverManager.firefoxdriver().driverVersion("0.33.0").setup();


				FirefoxOptions firefoxOptions = new FirefoxOptions();
				// Uncomment if running in headless mode or in Docker
				firefoxOptions.addArguments("--headless");
				firefoxOptions.addArguments("--no-sandbox");
				firefoxOptions.addArguments("--disable-dev-shm-usage");

				setDriver(new FirefoxDriver(firefoxOptions));

			} else if (DriverType.Edge.toString().equals(browsername)) {
				log4j.info("Opening Microsoft Edge browser");
				getLogger().info("Opening Microsoft Edge browser");
				// WebDriverManager.edgedriver().setup();
				WebDriverManager.firefoxdriver().driverVersion("116").setup();

				EdgeOptions edgeOptions = new EdgeOptions();
				// Uncomment if running in headless mode or in Docker
				edgeOptions.addArguments("--headless");
				edgeOptions.addArguments("--no-sandbox");
				edgeOptions.addArguments("--disable-dev-shm-usage");
				edgeOptions.addArguments("--window-size=1920,1080");  // Set resolution

				setDriver(new EdgeDriver(edgeOptions));

			} else if (DriverType.Safari.toString().equals(browsername)) {
				log4j.info("Opening Safari browser");
				getLogger().info("Opening Safari browser");
				// SafariDriver setup does not require WebDriverManager as it is already included in macOS
				SafariOptions safariOptions = new SafariOptions();
				safariOptions.setAutomaticInspection(true);
				safariOptions.setAutomaticProfiling(true);

				setDriver(new SafariDriver(safariOptions));


			} else {
				log4j.info("Opening Chrome browser");
				getLogger().info("Opening Chrome browser");
				if(machine.equalsIgnoreCase("CLOUDJENKINS")) {
					WebDriverManager.chromedriver().driverVersion("129").setup();
				}
				WebDriverManager.chromedriver().driverVersion("").setup();
				HashMap<String, Object> chromePrefs = new HashMap<>();
				chromePrefs.put("download.default_directory", downloadFolderWithTimestamp);
				chromePrefs.put("download.prompt_for_download", false);
				chromePrefs.put("download.directory_upgrade", true);
				chromePrefs.put("profile.safebrowsing.enabled", false);
				chromePrefs.put("safebrowsing.enabled", true);
				chromePrefs.put("safebrowsing_for_trusted_sources_enabled", true);
				chromePrefs.put("profile.default_content_settings.popups", 0);

				ChromeOptions options = new ChromeOptions();
				options.setExperimentalOption("prefs", chromePrefs);
				options.addArguments("--disable-infobars");
				options.addArguments("--disable-popup-blocking");
				options.addArguments("--disable-notifications");
				options.addArguments("--disable-extensions");
				options.addArguments("--disable-features=PasswordManagerOnLogin");
				options.addArguments("--disable-dev-shm-usage");
				options.addArguments("--no-sandbox");
				options.addArguments("--start-maximized");
				//options.addArguments("--window-size=1920,1080");
				options.addArguments("--disable-gpu");
				options.setUnhandledPromptBehaviour(UnexpectedAlertBehaviour.ACCEPT);

				
				if(machine.equalsIgnoreCase("CLOUDJENKINS")) {
					options.addArguments("--headless=new");
				}
				else {
						//options.addArguments("--headless=new");
				}
				chromePrefs.put("credentials_enable_service", false);         
				chromePrefs.put("profile.password_manager_enabled", false);       
				chromePrefs.put("profile.default_content_setting_values.notifications", 2);  // Disable notifications        
				chromePrefs.put("profile.default_content_setting_values.popups", 0); // Block popups

				setDriver(new ChromeDriver(options));
			}

		} catch (Exception e) {
			log4j.fail("Error initializing browser: " + e.getMessage());
			getLogger().fail("Error initializing browser: " + e.getMessage());
			String screenshotPath = captureScreenshot("BrowserInitializationFailure", machine);
			getLogger().addScreenCaptureFromPath(screenshotPath);
			throw e;
		}
	}

	@Parameters({"machine"})
	@AfterMethod
	public void afterMethod(ITestResult result, String machine) throws Exception {
	    ExtentTest test = getLogger(); // Use getLogger() instead of testThread.get()

	    if (test != null) { // Ensure test instance is not null
	        if (result.getStatus() == ITestResult.FAILURE) { 
	            String screenshotPath = captureScreenshot(result.getName(), machine);
	            getLogger().addScreenCaptureFromPath(screenshotPath);
	            getLogger().fail("Testcase Failed...");
	            log4j.fail("Testcase Failed...");
	        } 
//	        else if (result.getStatus() == ITestResult.SUCCESS) {
//	            getLogger().pass("Test Passed...");
//	        } 
	        
	        else if (result.getStatus() == ITestResult.SUCCESS) {
				getLogger().pass("Test Passed...");
				log4j.pass("Testcase Passed...");
	        }
	        
	        else if (result.getStatus() == ITestResult.SKIP) {
	            getLogger().skip("Test Skipped: " + result.getThrowable());
	        }
	    } else {
	        log4j.info("ExtentTest instance was null for test: " + result.getName());
	    }

	    // Quit and remove WebDriver instance
	    WebDriver currentDriver = driver.get();
	    if (currentDriver != null) {
	        currentDriver.quit();
	        driver.remove(); // Ensure the thread-local WebDriver instance is removed
	    }

	    testThread.remove(); // Ensure thread-local storage for ExtentTest is cleared
	}



	@AfterSuite
	public void afterSuite() throws InterruptedException {

		// Generate Extent Report
		log4j.info("Generating Extent Report");
		extent.flush();
	}

	public String getPath() {
		String path = System.getProperty("user.dir").replaceAll("\\\\+", "/");
		return path;
	}

	public String captureScreenshot(String result, String machine) throws Exception {
		String actualPath = "";
		DateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
		Date date = new Date();
		String formattedDate = dateFormat.format(date);
		String localPath = getPath() + "/screenshots/" + currentDate + "/" + timeStamp + "/" + result + "_" + formattedDate + ".png";
		log4j.info("Screenshot will be saved at: " + localPath);

		try {
			switch (machine.toUpperCase()) {
			case "LOCALSYSTEM":
				actualPath = localPath;
				break;

			case "LOCALJENKINS":
				actualPath = "http://localhost:8080/job/Syntasa_Auto_Test/ws/screenshots/" + currentDate + "/" + timeStamp + "/" + result + "_" + formattedDate + ".png";
				break;
			case "CLOUDJENKINS":
				actualPath = "https://jenkins-qa.syntasa.net/job/SyntasaAutomation/ws/screenshots/" + currentDate + "/" + timeStamp + "/" + result + "_" + formattedDate + ".png";
				break;	
			default:
				throw new IllegalArgumentException("Invalid environment: " + machine);
			}

			// Create directories if they don't exist
			File localDir = new File(localPath).getParentFile();
			if (!localDir.exists()) {
				localDir.mkdirs();
			}
			WebDriver actualDriver = driver.get(); // Retrieve WebDriver from ThreadLocal
		    File srcFile = ((TakesScreenshot) actualDriver).getScreenshotAs(OutputType.FILE);
		    
		    if (srcFile.exists()) {
		    	log4j.info("Screenshot successfully captured.");
		    } else {
		    	log4j.info("Screenshot capture failed.");
		    }			
			// Take screenshot
			//File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			File localDestFile = new File(localPath);
			Files.copy(srcFile.toPath(), localDestFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
			log4j.info("Screenshot saved at: " + localDestFile.getAbsolutePath());

			// Saving screenshot in the recentRunReport folder
			String recentRunLocation = getPath() + "/reports/recentRunReport/" + result + "_" + timeStamp + ".png";
			File recentRunFile = new File(recentRunLocation);
			FileUtils.copyFile(srcFile, recentRunFile);
			log4j.info("Recent Run Screenshot Path: " + recentRunLocation);

		} catch (Exception e) {
			log4j.info("Error taking screenshot: " + e.getMessage());
			e.printStackTrace();
		}

		return actualPath;
	}


	public void openURL(String url)
	{
		if (url == null) {
			throw new IllegalArgumentException("URL cannot be null");
		}
		else {
			try {
				log4j.info("Navigating to URL: "+url);
				getDriver().get(url);
				log4j.pass("Successfully navigated to the URL: " + url);
				getLogger().log(Status.PASS, "Successfully navigated to the URL: " + url);
			} catch (Exception e) {
				log4j.fail("Failed to navigate to the URL: " + url);
				getLogger().log(Status.FAIL, "Failed to navigate to the URL: " + url);
				throw new RuntimeException("Navigation to URL failed", e);
			}
		}

		log4j.info("Maximizing the browser window");
		//driver.manage().window().setSize(new Dimension(1500, 900));
		getDriver().manage().window().maximize();
	}

	public void loginToPlatform() throws InterruptedException {
		initializeLocators(getDriver());	
		String applicationUrl = properties.webUrl;
		String userName = properties.userName;
		String password = properties.password;	
		propertyfilewriter.setProperty("userName", userName);
		openURL(applicationUrl);
		commonHelper.verifyElementPresent(loginPageLocators.platformHeaderTitle, "platformHeaderTitle");
		//Thread.sleep(3000);
		commonHelper.click(loginPageLocators.loginHeaderBtn,"loginHeaderBtn","Clicking 'Login' header button on page");
		//Thread.sleep(3000);
		commonHelper.verifyElementPresent(loginPageLocators.loginPage, "loginPage");
		// Convert the password into star code
		String starCodedPassword = toStarCode(password);
		commonHelper.isElementClickable(getDriver(), loginPageLocators.usernameInput, "usernameInput");

		commonHelper.enterText(loginPageLocators.usernameInput, "usernameInput", userName, "Entering the text: '"+userName+"' in 'Username' field");
		log4j.info("Inserting Password: " + starCodedPassword);
		commonHelper.isElementClickable(getDriver(), loginPageLocators.passwordInput, "passwordInput");
		loginPageLocators.setPassword(password);
		//Thread.sleep(2000);
		commonHelper.isElementClickable(getDriver(), loginPageLocators.loginButton, "loginButton");
		//Thread.sleep(2000);
		commonHelper.click(loginPageLocators.loginButton,"loginButton","Clicking 'Login' button");
		commonHelper.verifyElementPresent(loginPageLocators.logOutButton, "logOutButton");
		log4j.pass("Successfully logged in to the platform.");
		getLogger().pass("Successfully logged in to the platform.");

	}
	
	public void logoutFromPlatform() throws InterruptedException {
		commonHelper.verifyElementPresent(loginPageLocators.logOutButton, "logOutButton");
		commonHelper.click(loginPageLocators.logOutButton, "logOutButton", "Clicking on 'log out' button");	
		commonHelper.verifyElementPresent(loginPageLocators.loginButtonAfterLogout, "loginButtonAfterLogout");
		log4j.pass("Successfully logged in out from the platform.");
		getLogger().pass("Successfully logged out from the platform.");
	}

	// Method to convert a string into star code
	public String toStarCode(String input) {
		StringBuilder starCode = new StringBuilder();
		for (char c : input.toCharArray()) {
			starCode.append("*"); // Each character is replaced by 1 asterisk
		}
		return starCode.toString();
	}


	public String generateID() {
		//log4j.info("Generating current Timestamp");
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		Date date = new Date();
		String id = dateFormat.format(date);
		return id;
	}

	public static String timeStamp() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd_HHmmss");
		Date date = new Date();
		String id = dateFormat.format(date);
		return id;
	}

	public static String currentDate() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd");
		Date date = new Date();
		String id = dateFormat.format(date);
		return id;
	}
}