package org.dimple.reporter;

import java.io.File;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;


// this is configuration of extent report 

public class ExtentManager {
	
    private static ExtentReports extent;
    private static String reportFileName = "Test-Report"+".html"; ///<<<<<< you can change the report name
    private static String fileSeperator = System.getProperty("file.separator");
    private static String reportFilepath = System.getProperty("user.dir") +fileSeperator+ "TestReport";
    private static String reportFileLocation =  reportFilepath +fileSeperator+ reportFileName;
    
  
 
    public static ExtentReports getInstance() {
        if (extent == null)
            createInstance();
        return extent;
    }
 
    //Create an extent report instance
    public static ExtentReports createInstance() {
    	
        String fileName = createReportPath();
       
        ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(fileName);
        htmlReporter.config().setAutoCreateRelativePathMedia(true);
        htmlReporter.config().setTheme(Theme.DARK);
        htmlReporter.config().setDocumentTitle(reportFileName);
        htmlReporter.config().setEncoding("utf-8");
        htmlReporter.config().setReportName(reportFileName);
        htmlReporter.config().setTimeStampFormat("EEEE, MMMM dd, yyyy, hh:mm a '('zzz')'");
        htmlReporter.config().setAutoCreateRelativePathMedia(false);
 
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
        //Set environment details
		extent.setSystemInfo("OS", "Windows");
		extent.setSystemInfo("AUT", "QA-Team");
 
        return extent;
    }
     
    //It Creates the report path if not exists 
    private static String createReportPath() {
    	File testDirectory = new File(reportFilepath);
        if (!testDirectory.exists()) {
        	if (testDirectory.mkdir()) {
                System.out.println("Directory: " + reportFilepath + " is created!" );
                return reportFileLocation;
            } else {
                System.out.println("Failed to create directory: " + reportFilepath);
                return System.getProperty("user.dir");
            }
        } else {
            System.out.println("Directory already exists: " + reportFilepath);
        }
		return reportFileLocation;
    }
    
    public static String getReportPath()
    {
    	return reportFilepath;
    }
    
 
}