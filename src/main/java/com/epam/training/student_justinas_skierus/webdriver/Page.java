package com.epam.training.student_justinas_skierus.webdriver;

import java.time.Duration;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public interface Page
{
	static final int TIME_OUT_IN_SECONDS = 5;
	static final Duration TIMEOUT = Duration.ofSeconds(TIME_OUT_IN_SECONDS);
	
	boolean isPageStateCorrect();
    WebDriverWait sleep();
	
	public default boolean urlStartsWith(String url)
	{
	    return expectedCondition((driver) -> driver.getCurrentUrl().startsWith(url));
	}
	
    public default ExpectedCondition<Boolean> urlChanges(String currentUrl)
    {
        return ExpectedConditions.not(ExpectedConditions.urlToBe(currentUrl));
    }
    
	public default boolean elementIsDisplayed(WebElement element)
	{
		try
		{
			return element.isDisplayed();
		}
		catch(TimeoutException | NoSuchElementException e)
		{
			
		}
		return false;
	}
	
	public default boolean expectedCondition(ExpectedCondition<Boolean> expectedCondition)
	{
		try
		{
			return sleep().until(expectedCondition);
		}
		catch(TimeoutException | NoSuchElementException e)
		{
			
		}
		return false;
	}
}
