package com.bettervet.appointment.base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.Logger;

public class BrowserOptions extends HelperMethods{
	
	protected WebDriver driver; //to make it available for other class that extends this one
//	private ThreadLocal<WebDriver> driver = new ThreadLocal<WebDriver>();
	private String browser;
	private Logger log; //variable to control and print the logs

	public BrowserOptions(String browser, Logger log) { //constructor
		this.browser = browser.toLowerCase();
		this.log = log;
	}
	
	public WebDriver createDriver() {
		// Create driver
		log.info("Create driver: " + browser);
		
		switch (browser){
		case "chrome":
			System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver");
			driver = new ChromeDriver();
			break;

		case "firefox":
			System.setProperty("webdriver.gecko.driver", "src/main/resources/geckodriver");
			driver = new FirefoxDriver();
			break;
			
		case "edge":
			System.setProperty("webdriver.edge.driver", "src/main/resources/edgedriver");
			driver = new EdgeDriver();
			break;

		default:
			System.out.println("Do not know how to start " + browser + ", starting chrome instead");
			System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver");
			driver = new ChromeDriver();
			break;
		}
		
		return driver;
	}
	
	public WebDriver createChromeWithMobileEmulation(String deviceName) {
		log.info("Starting driver with " + deviceName + " emulation]");
		Map<String, String> mobileEmulation = new HashMap<>();
		mobileEmulation.put("deviceName", deviceName);
		ChromeOptions chromeOptions = new ChromeOptions();
		chromeOptions.setExperimentalOption("mobileEmulation", mobileEmulation);

		System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver");
		driver = new ChromeDriver(chromeOptions);
		return driver;
	}
}
