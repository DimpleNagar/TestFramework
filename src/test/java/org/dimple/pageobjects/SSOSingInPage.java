package org.dimple.pageobjects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dimple.locators.SignInPageLocators;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class SSOSingInPage extends BasePage {
	
	private static final Logger log = LogManager.getLogger(SSOSingInPage.class.getName());
	
	public SSOSingInPage(WebDriver driver) throws Throwable {
		super(driver);
	}
	
	public void enterUserName(String userName) throws Exception{
		
		WebElement eleUserName = getElement(SignInPageLocators.userName);
		log.debug("Enterting text["+ userName +"] into " + SignInPageLocators.userName);
		eleUserName.clear();
		eleUserName.sendKeys(userName);
		log.info("Entered Successfully");
		
	}
	public void enterPassword(String password) throws Exception{
		
		WebElement elePassword = getElement(SignInPageLocators.userPassword);
		elePassword.clear();
		log.debug("Enterting text["+ password +"] into " + SignInPageLocators.userPassword);
		elePassword.sendKeys(password);
		
	}
	public void clickOnLogin() throws Exception{
		
		WebElement loginButton = getElement(SignInPageLocators.loginButton);
		loginButton.click();
		
	}

}
