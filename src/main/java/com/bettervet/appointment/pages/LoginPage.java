package com.bettervet.appointment.pages;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends BaseObject{
	

	private String login_page_url = "https://admin:BetterVetisAwesome@app-qa.bettervet.com/login";
	private By email_field = By.xpath("//input[@placeholder='Email']");
	private By pwd_field = By.xpath("//input[@placeholder='Password']");
	private By login_button = By.xpath("//button[text()='Sign In']");
	private By login_title = By.xpath("//span[@class='Login__title']");
	private By invalid_username_msg_found = By.xpath("//div[@class='inputErrorText']");
	private By loading_logo = By.cssSelector(".loadingIndicator__image");

	public LoginPage(WebDriver driver, Logger log) {
		
		super(driver, log);
	}
	
	public void LoginProcess(String username, String password) {
		TextAssertions();
		type(email_field, username);
		type(pwd_field, password);
		click(login_button);
	}
	
	public void NegativeLoginProcess(String username, String password, String errorText) {
		LoginProcess(username, password);
		assert_text(invalid_username_msg_found, errorText);
	}
	
	public void PositiveLoginProcess(String username, String password) {
		LoginProcess(username, password);
		assert_element_is_displayed(loading_logo);
	}
	
	public void TextAssertions() {
		assert_url(login_page_url);
		assert_text(login_title, "Sign in with Your Existing Account");
	}

}
