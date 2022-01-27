package com.epam.training.student_justinas_skierus.webdriver.fastmail;

import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.epam.training.student_justinas_skierus.webdriver.ComposePage;
import com.epam.training.student_justinas_skierus.webdriver.InboxPage;

public class FastMailInboxPage extends LoadableComponent<FastMailInboxPage> implements InboxPage
{
    public static final String URL = "https://www.fastmail.com/mail/Inbox/?u";

    private WebDriver webdriver;
    private FastMailLoginPage loginPage;
    private WebDriverWait webDriverWait;

    @FindBy(css = ".s-new-message")
    private WebElement composeButton;

    public FastMailInboxPage(WebDriver webdriver)
    {
        this(webdriver, new FastMailLoginPage(webdriver));
    }

    public FastMailInboxPage(WebDriver webdriver, FastMailLoginPage loginPage)
    {
        this.webdriver = webdriver;
        this.loginPage = loginPage;
        this.webDriverWait = new WebDriverWait(webdriver, TIMEOUT);
        PageFactory.initElements(new AjaxElementLocatorFactory(webdriver, TIME_OUT_IN_SECONDS), this);
    }

    @Override
    protected void load()
    {
        Properties properties = new Properties();
        try
        {
            properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("authentication.properties"));
            String email = properties.getProperty("fastmail-username");
            String password = properties.getProperty("fastmail-password");
            loginPage.get().authorize(email, password);
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
        return urlStartsWith(URL);
    }

    @Override
    public ComposePage clickComposeButton()
    {
        composeButton.click();

        return new FastMailComposePage(webdriver, this);
    }

    @Override
    public WebDriverWait sleep()
    {
        return webDriverWait;
    }

}
