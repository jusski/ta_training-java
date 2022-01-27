package com.epam.training.student_justinas_skierus.webdriver.fastmail;

import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.epam.training.student_justinas_skierus.webdriver.AbstractPageTest;
import com.epam.training.student_justinas_skierus.webdriver.utilities.ScreenshotCaptureOnTestFailure;

@Listeners(ScreenshotCaptureOnTestFailure.class)
public class LoginPageTest extends AbstractPageTest
{
	@Test
	public void shouldSuccessfulyLoginWithValidCredentials()
	{
	    FastMailLoginPage loginPage = new FastMailLoginPage(driver);
		loginPage.get();
		
		FastMailInboxPage inboxPage = loginPage.authenticate("rdx@fastmail.com", "asdf6ghj");
		Assert.assertTrue(inboxPage.isPageStateCorrect());
	}
	
	@Test
	public void shouldFailLoginWithInvalidUsername()
	{
	    FastMailLoginPage loginPage = new FastMailLoginPage(driver);
        loginPage.get();
        
		FastMailInboxPage inboxPage = loginPage.authenticate("nonsense", "asdf6gh");
		Assert.assertFalse(inboxPage.isPageStateCorrect());
	}
	
	@Test
	public void shouldFailLoginWithInvalidPassword()
	{
	    FastMailLoginPage loginPage = new FastMailLoginPage(driver);
        loginPage.get();
        
		FastMailInboxPage inboxPage = loginPage.authenticate("rdx@fastmail.com", "123456");
		Assert.assertFalse(inboxPage.isPageStateCorrect());
	}
	
	@Test
	public void shouldFailLoginWithBlankdPassword()
	{
	    FastMailLoginPage loginPage = new FastMailLoginPage(driver);
        loginPage.get();
        
		FastMailInboxPage inboxPage = loginPage.authenticate("rdx@fastmail.com", "");
		Assert.assertFalse(inboxPage.isPageStateCorrect());
	}
	
	@Test
	public void shouldFailLoginWithBlankdUsername()
	{
	    FastMailLoginPage loginPage = new FastMailLoginPage(driver);
        loginPage.get();
        
		FastMailInboxPage inboxPage = loginPage.authenticate("", "asdf6gh");
		Assert.assertFalse(inboxPage.isPageStateCorrect());
	}
}
