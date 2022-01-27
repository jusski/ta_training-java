package com.epam.training.student_justinas_skierus.webdriver.sdfeu;

import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.Properties;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.epam.training.student_justinas_skierus.webdriver.ComposePage;
import com.epam.training.student_justinas_skierus.webdriver.InboxPage;
import com.epam.training.student_justinas_skierus.webdriver.MailPage;
import com.epam.training.student_justinas_skierus.webdriver.dummy.InvalidMailPage;

public class SDFInboxPage extends LoadableComponent<SDFInboxPage> implements InboxPage
{
	public static final String URL = "https://sdfeu.org/rc/?_task=mail&_mbox=INBOX";

	private WebDriver webdriver;
	private SDFLoginPage loginPage;
	private WebDriverWait webDriverWait;

	@FindBy(id = "rcmbtn107")
	private WebElement composeButton;
	@FindBy(id = "quicksearchbox")
	private WebElement searchInput;

	@FindBy(css = "#messagestack > .confirmation")
	private WebElement searchFoundMailMessagesConfirmationAlert;

	@FindBy(css = "#messagelist .message.unread")
	private List<WebElement> unreadEMails;

	public SDFInboxPage(WebDriver webdriver)
	{
	    this(webdriver, new SDFLoginPage(webdriver));
	}
	
    public SDFInboxPage(WebDriver webdriver, SDFLoginPage loginPage)
    {
        this.webdriver = webdriver;
        this.loginPage = loginPage;
        this.webDriverWait = new WebDriverWait(webdriver, TIMEOUT);
        PageFactory.initElements(new AjaxElementLocatorFactory(webdriver, TIME_OUT_IN_SECONDS), this);
    }

	public MailPage searchForUnreadEmailsWithSubject(String subject) 
	{
//		searchInput.click();
//		searchInput.sendKeys(subject, Keys.ENTER);
//		searchInput.click();
//		searchInput.sendKeys(Keys.ENTER);
	    
		Actions actions = new Actions(webdriver);
		actions.pause(Duration.ofMillis(500));
		actions.click(searchInput);
		actions.sendKeys(subject, Keys.ENTER);
		actions.build().perform();
		
		if(isElementDisplayed(searchFoundMailMessagesConfirmationAlert))
		{
			if(!unreadEMails.isEmpty())
			{
				String currentUrl = webdriver.getCurrentUrl();
				unreadEMails.get(0).click();
				unreadEMails.get(0).click();
				sleep().until(ExpectedConditions.not(ExpectedConditions.urlToBe(currentUrl)));
				
				return new SDFMailPage(webdriver);
			}
		}
		
		return new InvalidMailPage();
	}

	@Override
	protected void load()
	{
	    Properties properties = new Properties();
	    try
        {
            properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("authentication.properties"));
            String email = properties.getProperty("sdf-username");
            String password = properties.getProperty("sdf-password");
            loginPage.get().authenticate(email, password);
        }
        catch (IOException e)
        {
           e.printStackTrace();
        }
	}

	@Override
	protected void isLoaded() throws Error
	{
		Assert.assertTrue(isPageStateCorrect());
	}

	@Override
	public boolean isPageStateCorrect()
	{
		return isUrlBeginningWith(URL);
	}

	@Override
	public ComposePage clickComposeButton()
	{
		composeButton.click();
		throw new UnsupportedOperationException("method not implemented");
	}

	@Override
	public WebDriverWait sleep()
	{
		return webDriverWait;
	}

}