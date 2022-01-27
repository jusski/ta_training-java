package com.epam.training.student_justinas_skierus.webdriver;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverLogLevel;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import com.epam.training.student_justinas_skierus.webdriver.utilities.ScreenshotCaptureOnTestFailure;

public abstract class AbstractPageTest
{
	protected WebDriver driver;
	private ChromeOptions options;
	
	@BeforeSuite
	public void beforeSuit()
	{
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\R61\\Downloads\\chromedriver_win32\\chromedriver.exe");
		options = new ChromeOptions();
		options.setLogLevel(ChromeDriverLogLevel.OFF);
		Logger.getLogger("org.openqa.selenium").setLevel(Level.OFF);
	}
	
	 @BeforeMethod
     public void beforeMethod()
     {
         createNewWebDriverSession();
         ScreenshotCaptureOnTestFailure.driver = driver;
     }

     @AfterMethod
     public void afterMethod()
     {
         closeWebDriverSession();
     }
	    
	protected void createNewWebDriverSession()
	{
		driver = new ChromeDriver(options);
		driver.manage().deleteAllCookies();
		driver.manage().window().setSize(new Dimension(500, 600));
	}
	
	protected void closeWebDriverSession()
	{
		if(driver != null) driver.close();
	}
    
}
