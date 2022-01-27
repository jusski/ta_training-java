package com.epam.training.student_justinas_skierus.webdriver.fastmail;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.epam.training.student_justinas_skierus.webdriver.AbstractPageTest;

public class ComposePageTest extends AbstractPageTest
{
	
	@Test
	public void shouldSendEmail()
	{
        FastMailComposePage composePage = new FastMailComposePage(driver);
		composePage.get();
		
		FastMailInboxPage inboxPage = composePage.sendEmail("jusski@sdf-eu.org", "first blood", "kaip laikaisi?");
		Assert.assertTrue(inboxPage.isPageStateCorrect());
	}
}
