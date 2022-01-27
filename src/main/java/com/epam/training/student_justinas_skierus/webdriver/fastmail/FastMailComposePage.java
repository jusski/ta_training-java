package com.epam.training.student_justinas_skierus.webdriver.fastmail;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.epam.training.student_justinas_skierus.webdriver.ComposePage;

public class FastMailComposePage extends LoadableComponent<FastMailComposePage> implements ComposePage
{
	private final String URL = "https://www.fastmail.com/mail/Inbox/compose?u";
	
	private final FastMailInboxPage parent;
	private final WebDriver webdriver;
	private WebDriverWait webDriverWait;
	
	@FindBy(css = ".v-Button.s-send")
	WebElement sendButton;
	
	@FindBy(css = ".v-EmailInput-input")
	WebElement toInput;
	
	@FindBy(css = "input[class=v-TextInput-input]")
	WebElement subjectInput;
	
	@FindBy(css = ".v-RichText-input.u-article")
	WebElement textBodyInput;

	public FastMailComposePage(WebDriver webdriver)
	{
		this(webdriver, new FastMailInboxPage(webdriver));
	}
	public FastMailComposePage(WebDriver webdriver, FastMailInboxPage parent)
	{
	   this.parent = parent;
	   this.webdriver = webdriver;
	   this.webDriverWait = new WebDriverWait(webdriver, TIMEOUT);
	   PageFactory.initElements(new AjaxElementLocatorFactory(webdriver, TIME_OUT_IN_SECONDS), this);
	}

	public FastMailInboxPage sendEmail(String to, String subject, String textBody)
	{
		toInput.click();
		toInput.sendKeys(to);

		subjectInput.sendKeys(subject);
		textBodyInput.sendKeys(textBody);
		
		String currentUrl = webdriver.getCurrentUrl();
		sendButton.click();
        sleep().until(urlChanges(currentUrl));
        
		return parent;
	}
	@Override
    protected void load()
    {
        parent.get();
        String currentUrl = webdriver.getCurrentUrl();
        webdriver.get(URL);
        sleep().until(urlChanges(currentUrl));
    }
	
	@Override
	protected void isLoaded() throws Error
	{
		Assert.assertTrue(isPageStateCorrect(), "Page is not loaded. Tried to load: " + URL + " but currentUrl is: " + webdriver.getCurrentUrl());
	}

	@Override
	public boolean isPageStateCorrect()
	{
		return isUrlBeginningWith(URL);
	}

	@Override
	public WebDriverWait sleep()
	{
		return webDriverWait;
	}
}
