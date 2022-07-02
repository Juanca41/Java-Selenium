package com.bettervet.appointment.base;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import java.lang.reflect.Method;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BaseMethods{
		
	protected WebDriver driver;
	protected Logger log; //variable to control and print the logs
	protected String testSuiteName;
	protected String testName;
	protected String testMethodName; 
	
	@Parameters({ "browser", "deviceName"})
	@BeforeMethod(alwaysRun = true)
	public void setUp(Method method, @Optional("chrome") String browser, @Optional String deviceName, ITestContext ctx) {
		String testName = ctx.getCurrentXmlTest().getName(); //to get the name of the test suite
		log = LogManager.getLogger(testName);
		
		BrowserOptions browser_options = new BrowserOptions(browser, log); //the class Browser is instantiated to use its methods (createDriver and the constructor)
		
		if (deviceName != null) {
			driver = browser_options.createChromeWithMobileEmulation(deviceName);
		} else {
			driver = browser_options.createDriver();
		}
//		System.setProperty("webdriver.chrome.driver","src/main/resources/chromedriver"); //to set the driver path
//		driver = new ChromeDriver(); //to create the driver handle variable
		driver.manage().window().maximize();
		
		this.testSuiteName = ctx.getSuite().getName();
		this.testName = testName;
		this.testMethodName = method.getName();
		
		String url = "https://admin:BetterVetisAwesome@app-qa.bettervet.com";
		driver.get(url);
		log.info("BetterVet QA page was open.");
	}
	
	@AfterMethod(alwaysRun = true)
	public void tearDown() {
//		log.info("Close driver");
		// Close browser
		driver.quit();
	}
}
