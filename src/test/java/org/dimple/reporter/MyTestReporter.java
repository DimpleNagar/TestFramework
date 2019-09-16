package org.dimple.reporter;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;

// this is the main custom reporting listener 
// use this in testng xml test suite to create report

public class MyTestReporter implements ITestListener {

	private static final Logger log = LogManager.getLogger(MyTestReporter.class.getName());

	public void onStart(ITestContext context) {
		System.out.println("*** Test Suite " + context.getSuite().getName() + " started ***");
	}

	public void onFinish(ITestContext context) {
		log.info(("*** Test Suite " + context.getSuite().getName() + " ending ***"));
		ExtentTestManager.endTest();
		ExtentManager.getInstance().flush();
	}

	public void onTestStart(ITestResult result) {
		log.info(("Running test method ==> " + result.getMethod().getMethodName() + "..."));
	
		String testName =  (result.getMethod().getDescription() != null) ? result.getMethod().getDescription().trim(): 
			result.getMethod().getMethodName();
		ExtentTestManager.startTest((testName.isEmpty())?result.getMethod().getDescription() : testName);
	}

	public void onTestSuccess(ITestResult result) {
		log.info("*** Executed ===> [" + result.getMethod().getMethodName() + "] test successfully...");
		ExtentTestManager.getTest().log(Status.PASS, "Test passed");
	}

	public void onTestFailure(ITestResult result) {
		
		log.info("*** Test execution " + result.getMethod().getMethodName() + " failed...");
		log.info((result.getMethod().getMethodName() + " failed!"));

		ITestContext context = result.getTestContext();

		// take screenshot and attach screenshots to report
		try {
			WebDriver driver = (WebDriver) context.getAttribute("driver");
			File screenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			String targetLocation = getScreenshotPath(result);
			File targetFile = new File(targetLocation);
			log.info("Screen shot file location - " + screenshotFile.getAbsolutePath());
			log.info("Target File location - " + targetFile.getAbsolutePath());
			FileUtils.copyFile(screenshotFile, targetFile);
			ExtentTestManager.getTest().fail("Screenshot",
					MediaEntityBuilder.createScreenCaptureFromPath(targetLocation).build());
			ExtentTestManager.getTest().log(Status.FAIL, "Test Failed, Please see screenshot and error logs");
			ExtentTestManager.getTest().log(Status.FAIL, MarkupHelper.createLabel(result.getName()+" FAILED ", ExtentColor.RED));
			ExtentTestManager.getTest().fail(result.getThrowable());
		} catch (IOException e) {
			log.info("An exception occured while taking screenshot " + e.getCause());
		}
		

	}

	public void onTestSkipped(ITestResult result) {
		System.out.println("*** Test " + result.getMethod().getMethodName() + " skipped...");
		ExtentTestManager.getTest().log(Status.SKIP, "Test Skipped");

	}

	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		System.out.println("*** Test failed but within percentage % " + result.getMethod().getMethodName());
	}

	private String getScreenshotPath(ITestResult result) {
		String screenShotFilePath = null;

		try {
			String classInstance = result.getInstanceName().trim().replaceAll("\\.", "-");
			String arrClass[] = classInstance.split("-");
			String testClassName = (arrClass.length > 1) ? arrClass[arrClass.length-1] : classInstance.substring(classInstance.lastIndexOf(".") + 1);
			long timeStamp = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC);
			String testMethodName = result.getName().toString().trim();
			String screenShotName = testMethodName + "_" + timeStamp + ".png";
			String fileSeperator = System.getProperty("file.separator");
			String reportsPath = ExtentManager.getReportPath() + fileSeperator + "screenshots";
			log.info("Screen shots reports path - " + reportsPath);

			File file = new File(reportsPath + fileSeperator + testClassName);
			if (!file.exists()) {
				if (file.mkdirs()) {
					log.info("Directory: " + file.getAbsolutePath() + " is created!");
				} else {
					log.info("Failed to create directory: " + file.getAbsolutePath());
				}

			}

			screenShotFilePath = reportsPath + fileSeperator + testClassName + fileSeperator + screenShotName;

		} catch (Exception e) {
			log.error("An exception occurred while creating screenshot folder and file name " + e.getCause());
		}
		return screenShotFilePath;
	}

}
