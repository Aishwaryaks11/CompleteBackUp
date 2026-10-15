package com.comcast.crm.listenerutility;

import java.util.Date;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.comcast.crm.basetest.BaseClass;
import com.comcast.crm.generic.webdriverutility.UtilityClassObject;

public class ListImpClass implements ITestListener,ISuiteListener {
	
	public ExtentSparkReporter spark;
	public static ExtentReports report;
	public static ExtentTest test;

	@Override
	public void onStart(ISuite suite) {
		//ISuiteListener.super.onStart(suite);
		System.out.println("Report configuration");
		
		String time=new Date().toString().replace(" ", "_").replace(":", "_");

		
		//spark report config
				ExtentSparkReporter spark= new ExtentSparkReporter("./AdvanceReport/report"+time+".html");
				spark.config().setDocumentTitle("CRM Test Suite Result");
				spark.config().setReportName("CRM Report");
				spark.config().setTheme(Theme.DARK);
				
				//add Env information and create test
				report=new ExtentReports();
				report.attachReporter(spark);
				report.setSystemInfo("OS","Windows-10");
				report.setSystemInfo("BROWSER", "CHROME-100");
				
				
	}

	@Override
	public void onFinish(ISuite suite) {
		//ISuiteListener.super.onFinish(suite);
		System.out.println("Report backUp");
		report.flush();
	}

	@Override
	public void onTestStart(ITestResult result) {
		//ITestListener.super.onTestStart(result);
		System.out.println("====="+result.getMethod().getMethodName()+"===Started==");
		test=report.createTest(result.getMethod().getMethodName());
		UtilityClassObject.setTest(test);
		test.log(Status.INFO, result.getMethod().getMethodName()+"===STARTED====");
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		//ITestListener.super.onTestSuccess(result);
		System.out.println("====="+result.getMethod().getMethodName()+"====END===");
		test.log(Status.PASS, result.getMethod().getMethodName()+"===COMPLETED====");
	}

	//@Override
//	public void onTestFailure(ITestResult result) {
//		
//		//ITestListener.super.onTestFailure(result);
//		
//		String testName=result.getMethod().getMethodName();
//		
//		TakesScreenshot eDriver=(TakesScreenshot)BaseClass.sd;
//		String filepath = eDriver.getScreenshotAs(OutputType.BASE64);
//		
////		TakesScreenshot ts=(TakesScreenshot)BaseClass.sd;    ---------
////		File src=ts.getScreenshotAs(OutputType.FILE);        ----------
//		
//		String time=new Date().toString().replace(" ", "_").replace(":", "_");
//		test.addScreenCaptureFromBase64String(filepath, testName+"_"+time);
//		test.log(Status.FAIL, result.getMethod().getMethodName()+"===FAILED====");
//		
////		File dest=new File("./screenshot/"+testName+"+"+time+".png");
////		
////		try {
////			FileHandler.copy(src, dest);
////		} catch (IOException e) {
////			e.printStackTrace();
////		}
//	}

	
	@Override
	public void onTestFailure(ITestResult result) 
	//When TestNG runs your test, TestNG itself creates and maintains an ITestResult object
	//containing information about that test execution.
	{

	    try {

	        String testName = result.getMethod().getMethodName();//-gets name of which method failed  it will be in side testname

	        WebDriver driver = UtilityClassObject.getDriver();//-Take a picture of what is currently visible in the browser.

	        if (driver == null) {
	            System.out.println("Driver is NULL, skipping screenshot");
	            return;
	        }

	        String filepath =
	                ((TakesScreenshot) driver)  //----"This driver can take screenshots."
	                        .getScreenshotAs(OutputType.BASE64); //---Take the screenshot NOW.

	        //driver belong to webDriver so java understands this
	        //but getScreenshot () belongs to takeScreenshot,
	        //so if u give driver.getscreenshot() Selenium think in webDriver
	        //we dont have screenshot() know so we should upCast..
	        //Java, I know u are treating driver as a WebDriver, but I want to use its TakesScreenshot capability now."
	        
	        String time = new Date().toString()
	                .replace(" ", "_")
	                .replace(":", "_");

	        test.addScreenCaptureFromBase64String(filepath, testName + "_" + time);

	        test.log(Status.FAIL, testName + "===FAILED===="); 
	        // To add print statement like failed in extent report it is just like SOP

	    } catch (Exception e) {
	        System.out.println("Screenshot error: " + e.getMessage());
	    }
	}
	
	
	
	
	
	
	@Override
	public void onTestSkipped(ITestResult result) {
		// TODO Auto-generated method stub
		ITestListener.super.onTestSkipped(result);
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub
		ITestListener.super.onTestFailedButWithinSuccessPercentage(result);
	}

	@Override
	public void onTestFailedWithTimeout(ITestResult result) {
		// TODO Auto-generated method stub
		ITestListener.super.onTestFailedWithTimeout(result);
	}

	@Override
	public void onStart(ITestContext context) {
		// TODO Auto-generated method stub
		ITestListener.super.onStart(context);
	}

	@Override
	public void onFinish(ITestContext context) {
		// TODO Auto-generated method stub
		ITestListener.super.onFinish(context);
	}

}
