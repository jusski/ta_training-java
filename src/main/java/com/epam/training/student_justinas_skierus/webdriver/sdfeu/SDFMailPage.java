package com.epam.training.student_justinas_skierus.webdriver.sdfeu;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.epam.training.student_justinas_skierus.webdriver.MailPage;

public class SDFMailPage implements MailPage
{
	private WebDriver webdriver;
	private WebDriverWait webDriverWait;

	@FindBy(css = "td.header.from > span > a.rcmContactAddress")
	private WebElement from;
	@FindBy(css = "#messageheader > h2 > span")
	private WebElement subject;
	@FindBy(css = "#message-part1 > div")
	private WebElement textBody;
	
	@FindBy(css = "#rcmcountdisplay")
	private WebElement countDisplay;
	@FindBy(css = "#rcmbtn130 > span")
	private WebElement nextMessageButton;
	
	public SDFMailPage(WebDriver webdriver)
	{
		this.webdriver = webdriver;
		this.webDriverWait = new WebDriverWait(webdriver, TIMEOUT);
		PageFactory.initElements(new AjaxElementLocatorFactory(webdriver, TIME_OUT_IN_SECONDS), this);
	}
	
	@Override
	public boolean isPageStateCorrect()
	{
		return elementIsDisplayed(from) &&
			   elementIsDisplayed(subject) &&
			   elementIsDisplayed(textBody);
	}

	@Override
	public WebDriverWait sleep()
	{
		return webDriverWait;
	}

	@Override
	public String getFrom()
	{
		return from.getText();
	}

	@Override
	public String getSubject()
	{
		return subject.getText();
	}

	@Override
	public String getBody()
	{
		return textBody.getText();
	}

	@Override
	public boolean hasNext()
	{
        if(countDisplay.getText().startsWith("Loading"))
        {
        	sleep().until(ExpectedConditions.textToBePresentInElement(countDisplay, "Message"));
        }
		if (countDisplay.getText().startsWith("Message"))
		{
			System.out.println(countDisplay.getText());
			Matcher matcher = Pattern.compile("Message (\\d+) of (\\d+)").matcher(countDisplay.getText());
			matcher.matches();
			return !matcher.group(1).equals(matcher.group(2));
		}
		System.out.println(countDisplay.getText());
		return false;
	}

	@Override
	public MailPage next()
	{
		if(hasNext())
		{
			String currentUrl = webdriver.getCurrentUrl();
			nextMessageButton.click();
			sleep().until(ExpectedConditions.not(ExpectedConditions.urlToBe(currentUrl)));
			sleep().until(ExpectedConditions.textToBePresentInElement(countDisplay, "Message"));
			
			return new SDFMailPage(webdriver);
		}
		return null;
	}
}
