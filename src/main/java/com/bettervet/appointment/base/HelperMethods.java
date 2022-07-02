package com.bettervet.appointment.base;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.testng.annotations.DataProvider;

public class HelperMethods extends BaseMethods {
	
	public void sleep(int s) {
		try { //to stop the thread for a couple of seconds
			Thread.sleep(s);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@DataProvider
	public static Object[][] negativeLoginData(){
		return new Object[][]{
		
			{"juancarlosgularte","Helloworld10","Please enter a valid email address"},
			{"juancarlosgularte@bettervet.com", "123", "Your password must contain at least one number, a capital letter and a minimum of 6 characters"},
			{"juancarlosgularte@bettervet.com", "IncorrectPassword1", "User or password invalid."}
		};
	}
	
	/** Take screenshot */
	protected void takeScreenshot(String fileName) {
		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		String path = System.getProperty("user.dir")//main directory
				+ File.separator + "test-output" 
				+ File.separator + "screenshots"
				+ File.separator + getTodaysDate() 
				+ File.separator + testSuiteName 
				+ File.separator + testName
				+ File.separator + testMethodName 
				+ File.separator + getSystemTime() 
				+ " " + fileName + ".png";
		try {
			FileUtils.copyFile(scrFile, new File(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/** Todays date in yyyyMMdd format */
	protected static String getTodaysDate() {
		return (new SimpleDateFormat("yyyyMMdd").format(new Date()));
	}

	/** Current time in HHmmssSSS */
	protected String getSystemTime() {
		return (new SimpleDateFormat("HHmmssSSS").format(new Date()));
	}
	
	/** Get logs from browser console */
	protected List<LogEntry> getBrowserLogs() {
		LogEntries log = driver.manage().logs().get("browser");
		List<LogEntry> logList = log.getAll();
		return logList;
	}
}
