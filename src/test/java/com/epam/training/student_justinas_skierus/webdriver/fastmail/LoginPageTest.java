package com.epam.training.student_justinas_skierus.webdriver.fastmail;

import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.epam.training.student_justinas_skierus.webdriver.AbstractPageTest;
import com.epam.training.student_justinas_skierus.webdriver.utilities.ScreenshotCaptureOnTestFailure;

@Listeners(ScreenshotCaptureOnTestFailure.class)
public class LoginPageTest extends AbstractPageTest
{
	@Test(description = "Tests successfull authorization with valid credentials")
	public void shouldSuccessfulyLoginWithValidCredentials()
	{
	    FastMailLoginPage loginPage = new FastMailLoginPage(driver);
		loginPage.get();
		
		FastMailInboxPage inboxPage = loginPage.authenticate("rdx@fastmail.com", "asdf6ghj");
		Assert.assertTrue(inboxPage.isPageStateCorrect(), "Failed to login with valid username/password pair.");
	}
	
	@Test(description = "Tests unsuccessfull authorization with invalid username")
	public void shouldFailLoginWithInvalidUsername()
	{
	    FastMailLoginPage loginPage = new FastMailLoginPage(driver);
        loginPage.get();
        
		String email = "nonsense_and_should_not_exsist";
        String passphrase = "asdf6gh";
        FastMailInboxPage inboxPage = loginPage.authenticate(email, passphrase);
		Assert.assertFalse(inboxPage.isPageStateCorrect(), "Managed to login with invalid username: " + email + "and password: " + passphrase);
	}
	
	@Test(description = "Tests unsuccessfull authorization with invalid password")
	public void shouldFailLoginWithInvalidPassword()
	{
	    FastMailLoginPage loginPage = new FastMailLoginPage(driver);
        loginPage.get();
        
		String email = "rdx@fastmail.com";
        String passphrase = "123456";
        FastMailInboxPage inboxPage = loginPage.authenticate(email, passphrase);
		Assert.assertFalse(inboxPage.isPageStateCorrect(), "Managed to login with valid username: " + email + "and invalid password: " + passphrase);
	}
	
	@Test(description = "Tests unsuccessfull authorization with valid username and blank password")
	public void shouldFailLoginWithBlankdPassword()
	{
	    FastMailLoginPage loginPage = new FastMailLoginPage(driver);
        loginPage.get();
        
        String email = "rdx@fastmail.com";
		FastMailInboxPage inboxPage = loginPage.authenticate(email, "");
		Assert.assertFalse(inboxPage.isPageStateCorrect(), "Managed to login with username: " + email + "and blank password");
	}
	
	@Test(description = "Tests unsuccessfull authorization with blank username")
	public void shouldFailLoginWithBlankdUsername()
	{
	    FastMailLoginPage loginPage = new FastMailLoginPage(driver);
        loginPage.get();
        
		String passphrase = "asdf6gh";
        FastMailInboxPage inboxPage = loginPage.authenticate("", passphrase);
		Assert.assertFalse(inboxPage.isPageStateCorrect(), "Managed to login with blank username and password: " + passphrase);
	}
}
