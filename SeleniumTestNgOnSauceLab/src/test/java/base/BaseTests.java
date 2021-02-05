package base;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.google.common.io.Files;

import pages.GooglePage;

public class BaseTests {

	public WebDriver driver;

	private static ExtentHtmlReporter htmlReport;
	private static ExtentReports extent;
	protected static GooglePage googlePage;
	public static final String USERNAME = "abdessalem01";
	public static final String ACCESS_KEY = "f77f3a58-a56b-4067-be26-da4c2e08b23a";
	public static final String URL = "https://" + USERNAME + ":" + ACCESS_KEY
			+ "@ondemand.eu-central-1.saucelabs.com:443/wd/hub";
//	public static final String URL = "https://abdessalem01:f77f3a58-a56b-4067-be26-da4c2e08b23a@ondemand.eu-central-1.saucelabs.com:443/wd/hub";

	public static ExtentReports getInstance() {

		String reportName = getReporttName();
		String directory = System.getProperty("user.dir") + "/reports/";
		new File(directory).mkdirs();
		String path = directory + reportName;
		htmlReport = new ExtentHtmlReporter(path);
		htmlReport.config().setEncoding("utf-8");
		htmlReport.config().setDocumentTitle("Automation Test");
		htmlReport.config().setTheme(Theme.STANDARD);

		extent = new ExtentReports();
		extent.setSystemInfo("organization", "abdou guesmi");
		extent.setSystemInfo("Browser", "Chrome");
		extent.setSystemInfo("organization", "abdou guesmi");
		extent.attachReporter(htmlReport);

		// WebDriverManager.chromedriver().setup();
		return extent;
	}

	public static String getReporttName() {
		Date d = new Date();
		String fileName = "Report_" + d.toString().replace(":", "-") + ".html";
		return fileName;

	}

	@BeforeMethod(alwaysRun = true)
	public void setUp(ITestResult iTestResult) throws MalformedURLException {
		/*
		 * MutableCapabilities mutableCapabilities = new MutableCapabilities();
		 * mutableCapabilities.setCapability("seleniumVersion", "3.6.0");
		 * mutableCapabilities.setCapability("username", "abdessalem");
		 * mutableCapabilities.setCapability("tage", "google search");
		 */
		DesiredCapabilities caps = DesiredCapabilities.chrome();
		// caps.setCapability("sauce:options", mutableCapabilities);
		caps.setCapability("platform", "Windows 7");
		caps.setCapability("version", "latest");
		caps.setCapability("idleTimeout", 320);
		caps.setCapability("extendedDebugging", "true");
		caps.setCapability("build", "build-3.0");
		caps.setCapability("name", "Google_" + iTestResult.getMethod().getMethodName());
		driver = new RemoteWebDriver(new URL(URL), caps);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.navigate().to("https://www.google.com/");
		googlePage = new GooglePage(driver);

	}

	@AfterMethod
	public void recordFailure(ITestResult result) {
		if (ITestResult.FAILURE == result.getStatus()) {
			TakesScreenshot camera = (TakesScreenshot) driver;
			File screenshot = camera.getScreenshotAs(OutputType.FILE);
			try {
				Files.move(screenshot, new File("screenshot/" + result.getName() + ".png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		driver.close();
	}

	@AfterClass
	public void tearDown() {
		driver.quit();
	}
}
