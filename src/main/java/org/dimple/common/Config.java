package org.dimple.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class Config {

	public String configFile = "./src/test/resources/config.properties";

	// create variables to store values from file: config.properties
	public String baseURL;
	public String browser;
	public String defaultUserName;
	public String defaultPassword;

	public long shortWait;
	public long mediumtWait;
	public long longWait;
	
	
	private static Config config = null;

	private Config() {
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
			baseURL = props.getProperty("baseURL").trim();
			browser = props.getProperty("browser").trim();

			defaultUserName = props.getProperty("defaultUserName").trim();
			defaultPassword = props.getProperty("defaultPassword").trim();
			

			shortWait = new Long(props.getProperty("shortWait").trim()).longValue();
			mediumtWait = new Long(props.getProperty("mediumWait").trim()).longValue();
			longWait = new Long(props.getProperty("longWait").trim()).longValue();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public static Config getConfig()
	{
		if(config == null)
			config = new Config();
		return config;
	}

}
