/*
  Class to initialize all application page objects and manage WebDriver browser
  object. Each and every test script class must extend this. This class does
  not use any of the Selenium APIs directly, and adds support to make this
  framework tool independent.
 */
package net.marklogic.selenium.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import net.marklogic.enums.DriverType;
import net.marklogic.pages.IndexPage;
import net.marklogic.pages.QueryConsolePage;
import net.marklogic.pages.ResultPage;
import net.marklogic.pages.SearchResultPage;
import net.marklogic.report.ScreenRecorder;
import net.marklogic.utilities.ApplicationVerificationTitles;
import net.marklogic.utilities.UserClaims;
import net.marklogic.utilities.Utilities;

public abstract class BaseTest extends ScreenRecorder implements UserClaims, ApplicationVerificationTitles {

	public static final Logger logger = LoggerFactory.getLogger(BaseTest.class);
	private static final String BREAK_LINE = "\n";
	public static ExtentTest test;
	public static ExtentReports extent;
	private static WebDriver driver;
	
	// pages object initialization
	protected IndexPage indexPage;
	protected ResultPage resultPage;
	protected SearchResultPage searchPage;
	protected QueryConsolePage qConsolePage;


	private static String setEnvironment, quitBrowser;

	protected String browserType,color,searchTerm,searchFalseTerm,query1,query2;
	protected static Properties RegressionTestData;
	public static String resultPath;
	public static Document document = null;
	static Image img;

	private static Map<Integer, ExtentTest> extentTestMap;
	

	@BeforeSuite
	public void before() throws Exception {

		// Create Result repository for report.
		String timeStamp = getFormattedTimeStamp().replace("-", "").replace(":", "").replace(".", "");
		String path = Utilities.getPath();
		resultPath = path + "/Result/Suite_" + timeStamp;

		File ExtentReportsource = new File(path + "/Result/");

		if (ExtentReportsource.exists()) {

			try {
				FileUtils.deleteDirectory(ExtentReportsource);
			} catch (Exception e) {

			}

		}
		if (!(ExtentReportsource.exists())) {
			ExtentReportsource.mkdir();
		}

		new File(resultPath).mkdirs();
		extent = new ExtentReports(resultPath + "/CustomReport.html", true);
		// machineForRun = Configuration.readApplicationFile("RunOnLocalMachine");
		quitBrowser = Configuration.readApplicationFile("closeAndQuitBrowser");

		extentTestMap = new HashMap<Integer, ExtentTest>();

		if (System.getProperty("browser") != null) {
			browserType = System.getProperty("browser");
		}


	}

	@SuppressWarnings({ "static-access", "deprecation", "unused" })
	@BeforeMethod
	@Parameters({"browser","url"})
	public void setUp(Method method, @Optional("chrome") String browserParam, String urls) throws Exception {

		if (browserParam != null) {
			browserType = browserParam;
		}

		if (browserType == null) {
			browserType = Configuration.readApplicationFile("Chrome");
		}

		@SuppressWarnings("rawtypes")
		Class className = this.getClass();

		if (DriverType.Firefox.toString().toLowerCase().equals(browserType.toLowerCase())) {
			System.setProperty("webdriver.gecko.driver",
					Utilities.getPath() + "//src//test//resources//webdriver/geckodriver.exe");
			FirefoxOptions firefoxOptions = new FirefoxOptions();
			firefoxOptions.setCapability("marionette", true);
			driver = new FirefoxDriver(firefoxOptions);
		} else if (DriverType.IE.toString().toLowerCase().equals(browserType.toLowerCase())) {
			System.setProperty("webdriver.ie.driver",
					Utilities.getPath() + "//src//test//resources//webdriver/IEDriverServer.exe");
			InternetExplorerOptions ieOptions = new InternetExplorerOptions();
			ieOptions.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
			ieOptions.setCapability("nativeEvents", false);
			driver = new InternetExplorerDriver(ieOptions);
		} else if (DriverType.Chrome.toString().toLowerCase().equals(browserType.toLowerCase())) {
			System.setProperty("webdriver.chrome.driver", ".//src//test//resources//webdriver/chromedriver.exe");

			ChromeOptions options = new ChromeOptions();
			options.addArguments("--disable-extensions");
			options.addArguments("disable-infobars");
			driver = new ChromeDriver(options);

		} else {
			throw new Exception("Please pass a valid browser type value");
		}

		/**
		 * Maximize window
		 */
		driver.manage().window().maximize();

		/**
		 * Delete cookies and set timeout
		 */
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		/**
		 * Open application URL
		 */

		getWebDriver().navigate().to(urls);

		/**
		 * Initialize the reporting object
		 */
		setReportTest(method, browserType);
		indexPage = PageFactory.initElements(getWebDriver(), IndexPage.class);
		searchPage = PageFactory.initElements(getWebDriver(), SearchResultPage.class);
		qConsolePage=PageFactory.initElements(getWebDriver(), QueryConsolePage.class);
	

		/**
		 * Set property file
		 */
		RegressionTestData = Configuration.readTestData("RegressionTestData");

		reportLog("Initialize Browser - " + browserType + " ");
		reportLog("Invoke target Environment - " + setEnvironment + " ");

	}

