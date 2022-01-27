package com.epam.training.student_justinas_skierus.webdriver;

import java.util.UUID;

import org.testng.Assert;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;
import org.testng.annotations.Test;

import com.epam.training.student_justinas_skierus.webdriver.fastmail.FastMailComposePage;
import com.epam.training.student_justinas_skierus.webdriver.sdfeu.SDFInboxPage;
import com.epam.training.student_justinas_skierus.webdriver.sdfeu.SDFMailPage;

public class IntegrationTest extends AbstractPageTest
{
    String SDF_EMAIL_ADDRESS = "jusski@sdf-eu.org";
    String FASTMAIL_EMAIL_ADDRESS = "rdx@fastmail.com";
    String randomSubject = UUID.randomUUID().toString();
    String randomText = UUID.randomUUID().toString();
   
    @Test
    public void shouldSendEmailFromFastMailProvider()
    {
        FastMailComposePage composeMailPage = new FastMailComposePage(driver);
        composeMailPage.get();
        
        InboxPage inboxPage = composeMailPage.sendEmail(SDF_EMAIL_ADDRESS, randomSubject, randomText);
        Assert.assertTrue(inboxPage.isPageStateCorrect(), "Error in sending email.");
    }
    
    @Test(dependsOnMethods = "shouldSendEmailFromFastMailProvider",
          retryAnalyzer = AbstractPageTest.RetryAnalyzer.class)
    public void shouldReceiveEmailInSDFProviderFromFastMailInbox()
    {
        SDFInboxPage inboxPage = new SDFInboxPage(driver);
        inboxPage.get();
        
        MailPage email = inboxPage.searchForEmailsWithSubject(randomSubject);
        Assert.assertTrue(email.isPageStateCorrect(), "Could not find email with subject: " + randomSubject);
        Assert.assertTrue(email.getSubject().contains(randomSubject), 
                "Subject in email is wrong. Expected: " + randomSubject + " but got: " + email.getSubject());
        Assert.assertTrue(email.getBody().contains(randomText), 
                "Body in email is wrong. Expected: " + randomText + " but got: " + email.getBody());
        Assert.assertTrue(email.getFrom().contains(FASTMAIL_EMAIL_ADDRESS), 
                "Sender is wrong. Expected: " + FASTMAIL_EMAIL_ADDRESS + " but got"  + email.getFrom());
    }
    
   
}
