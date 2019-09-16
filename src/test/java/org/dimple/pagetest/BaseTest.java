package org.dimple.pagetest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dimple.common.WebDriverInit;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;


public class BaseTest {

	
	private static final Logger log = LogManager.getLogger(BaseTest.class.getName());
	
	public WebDriver driver;

	@BeforeSuite
	public void beforeSuite() throws Throwable {
		log.debug("Creating WebDriver...........");
		driver = WebDriverInit.getWebDriver();

	}

	@AfterSuite
	public void afterSuite() {
		try {
			log.debug("CLosing the browser............");
			Thread.sleep(10000);
			if (null != driver) {
				driver.close();
				driver.quit();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	

	public WebDriver getDriver() {
		return driver;
	}
}
