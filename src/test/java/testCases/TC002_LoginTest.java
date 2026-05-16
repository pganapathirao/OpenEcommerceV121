package testCases;

import org.testng.annotations.Test;

import junit.framework.Assert;
import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import testBase.BaseClass;

public class TC002_LoginTest extends BaseClass {

	@Test(groups={"Sanity","Master"})
	public void verify_login()
	{
		logger.info("*****Starting TC_002 Lgin Test");
		try
		{
		HomePage hp=new HomePage(driver);
		hp.clickMyaccount();
		
		//login page
		hp.clickLogin();
		logger.info("*****Enter Lgin Page");
		LoginPage lp=new LoginPage(driver);
		lp.setEmail(p.getProperty("email"));
		lp.setPassword(p.getProperty("password"));
		lp.clickLogin();
		
		//Myaccount page
		MyAccountPage macc=new MyAccountPage(driver);
		boolean targetPage=macc.isMyAccountPageExists();
		
		//Assert.assertEquals(targetPage, true);
		Assert.assertTrue(targetPage);
		}
	
		catch(Exception e)
		{
			Assert.fail();
		}
		
		logger.info("*****Ending  TC_002 Lgin Test");
		
	}
}
