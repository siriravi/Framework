package com.learnautomation.pages;

import java.io.File;
import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.learnautomation.utility.BrowserFactory;
import com.learnautomation.utility.ConfigDataProvider;
import com.learnautomation.utility.ExcelDataProvider;
import com.learnautomation.utility.Helper;

public class BaseClass {
	
	public WebDriver driver;
	public ExcelDataProvider excel;
	public ConfigDataProvider config;
	public ExtentReports report;
	public ExtentTest logger;
	
	@BeforeSuite
	public void setUpSuite()
	{
		
		Reporter.log("Setting up reports and Test Started", true);
		excel = new ExcelDataProvider();
		config = new ConfigDataProvider();
		
		ExtentHtmlReporter extent = new ExtentHtmlReporter(new File(System.getProperty("user.dir"))+"/Reports/OrangeHRM_"+Helper.getCurrentDateTime()+".html");
		report = new ExtentReports();
		report.attachReporter(extent);
		
		Reporter.log("Configuration Done and Test can be Started", true);
		
	}
	
	@Parameters({"browser","urltobeTested"})
	@BeforeClass
	public void setup(String browser, String url)
	{
		Reporter.log("Trying to start browser and getting application ready", true);
		
		driver = BrowserFactory.startApplication(driver, browser, url);
		//driver = BrowserFactory.startApplication(driver, config.getBrowser(), config.getAppURL());
	}
	
	
	@AfterClass
	public void tearDown()
	{
		BrowserFactory.closeBrowser(driver);
		
	} 
	
	@AfterMethod
	public void tearDownMethod(ITestResult result) throws IOException
	
		{
		Reporter.log("Test is about to end 	", true);
		
		if(result.getStatus()==ITestResult.FAILURE)
		{
			Helper.captureScreenshot(driver);
			logger.fail("Test Failed", MediaEntityBuilder.createScreenCaptureFromPath(Helper.captureScreenshot(driver)).build());
		}
		else if(result.getStatus()==ITestResult.SUCCESS)
		{
			Helper.captureScreenshot(driver);
			logger.pass("Test Passed", MediaEntityBuilder.createScreenCaptureFromPath(Helper.captureScreenshot(driver)).build());
		}
		else if(result.getStatus()==ITestResult.SKIP)
		{
			Helper.captureScreenshot(driver);
			logger.skip("Test Skipped", MediaEntityBuilder.createScreenCaptureFromPath(Helper.captureScreenshot(driver)).build());
		}
		
		report.flush();
		
		Reporter.log("Tests Completed >>> Reports Generated", true);
		
	}

}
