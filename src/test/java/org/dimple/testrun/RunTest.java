package org.dimple.testrun;


import java.util.ArrayList;
import java.util.List;

import org.testng.TestNG;

/**
 * 
 * Run this file only when you want to execute test without maven command
 *
 */
public class RunTest {

	public static void main(String[] args) {

		try {
			
			String fileSeperator = System.getProperty("file.separator");
			String suiteFilePath = System.getProperty("user.dir") + fileSeperator + "TestSuite.xml";

			TestNG testng = new TestNG();
			List<String> suites = new ArrayList<String>();
			suites.add(suiteFilePath);
			// suites.add("TestSuite.xml");
			testng.setTestSuites(suites);

			testng.run();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
