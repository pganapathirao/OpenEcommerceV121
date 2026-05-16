package utilities;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import testBase.BaseClass;

public class ExtentReportManager implements ITestListener
{
	
	public ExtentSparkReporter sparkreporter;
	public ExtentReports extent;
	public ExtentTest test;
	
	String repname;
	
	public void onStart(ITestContext context) {
		
		/*SimpleDateFormat df=new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");
		Date dt=new Date();
		String currentdatetimestamp=df.format(dt);*/
		
        String timestamp=new SimpleDateFormat("yyyy.mm.dd.HH.mm.ss").format(new Date());
        
        repname="Test-Report-"+timestamp+".html";
        sparkreporter=new ExtentSparkReporter(".\\reports\\"+repname);
		//sparkreporter=new ExtentSparkReporter(System.getProperty("user.dir")+"/reports/myReport.html");
		sparkreporter.config().setDocumentTitle("OpenCart Automation Report");
		sparkreporter.config().setReportName("Functional Testing");
		sparkreporter.config().setTheme(Theme.DARK);
		
		
		extent=new ExtentReports();
		extent.attachReporter(sparkreporter);
		
		
		extent.setSystemInfo("Computername", "LocalHost");
		extent.setSystemInfo("Envionment", "QA");
		extent.setSystemInfo("TestName", "Ganesh");
		extent.setSystemInfo("BrowserName", "QA");
		
		String os=context.getCurrentXmlTest().getParameter("os");
		extent.setSystemInfo("operating system", os);
		
		String browser=context.getCurrentXmlTest().getParameter("browser");
		extent.setSystemInfo("Browser", browser);
		
		
		List<String>includeGroups=context.getCurrentXmlTest().getIncludedGroups();
		if(!includeGroups.isEmpty())
			extent.setSystemInfo("Groups", includeGroups.toString());
		
    }
	
	public void onTestSuccess(ITestResult result) {
       
		test=extent.createTest(result.getTestClass().getName());
		test.assignCategory(result.getMethod().getGroups());
		test.log(Status.PASS, "Test Case Passed"+result.getName());
		
    }
	
	  public void onTestFailure(ITestResult result) {
		  test=extent.createTest(result.getTestClass().getName());
		  test.assignCategory(result.getMethod().getGroups());
			test.log(Status.FAIL, "Test Case Failed"+result.getName());
			test.log(Status.INFO, result.getThrowable().getMessage());
			
			try
			{
				String imgPath=new BaseClass().captureScreen(result.getName());
				test.addScreenCaptureFromPath(imgPath);
			}
			catch(IOException e1)
			{
				e1.printStackTrace();
			}
			
	    }
	  
	    public void onTestSkipped(ITestResult result) {
	    	  test=extent.createTest(result.getTestClass().getName());
			  test.assignCategory(result.getMethod().getGroups());
			  test.log(Status.SKIP, "Test Case Skipped"+result.getName());
			  test.log(Status.INFO, result.getThrowable().getMessage());
	    }
	    
	    public void onFinish(ITestContext context) {
	    	extent.flush();
	    	
	    	String pathofExtentReport =System.getProperty("user.dir")+"\\reports\\"+repname;
	    	File extentReport=new File(pathofExtentReport);
	    	try
	    	{
	    		Desktop.getDesktop().browse(extentReport.toURI());
	    	}
	    	catch(IOException e)
	    	{
	    		e.printStackTrace();
	    	}
	    }

}
