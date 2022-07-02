package com.bettervet.appointment.pages;

import java.time.Duration;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class BaseObject {
	
	protected WebDriver driver; //to make it available for other class that extends this one
	protected Logger log; //variable to control and print the logs
	
	public BaseObject(WebDriver driver, Logger log) {
		// TODO Auto-generated constructor stub
		this.driver = driver;
		this.log = log;
	}
	
	protected void openUrl(String url) {
		driver.get(url);
	}
	
	protected WebElement find(By locator) {
		return driver.findElement(locator);
	}
	
	protected List<WebElement> findAll(By locator) {
		return driver.findElements(locator);
	}
	
	protected void click(By locator) {
		waitForElementToBeClickable(locator);
		find(locator).click();
	}
	
	protected String text(By locator) {
		waitForElementToBeClickable(locator);
		String text = find(locator).getText();
		return text;
	}
	
	protected void type(By locator, String text) {
		waitForElementToBeVisible(locator);
		find(locator).sendKeys(text);
	}
	
	protected void assert_text(By locator, String text) {
		Assert.assertEquals(text(locator), text, "Text is not equal.\nText found: "+text(locator)+"\nText expected: "+text+"\n");
	}
	
	protected void assert_contains_text(By locator, String text) {
		Assert.assertEquals(text(locator).contains(text), "Text is not equal.\nText found: "+text(locator)+"\nText expected: "+text+"\n");
	}
	
	protected void assert_url(String text) {
		String url = driver.getCurrentUrl();
		Assert.assertEquals(url, text, "URL is not equal.\nCurrent url: "+url+"\nText expected: "+text+"\n");
	}
	
	protected void assert_element_is_displayed(By locator) {
		waitForElementToBeVisible(locator);
		WebElement element = find(locator);
		Assert.assertTrue(element.isDisplayed(), "The element is not displayed.");
	}
	
	private void wait_for(ExpectedCondition<WebElement> condition, Duration timeout) {
		
		timeout = timeout != null ? timeout : Duration.ofSeconds(15);
		WebDriverWait wait = new WebDriverWait(driver, timeout);
		wait.until(condition);
	}
	
	protected void waitForElementToBeVisible(By locator, Duration... timeout) {
		
		int attempts = 0;
		while(attempts < 2) {
			try {
				wait_for(ExpectedConditions.visibilityOfElementLocated(locator), (timeout.length > 0 ? timeout[0] : null));
			}catch(StaleElementReferenceException error) {
			}
		
			attempts ++;
		}
	}
		
	protected void waitForElementToBeClickable(By locator, Duration... timeout) {
		
		int attempts = 0;
		while(attempts < 2) {
			try {
				wait_for(ExpectedConditions.elementToBeClickable(locator), (timeout.length > 0 ? timeout[0] : null));
			}catch(StaleElementReferenceException error) {
			}
		
			attempts ++;
		}
	}
	
	/** Get title of current page */
	public String getCurrentPageTitle() {
		return driver.getTitle();
	}

	/** Get source of current page */
	public String getCurrentPageSource() {
		return driver.getPageSource();
	}
	
	/** Wait for alert present and then switch to it */
	protected Alert switchToAlert() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
		wait.until(ExpectedConditions.alertIsPresent());
		return driver.switchTo().alert();
	}
	
	public void switchToWindowWithTitle(String expectedTitle) {
		// Switching to new window
		String firstWindow = driver.getWindowHandle(); 

		Set<String> allWindows = driver.getWindowHandles();
		Iterator<String> windowsIterator = allWindows.iterator();

		while (windowsIterator.hasNext()) {
			String windowHandle = windowsIterator.next().toString();
			if (!windowHandle.equals(firstWindow)) {
				driver.switchTo().window(windowHandle);
				if (getCurrentPageTitle().equals(expectedTitle)) {
					break;
				}
			}
		}
	}
	
	/** Switch to iFrame using it's locator */
	protected void switchToFrame(By frameLocator) {
		driver.switchTo().frame(find(frameLocator));
	}
	
	/** Press Key on locator */
	protected void pressKey(By locator, Keys key) {
		find(locator).sendKeys(key);
	}

	/** Press Key using Actions class */
	public void pressKeyWithActions(Keys key) {
		log.info("Pressing " + key.name() + " using Actions class");
		Actions action = new Actions(driver);
		action.sendKeys(key).build().perform();
	}
	
	/** Perform scroll to the bottom */
	public void scrollToBottom() {
		log.info("Scrolling to the bottom of the page");
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		jsExecutor.executeScript("window.scrollTo(0, document.body.scrollHeight)");
	}
	
	public void scrollToElement() {
		log.info("Scrolling to an element");
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		jsExecutor.executeScript("arguments[0].scrollIntoView()");
	}
	
	/** Drag 'from' element to 'to' element */
	protected void performDragAndDrop(By from, By to) {
		// Actions action = new Actions(driver);
		// action.dragAndDrop(find(from), find(to)).build().perform();

		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		jsExecutor.executeScript(
				"function createEvent(typeOfEvent) {\n" + "var event =document.createEvent(\"CustomEvent\");\n"
						+ "event.initCustomEvent(typeOfEvent,true, true, null);\n" + "event.dataTransfer = {\n"
						+ "data: {},\n" + "setData: function (key, value) {\n" + "this.data[key] = value;\n" + "},\n"
						+ "getData: function (key) {\n" + "return this.data[key];\n" + "}\n" + "};\n"
						+ "return event;\n" + "}\n" + "\n" + "function dispatchEvent(element, event,transferData) {\n"
						+ "if (transferData !== undefined) {\n" + "event.dataTransfer = transferData;\n" + "}\n"
						+ "if (element.dispatchEvent) {\n" + "element.dispatchEvent(event);\n"
						+ "} else if (element.fireEvent) {\n" + "element.fireEvent(\"on\" + event.type, event);\n"
						+ "}\n" + "}\n" + "\n" + "function simulateHTML5DragAndDrop(element, destination) {\n"
						+ "var dragStartEvent =createEvent('dragstart');\n"
						+ "dispatchEvent(element, dragStartEvent);\n" + "var dropEvent = createEvent('drop');\n"
						+ "dispatchEvent(destination, dropEvent,dragStartEvent.dataTransfer);\n"
						+ "var dragEndEvent = createEvent('dragend');\n"
						+ "dispatchEvent(element, dragEndEvent,dropEvent.dataTransfer);\n" + "}\n" + "\n"
						+ "var source = arguments[0];\n" + "var destination = arguments[1];\n"
						+ "simulateHTML5DragAndDrop(source,destination);",
				find(from), find(to));
	}
	
	/** Perform mouse hover over element */
	protected void hoverOverElement(WebElement element) {
		Actions action = new Actions(driver);
		action.moveToElement(element).build().perform();
	}
		
}
