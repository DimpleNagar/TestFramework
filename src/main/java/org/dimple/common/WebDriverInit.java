package org.dimple.common;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import io.github.bonigarcia.wdm.WebDriverManager;

public class WebDriverInit {

	private static WebDriver driver = null;
	private static Config config;

	private WebDriverInit() throws Throwable {
		// call config to read props
		config = Config.getConfig();
		createWebDriver(config.browser);
		setWindowSize();
	}

	private void createWebDriver(String browserName) throws Throwable {
		try {

			if (browserName.equalsIgnoreCase("chrome")) {
				WebDriverManager.chromedriver().setup();

				// set up some common capabilities
				DesiredCapabilities cap = getCapability(browserName);
				ChromeOptions opt = new ChromeOptions();
				opt.merge(cap);
				System.out.println("==================");
				driver = new ChromeDriver(opt);

			} else if (browserName.equalsIgnoreCase("ie") || browserName.equalsIgnoreCase("internetexplorer")) {
				WebDriverManager.iedriver().setup();
				// get common capabilities
				DesiredCapabilities cap = getCapability(browserName);
				InternetExplorerOptions opt = new InternetExplorerOptions();
				opt.merge(cap);

				driver = new InternetExplorerDriver(opt);
			} else if (browserName.equalsIgnoreCase("ff") || browserName.equalsIgnoreCase("firefox")) {
				WebDriverManager.firefoxdriver().setup();
				// get common capabilities
				DesiredCapabilities cap = getCapability(browserName);
				FirefoxOptions opt = new FirefoxOptions();
				opt.merge(cap);

				driver = new FirefoxDriver(opt);
			} else {
				throw new Exception("Browser name : " + browserName + " is not correct");
			}
		} catch (Throwable t) {
			t.printStackTrace();
			throw new Exception(t.getMessage());
		}

	}

	private DesiredCapabilities getCapability(String browserName) {
		// set up some common capabilities
		DesiredCapabilities cap = null;
		if (browserName.equalsIgnoreCase("ff") || browserName.equalsIgnoreCase("firefox")) {
			cap = DesiredCapabilities.firefox();
		} else if (browserName.equalsIgnoreCase("ie") || browserName.equalsIgnoreCase("internetexplorer")) {
			cap = DesiredCapabilities.internetExplorer();
			// IE Specific
			cap.setCapability(InternetExplorerDriver.IGNORE_ZOOM_SETTING, true);
			cap.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
		} else if (browserName.equalsIgnoreCase("chrome")) {
			cap = DesiredCapabilities.chrome();
		}

		if (cap != null) {
			cap.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);
			cap.setCapability(CapabilityType.SUPPORTS_ALERTS, true);
			cap.setCapability(CapabilityType.ELEMENT_SCROLL_BEHAVIOR, true);
			cap.setCapability(CapabilityType.SUPPORTS_JAVASCRIPT, true);
			cap.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
			// cap.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR,
			// true);
			// cap.setCapability(CapabilityType.UNHANDLED_PROMPT_BEHAVIOUR,
			// true);
		}

		return cap;
	}

	private void setWindowSize() {
		if (driver != null) {
			driver.manage().window().maximize();
			driver.get(config.baseURL);

		}

	}

	/**
	 * Call this function from outside the class
	 * 
	 * @return WebDriver
	 * @throws Throwable
	 */
	public static WebDriver getWebDriver() throws Throwable {
		// if driver object is null then only create object other wise return
		// existing driver
		if (driver == null) {
			new WebDriverInit();
		}
		return driver;
	}

}
