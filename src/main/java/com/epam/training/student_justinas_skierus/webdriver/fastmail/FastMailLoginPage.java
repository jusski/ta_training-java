package com.epam.training.student_justinas_skierus.webdriver.fastmail;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.epam.training.student_justinas_skierus.webdriver.LoginPage;

public class FastMailLoginPage extends LoadableComponent<FastMailLoginPage> implements LoginPage 
{
	private static final String URL = "https://www.fastmail.com/mail/";

	private WebElement username;
	private WebElement password;
	
	@FindBy(css = "form .v-Button") 
	private WebElement loginButton;
	
	private WebDriver webdriver;
	private WebDriverWait webDriverWait;

	public FastMailLoginPage(WebDriver webdriver)
	{
		this.webdriver = webdriver;
		this.webDriverWait = new WebDriverWait(webdriver, TIMEOUT);
		PageFactory.initElements(new AjaxElementLocatorFactory(webdriver, TIME_OUT_IN_SECONDS), this);
	}
	
	@Override
	public FastMailInboxPage authenticate(String email, String passphrase)
	{
		username.sendKeys(email);
		password.sendKeys(passphrase, Keys.ENTER);
		
		return new FastMailInboxPage(webdriver, this);
	}
	
	@Override
	protected void load() 
	{
		webdriver.get(URL);
	}
	
	@Override
	protected void isLoaded() throws Error
	{
		Assert.assertTrue(isPageStateCorrect(), "Failed to load FastMail login page.");
	}

	@Override
	public boolean isPageStateCorrect()
	{
	    return isUrlBeginningWith(URL) &&
			   isElementDisplayed(username) && 
			   isElementDisplayed(password) &&
			   isElementDisplayed(loginButton);
	}

	@Override
	public WebDriverWait sleep()
	{
		return webDriverWait;
	}
}
