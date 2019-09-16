package org.dimple.pagetest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dimple.common.Config;
import org.dimple.pageobjects.SSOSingInPage;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class SSOSingInPageTest extends BaseTest {

	private static final Logger log = LogManager.getLogger(SSOSingInPageTest.class.getName());
	
	SSOSingInPage signInPage = null; // create object of SignIn Page object
	Config  config = Config.getConfig();

	// do setup like below in each test class
	@BeforeClass
	public void setup(ITestContext ctx) throws Throwable
	{
		ctx.setAttribute("driver", getDriver());
		signInPage = new SSOSingInPage(getDriver());
	}
	
	
	@Test(description = "Login Test with valid credential")
	public void validLoginTest() throws Throwable {

		log.info("Executing the step : enter user name");
		
		signInPage.enterUserName(config.defaultUserName);
		log.info("Executing the step : enter user password");
		signInPage.enterPassword(config.defaultPassword);
		log.info("Executing the step : click on login button");
		signInPage.clickOnLogin();
		
		
	}
	@Test(description="")
	public void inValidLoginTest() throws Throwable {

		log.info("Executing the step : enter invalid user name");		
		signInPage.enterUserName("xyzzzz");
		log.info("Executing the step : enter invalid user password");
		signInPage.enterPassword("sadsdakajdk");
		log.info("Executing the step : click on login button");
		signInPage.clickOnLogin();
		
		Assert.fail();
		
		
	}
	
	@Test(description="valid user name and invalid password")
	public void inValidPasswordLoginTest() throws Throwable {

		log.info("Executing the step : enter invalid user name");		
		signInPage.enterUserName("valid user name");
		log.info("Executing the step : enter invalid user password");
		signInPage.enterPassword("invalid password");
		log.info("Executing the step : click on login button");
		signInPage.clickOnLogin();
		
		Assert.fail();
		
		
	}
}
