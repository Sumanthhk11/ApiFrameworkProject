package api.utilities;


//Listener class used to generate Extent reports

import java.text.SimpleDateFormat;
import java.util.Date;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentReportManager implements ITestListener
{
	public ExtentSparkReporter sparkReporter;
	public ExtentReports extent;
	public ExtentTest test;
	
	String repName;	
	public void onStart(ITestContext testContext)
	{
		String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());//time stamp
		repName="Test-Report-"+timeStamp+".html";
		
		sparkReporter=new ExtentSparkReporter(System.getProperty("user.dir")+"//reports//"+ repName); //(.//reports//"+repName));
		//specify location of the report
		
		extent=new ExtentReports();
		
		sparkReporter.config().setDocumentTitle("RestAssured Automation Project"); // Tile of report
		sparkReporter.config().setReportName("Pet Store User API"); // name of the report
		sparkReporter.config().setTheme(Theme.DARK);
		
		extent.attachReporter(sparkReporter);
		extent.setSystemInfo("Application","Pet Store User API");
		extent.setSystemInfo("Operating System",System.getProperty("os.name"));
		extent.setSystemInfo("User Name",System.getProperty("user.name"));
		extent.setSystemInfo("Envinoment", "QA");
		extent.setSystemInfo("User","Sumanth");
		
	}
	
	public void onTestSuccess(ITestResult result)
	{
		test=extent.createTest(result.getName()); // create new entry in th report
		test.assignCategory(result.getMethod().getGroups());
		test.createNode(result.getName());
		test.log(Status.PASS,"Test Passed"); // send the passed information to the report with GREEN color highlighted
	}
	
	public void onTestFailure(ITestResult result)
	{
		test=extent.createTest(result.getName()); // create new entry in th report
		test.assignCategory(result.getMethod().getGroups());
		test.createNode(result.getName());
		test.log(Status.FAIL,"Test Failed");
		test.log(Status.FAIL, result.getThrowable().getMessage()); // send the passed information to the report with GREEN color highlighted
	}
	/*	String screenshotPath=System.getProperty("user.dir")+"\\Screenshots\\"+result.getName()+".png";
		
		File f = new File(screenshotPath); 
		
		if(f.exists())
		{
		try {
			test.fail("Screenshot is below:" + test.addScreenCaptureFromPath(screenshotPath));
			} 
		catch (IOException e) 
				{
				e.printStackTrace();
				}
		}
		
	}*/
	
	public void onTestSkipped(ITestResult result)
	{
		test=extent.createTest(result.getName()); // create new entry in th report
		test.assignCategory(result.getMethod().getGroups());
		test.createNode(result.getName());
		test.log(Status.SKIP,"Test Skipped");
		test.log(Status.SKIP,result.getThrowable().getMessage());
			}
	
	public void onFinish(ITestContext testContext)
	{
		extent.flush();
	}
}
