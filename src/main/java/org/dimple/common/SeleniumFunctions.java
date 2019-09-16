package org.dimple.common;

import java.util.List;

import javax.jws.WebResult;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SeleniumFunctions {

	WebDriver driver;
	Config  config = Config.getConfig();
	WebDriverWait waitDriver;

	// constructor
	public SeleniumFunctions() throws Throwable {
		driver = WebDriverInit.getWebDriver();
		setWebDriverWait();
	}

	public SeleniumFunctions(WebDriver driver) throws Throwable {
		this.driver = driver;
		setWebDriverWait();
	}

	private void setWebDriverWait() {

		waitDriver = new WebDriverWait(driver, config.longWait);

	}
	
	public void openUrl(String url) {
		driver.navigate().to(url);
	}
	

	public String getPageTitle() {
		return driver.getTitle();
	}

	public String getCurrentURL() {
		return driver.getCurrentUrl();
	}

	public WebElement getElement(String locator) throws Exception {
		WebElement ele = null;
		ele = waitDriver.until(ExpectedConditions.visibilityOfElementLocated(getLocator(locator)));
		return ele;
	}
	/**
	 * this return more than one element if locator matched
	 * @param locator
	 * @return
	 * @throws Exception
	 */
	public List<WebElement> getElements(String locator) throws Exception {
		List<WebElement> ele = null;
		ele = waitDriver.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(getLocator(locator)));
		return ele;
	}

	public WebElement waitForElementToBeClickable(String locator) throws Exception {
		return waitDriver.until(ExpectedConditions.elementToBeClickable(getLocator(locator)));
	}
	private By getLocator(String locator) throws Exception {
		By by = null;
		try {

			String arrLoc[] = locator.split(":==");
			if (arrLoc.length != 2) {
				throw new Exception("Locator is not defined correctly, use pattern like ID:==Some value");
			}

			switch (arrLoc[0].toUpperCase()) {
			case "ID":
				return By.id(arrLoc[1]);
			case "XPATH":
				return By.xpath(arrLoc[1]);
			case "CSS":
				return By.cssSelector(arrLoc[1]);
			case "NAME":
				return By.name(arrLoc[1]);
			case "TAGNAME":
				return By.tagName(arrLoc[1]);
			case "LINKTEXT":
				return By.linkText(arrLoc[1]);

			case "PARTIAL_LINKTEXT":
				return By.partialLinkText(arrLoc[1]);

			default:
				throw new Exception("Locator value is not correct :" + arrLoc[0]);
			}

		} catch (Throwable e) {
			e.printStackTrace();
		}
		return by;
	}
	/**
	 * this wait until the visibility of the element
	 * @param locator  example -> xpath :== .//td[@id='abc'] 2) ID:== id1223
	 * @throws Exception
	 */
	public void waitForElementToAppear(String locator) throws Exception {
		waitDriver.until(ExpectedConditions.visibilityOfElementLocated(getLocator(locator)));
	}

	public void waitForElementToDisappear(String locator) throws Exception {
		waitDriver.until(ExpectedConditions.invisibilityOfElementLocated(getLocator(locator)));
	}

	public void waitForTextToDisappear(String locator, String text) throws Exception {
		waitDriver.until(ExpectedConditions.not(ExpectedConditions.textToBe(getLocator(locator), text)));
	}
	


	/**
	 * 
	 * @param locator
	 *            : example -> xpath :== .//td[@id='abc'] 2) ID:== id1223
	 * @optionToBeSelect : which value you want to select
	 * @param selectBy
	 *            : text, index, value
	 * @throws Exception
	 */
	public void selectDropdown(String locator, String optionToBeSelect, String selectBy) throws Exception {
		WebElement ele = getElement(locator);
		Select select = new Select(ele);

		switch (selectBy.toLowerCase()) {

		case "text":
			select.selectByVisibleText(optionToBeSelect);
			break;
		case "value":
			select.selectByValue(optionToBeSelect);
			break;
		case "index":
			select.selectByIndex(Integer.valueOf(optionToBeSelect));
			break;

		default:
			throw new Exception("User called this method with wrong option : " + selectBy);
		}
	}

	public void switchToAlert() {

		// it will return the object of Alert class
		Alert alert = driver.switchTo().alert();
		alert.accept(); // It will click on OK

	}

	/**
	 * Function to get alert box text
	 * 
	 * @return
	 */
	public String getAlerText() {

		Alert alert = driver.switchTo().alert();
		String text = alert.getText();
		alert.dismiss();
		return text;

	}

	public void switchToFrame(WebElement ele) {
		driver.switchTo().frame(ele);
	}

	/**
	 * 
	 * @param locaotrs
	 *            Example : ID:== some value , XPATH:== xpath value
	 * @throws Exception
	 */
	public void switchToFrame(String locaotrs) throws Exception {

		WebElement ele = getElement(locaotrs);
		driver.switchTo().frame(ele);
	}

}
