package org.dimple.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class Config {

	public String configFile = "/TestFramework/src/test/resources/config.properties";

	// create variables to store values from file: config.properties
	public String baseURL;
	public String browser;
	public String defaultUserName;
	public String defaultPassword;

	public long shortWait;
	public long mediumtWait;
	public long longWait;

	public Config() {
		readConfig();
	}

	/**
	 * function to read all defined configs from Config.properties
	 */
	public void readConfig() {
		try {
			FileInputStream fis = new FileInputStream(new File(configFile));

			Properties props = new Properties();
			props.load(fis);
			baseURL = props.getProperty("baseURL");
			baseURL = props.getProperty("browser");

			baseURL = props.getProperty("defaultUserName");
			baseURL = props.getProperty("defaultPassword");

			shortWait = new Long(props.getProperty("shortWait")).longValue();
			mediumtWait = new Long(props.getProperty("mediumWait")).longValue();
			longWait = new Long(props.getProperty("longWait")).longValue();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
