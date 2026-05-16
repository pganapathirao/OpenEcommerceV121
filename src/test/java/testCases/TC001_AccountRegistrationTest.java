package testCases;

import java.time.Duration;

import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import junit.framework.Assert;
import pageObjects.AccountRegistrationPage;
import pageObjects.HomePage;
import testBase.BaseClass;

public class TC001_AccountRegistrationTest extends BaseClass {

	
	
	@Test(groups={"Regression","Master"})
	public void verify_account_registration()
	{
		logger.info("***Starting**TC001_AccountRegistrationTest****");
		try
		{
		HomePage hp=new HomePage(driver);
		hp.clickMyaccount();
		logger.info("***Clicked on My Account Link****");
		hp.clickRegister();
		logger.info("***Clicked on Registered Link****");
		
		AccountRegistrationPage regpage=new AccountRegistrationPage(driver);
		logger.info("***Providing Customer Details****");
		regpage.setFirstName(randomString().toUpperCase());
		regpage.setLastName(randomString().toUpperCase());
		regpage.setEmail(randomString()+"@gamil.com");
		regpage.setTelephone(randomNumber());
		
		String password=randomAlphaNumeric();
		
		regpage.setPassword(password);
		regpage.setconfirmPassword(password);
		
		regpage.setPrivacyPolicy();
		regpage.clickContinue();
		
		logger.info("***Validating expected message****");
		
		String confmsg=regpage.getConfirmationMsg();
		if(confmsg.equals("Your Account Has Been Created!"))
		{
			Assert.assertTrue(true);
		}
		else
		{
			logger.error("Test Failed");
			logger.debug("Debug loggs");
			Assert.assertTrue(false);
		}

	}
		catch(Exception e)
		{
			Assert.fail();
		}
		
		logger.info("***Ending **TC001_AccountRegistrationTest****");
	
     }
}
