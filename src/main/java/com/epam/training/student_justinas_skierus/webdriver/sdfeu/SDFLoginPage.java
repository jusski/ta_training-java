package com.epam.training.student_justinas_skierus.webdriver.sdfeu;

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
import com.epam.training.student_justinas_skierus.webdriver.Page;

public class SDFLoginPage extends LoadableComponent<SDFLoginPage> implements LoginPage 
{
	private final String URL = "https://sdfeu.org/rc/";
	
	private final WebDriver webdriver;
	private WebDriverWait webDriverWait;

	public SDFLoginPage(WebDriver webdriver)
	{
		this.webdriver = webdriver;
		this.webDriverWait = new WebDriverWait(webdriver, TIMEOUT);
		PageFactory.initElements(new AjaxElementLocatorFactory(webdriver, TIME_OUT_IN_SECONDS), this);
	}
	
	@FindBy(id = "rcmloginuser")
	private WebElement username;
	@FindBy(id = "rcmloginpwd")
	private WebElement password;
	
	@Override
	public boolean isPageStateCorrect()
	{
		return urlStartsWith(URL) &&
			   elementIsDisplayed(username) &&
			   elementIsDisplayed(password);
	}

	@Override
	public WebDriverWait sleep()
	{
		return webDriverWait;
	}

	@Override
	public Page authorize(String email, String password)
	{
		this.username.sendKeys(email);
		this.password.sendKeys(password, Keys.ENTER);
		
		return new SDFInboxPage(webdriver, this);
	}

	@Override
	protected void load()
	{
		webdriver.get(URL);
	}

	@Override
	protected void isLoaded() throws Error
	{
		Assert.assertTrue(isPageStateCorrect());
	}

}
