package core;

import java.io.Writer;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Common {
	
	static WebDriver driver;
    static Writer report;
    static Properties p = new Properties();
	static String url;
	static String browser;

	static void getWebDriver(String browser) {

		Logger.getLogger("").setLevel(Level.OFF);
		String driverPath = "";	
		
		if (browser.equalsIgnoreCase("firefox")) {
		if (System.getProperty("os.name").toUpperCase().contains("MAC"))
			driverPath = "./geckodriver";
		else if (System.getProperty("os.name").toUpperCase().contains("WINDOWS"))
			driverPath = "./geckodriver.exe";
		else throw new IllegalArgumentException("Unknown OS");

		System.setProperty("webdriver.gecko.driver", driverPath);
		driver = new FirefoxDriver(); 
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		}
		
		else if (browser.equalsIgnoreCase("chrome")) {
			if (System.getProperty("os.name").toUpperCase().contains("Mac OS X"))
			driverPath = "chromedriver";
			else if (System.getProperty("os.name").toUpperCase().contains("WINDOWS"))
			driverPath = "chromedriver.exe";
			else throw new IllegalArgumentException("Unknown OS");
			System.setProperty("webdriver.chrome.driver", driverPath);
			System.setProperty("webdriver.chrome.silentOutput", "true");
			ChromeOptions option = new ChromeOptions();
			option.addArguments("disable-infobars");
			option.addArguments("--disable-notifications");
			if (System.getProperty("os.name").toUpperCase().contains("Mac OS X"))
				option.addArguments("-start-fullscreen");
			else if (System.getProperty("os.name").toUpperCase().contains("WINDOWS"))
				option.addArguments("--start-maximized");
			else throw new IllegalArgumentException("Unknown OS");
			driver = new ChromeDriver(option);
			driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		}
		else if (browser.equalsIgnoreCase("safari")) {
			if (!System.getProperty("os.name").contains("Mac OS X")) {
				throw new IllegalArgumentException("Safari is available only on Mac");
			}
			driver = new SafariDriver();
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		}
	}
	
	static void open(String browser, String url){
		getWebDriver(browser);
		driver.get(url);}
	
	static void stopDriver() {
		driver.quit();}
	
	static void leftClickOnElement(By by) {
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		if (!driver.findElements(by).isEmpty() && driver.findElement(by).isDisplayed())
			driver.findElement(by).click();}
	
    public static void highlightElement(WebElement element) throws Exception{
    	((RemoteWebDriver) driver).executeScript("arguments[0].setAttribute('style','border: solid 3px red');", element);
        Thread.sleep(100);}
    public static void unhighlightElement(WebElement element) throws Exception{
    	Thread.sleep(100);
        ((RemoteWebDriver) driver).executeScript("arguments[0].setAttribute('style','border: solid 0px red');", element);}

	static boolean isElementPresent(By by) throws Exception {
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		if (!driver.findElements(by).isEmpty()) {
			highlightElement(driver.findElement(by));
			unhighlightElement(driver.findElement(by));
			return true;}
		else return false;}
	
	static void setValue(By by, String value) {
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		if (!driver.findElements(by).isEmpty() && driver.findElement(by).isDisplayed())
			driver.findElement(by).sendKeys(value);
	}
	
	static void submit(By by) {
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		if (!driver.findElements(by).isEmpty() && driver.findElement(by).isDisplayed())
			driver.findElement(by).submit();}
	
	static String getValue(By by) {
		WebDriverWait wait = new WebDriverWait(driver, 15);
		driver.manage();
		wait.until(ExpectedConditions.presenceOfElementLocated(by));
		if (!driver.findElements(by).isEmpty() && driver.findElement(by).isDisplayed()
				&& driver.findElement(by).getTagName().equalsIgnoreCase("input"))
			return driver.findElement(by).getAttribute("value").toString().trim();

		else if (!driver.findElements(by).isEmpty() && driver.findElement(by).isDisplayed()
				&& driver.findElement(by).getTagName().equalsIgnoreCase("span"))
			return driver.findElement(by).getText().trim();
		else
			return "null";}
	
	static String getNameOfTitle() {
		return driver.getTitle().toString().trim();
	}
	
}