	@AfterMethod
	public void afterMainMethod(ITestResult result) throws IOException, InterruptedException {

		if (result.getStatus() == ITestResult.FAILURE) {
			try {
				test.log(LogStatus.FAIL, "Failed test step is: " + result.getName() + " => " + getMessage());
				test.log(LogStatus.FAIL, result.getThrowable());
				captureScreenshot(result);
			} catch (Exception e) {
				e.printStackTrace();
			}

		} else if (!(result.getStatus() == (ITestResult.SUCCESS) || result.getStatus() == (ITestResult.FAILURE))) {
			try {
				test.log(LogStatus.SKIP, "Skipped Test Case is: " + result.getName() + " => " + getMessage());
				captureScreenshot(result);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		extent.endTest(test);

		if (quitBrowser.equals("true")) {
			driver.quit();
		}

	}

	public static synchronized ExtentTest getTest() {
		return (ExtentTest) extentTestMap.get((int) (long) (Thread.currentThread().getId()));
	}

	public static synchronized ExtentTest startTest(String testName, String desc) {
		ExtentTest test = extent.startTest(testName, desc);
		extentTestMap.put((int) (long) (Thread.currentThread().getId()), test);
		return test;
	}

	public void setReportTest(Method method, String browserType) {

		System.setProperty("testcaseName", method.getName().toString());

		test = startTest(browserType.toUpperCase() + " - " + method.getName(), this.getClass().getName());
		test.assignCategory(browserType.toUpperCase());

	}

	@AfterSuite /* (alwaysRun = true) */
	public void tearDownSuite() throws IOException {

		extent.flush();

	}

	public WebDriver getWebDriver() {
		return driver;
	}

	/**
	 * Get All windows open in UI
	 *
	 * @return: Parent window name
	 * @throws InterruptedException
	 */
	public List<String> getAllWindowsOpenInUI() throws InterruptedException {
		Thread.sleep(2000);
		List<String> WinList = new ArrayList<>();
		Set<String> windows = getWebDriver().getWindowHandles();
		Iterator<String> iterator = windows.iterator();
		for (int i = 0; i <= windows.size() - 1; i++) {
			String win = iterator.next();
			WinList.add(win);
		}
		return WinList;
	}

	/**
	 * Handle child windows
	 *
	 * @return: Parent window name
	 * @throws InterruptedException
	 */
	public String switchToChildWindow() throws InterruptedException {
		Thread.sleep(2000);
		Set<String> windows = getWebDriver().getWindowHandles();
		// System.out.println(windows.size());
		Iterator<String> iterator = windows.iterator();
		String parent = iterator.next();
		String child = iterator.next();
		getWebDriver().switchTo().window(child);
		return parent;
	}

	/** close child window */
	public void switchParentWindowByClosingChild(String Win) {
		driver.close();
		getWebDriver().switchTo().window(Win);
	}

	/* Close Driver */
	public void closeChildWindow() {
		driver.close();
	}

	/**
	 * Switch To window
	 * 
	 * @throws InterruptedException
	 */
	public void switchToWindow(String Win) throws InterruptedException {
		Thread.sleep(2000);
		getWebDriver().switchTo().window(Win);
	}

	/**
	 * Get absolute path
	 *
	 * @return: Absolute path
	 */
	public String getPathUpload() {
		String path;
		File file = new File("");
		String absolutePathOfFirstFile = file.getAbsolutePath();
		path = absolutePathOfFirstFile.replaceAll("/", "//");
		return path;
	}

	/**
	 * Capturing screenshot once script is failed
	 * 
	 * @return
	 * @throws IOException
	 * @throws MalformedURLException
	 * @throws BadElementException
	 */
	public Image captureScreenshot(ITestResult result) throws BadElementException, MalformedURLException, IOException {
		String fileName = System.getProperty("className");
		String screen = "";
		try {
			String screenshotName = Utilities.getFileName(fileName);
			File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			String path = resultPath + "/" + fileName;
			new File(path).mkdirs();
			screen = path + "/" + "Failed_" + screenshotName + "_" + browserType + ".png";
			File screenshotLocation = new File(screen);
			FileUtils.copyFile(screenshot, screenshotLocation);
			Thread.sleep(1500);
			InputStream is = new FileInputStream(screenshotLocation);
			byte[] imageBytes = IOUtils.toByteArray(is);
			Thread.sleep(2000);
			String base64 = Base64.getEncoder().encodeToString(imageBytes);

			if (result.getStatus() == ITestResult.FAILURE) {
				test.log(LogStatus.FAIL, "Failed_" + fileName + " \n Snapshot below: "
						+ test.addBase64ScreenShot("data:image/png;base64," + base64));
				test.addScreenCapture(screen);
			}
			if (result.getStatus() == ITestResult.SKIP) {
				test.log(LogStatus.SKIP, "Skipped_" + fileName + " \n Snapshot below: "
						+ test.addBase64ScreenShot("data:image/png;base64," + base64));
				test.addScreenCapture(screen);
			}
			if (!(result.getStatus() == (ITestResult.SUCCESS) || result.getStatus() == (ITestResult.FAILURE))) {
				test.log(LogStatus.SKIP, "Skipped_" + fileName + " \n Snapshot below: "
						+ test.addBase64ScreenShot("data:image/png;base64," + base64));
				test.addScreenCapture(screen);
			}
		} catch (Exception e) {
			System.out.println(e.getStackTrace());
		}
		return Image.getInstance(screen);
	}



	/**
	 * Report logs
	 *
	 * @param :
	 *            message
	 * @throws DocumentException
	 * @throws IOException
	 * @throws MalformedURLException
	 * @throws BadElementException
	 */
	public void reportLog(String msg) {
		message = msg;

		getTest().log(LogStatus.PASS, message);

		message = BREAK_LINE + message;

		logger.info(" || Browser: " + browserType + " || Message: " + message + BREAK_LINE);
		Reporter.log(message);
	}

	static String message = "";

	public static String getMessage() {
		return message;
	}

	/**
	 * function : Fetch the System's current date with time
	 * 
	 */
	public String getTimeStamp() {
		Date date = new Date();
		return new Timestamp(date.getTime()).toString().replace(" ", "");
	}

	/**
	 * @return
	 * @function: Get formatted Time stamp
	 * 
	 */
	public String getFormattedTimeStamp() {

		LocalDateTime currentTime = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d-MMM-uuuu HH:mm:ss");
		String formatDateTime = currentTime.format(formatter);
		return formatDateTime;

	}

	/**
	 * Function: Get current date.
	 * 
	 * @return
	 */
	public String currentDate() {
		final DateFormat format = new SimpleDateFormat("dd-MMM-YYYY");
		Date date = new Date();
		final String currentDate = format.format(date);
		return currentDate;
	}





}