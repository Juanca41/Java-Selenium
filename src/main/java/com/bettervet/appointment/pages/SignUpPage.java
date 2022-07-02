package com.bettervet.appointment.pages;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class SignUpPage extends BaseObject{
	
	By sign_up_title = By.xpath("//div[@class='enterZip__title']");
	By login_link = By.xpath("//a//span[text()='Log in']");

	public SignUpPage(WebDriver driver, Logger log) {
		
		super(driver, log);
	}
	
	public void TextAssertions() {
		assert_text(sign_up_title, "Experience better vet care from home");
	}
	
	public void GoToLoginPage() {
		click(login_link);
	}

}
