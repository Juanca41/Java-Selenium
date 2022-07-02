package com.bettervet.appointment.loginScreen;

import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import com.bettervet.appointment.base.HelperMethods;
import com.bettervet.appointment.base.CsvDataProviders;
import com.bettervet.appointment.pages.LoginPage;
import com.bettervet.appointment.pages.SignUpPage;

import java.util.Map;

public class LoginScreenTests extends HelperMethods{
	
	@Parameters({ "username", "password" })
	@Test( groups = {"regression"})
	public void positiveLogin(String username, String password) {
		
		LoginPage loginPage = new LoginPage(driver, log);
		SignUpPage signUpPage = new SignUpPage(driver, log);
		
		//Sign Up page
		signUpPage.TextAssertions();
		signUpPage.GoToLoginPage();
		
		//Login page
		loginPage.PositiveLoginProcess(username, password);
		
		takeScreenshot(testMethodName);
		driver.close();
	}
	
	
//	@Parameters({ "username", "password", "expected_error_msg" })
	@Test(dataProvider = "csvReader", dataProviderClass = CsvDataProviders.class)
	public void negativeLogin(Map<String, String> testData) {
		
//		String number = testData.get("n");
		String username = testData.get("username");
		String password = testData.get("password");
		String expected_error_msg = testData.get("errorMessage");
		
		LoginPage loginPage = new LoginPage(driver, log);
		SignUpPage signUpPage = new SignUpPage(driver, log);
		
		signUpPage.TextAssertions();
		signUpPage.GoToLoginPage();
		
		//Login page
//		loginPage.LoginProcess(username, password);
		
		loginPage.NegativeLoginProcess(username, password, expected_error_msg);
		
		takeScreenshot(testMethodName+": "+expected_error_msg);
		driver.close();
	}

}
