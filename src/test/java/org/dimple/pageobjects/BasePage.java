package org.dimple.pageobjects;

import org.dimple.common.SeleniumFunctions;
import org.openqa.selenium.WebDriver;

public class BasePage extends SeleniumFunctions {


	protected WebDriver driver;

	public BasePage(WebDriver driver) throws Throwable {
		super(driver);
		this.driver = driver;
	}
	

}
