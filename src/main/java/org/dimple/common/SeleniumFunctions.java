package org.dimple.common;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SeleniumFunctions {

	WebDriver driver;
	Config config;
	WebDriverWait waitDriver;

	// constructor
	public SeleniumFunctions() throws Throwable {
		driver = WebDriverInit.getWebDriver();
		setWebDriverWait();
		config = new Config();
	}

	private void setWebDriverWait() {

		waitDriver = new WebDriverWait(driver, config.longWait);

	}

	public String getPageTitle() {
		return driver.getTitle();
	}

	public String getCurrentURL() {
		return driver.getCurrentUrl();
	}

	public WebElement getElement(String locator) throws Exception {
		WebElement ele = null;
		try {

			String arrLoc[] = locator.split(":==");
			if (arrLoc.length != 2) {
				throw new Exception("Locator is not defined correctly, use pattern like ID:==Some value");
			}

			switch (arrLoc[0].toUpperCase()) 
			{
				case "ID":
					ele = waitDriver.until(ExpectedConditions.visibilityOfElementLocated(By.id(arrLoc[1])));
					break;
				case "XPATH":
					ele = waitDriver.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(arrLoc[1])));
					break;
				case "CSS":
					ele = waitDriver.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(arrLoc[1])));
					break;
				case "NAME":
					ele = waitDriver.until(ExpectedConditions.visibilityOfElementLocated(By.name(arrLoc[1])));
					break;
				case "TAGNAME":
					ele = waitDriver.until(ExpectedConditions.visibilityOfElementLocated(By.tagName(arrLoc[1])));
					break;
				case "LINKTEXT":
					ele = waitDriver.until(ExpectedConditions.visibilityOfElementLocated(By.linkText(arrLoc[1])));
					break;
	
				case "PARTIAL_LINKTEXT":
					ele = waitDriver.until(ExpectedConditions.visibilityOfElementLocated(By.partialLinkText(arrLoc[1])));
					break;
	
				default:
					throw new Exception("Locator value is not correct :" + arrLoc[0]);
			}

		} catch (Throwable e) {
			e.printStackTrace();
		}
		return ele;
	}

	
	public void switchToFrame(WebElement ele) {
		driver.switchTo().frame(ele);
	}

	/**
	 * 
	 * @param locaotrs Example : ID:== some value , XPATH:== xpath value
	 * @throws Exception 
	 */
	public void switchToFrame(String locaotrs) throws Exception {
		
		WebElement ele = getElement(locaotrs);
		driver.switchTo().frame(ele);
	}

}
