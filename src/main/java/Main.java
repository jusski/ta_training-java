
import java.io.IOException;
import java.util.UUID;
import java.util.logging.Level;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverLogLevel;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;

import com.epam.training.student_justinas_skierus.webdriver.InboxPage;
import com.epam.training.student_justinas_skierus.webdriver.MailPage;
import com.epam.training.student_justinas_skierus.webdriver.fastmail.FastMailComposePage;
import com.epam.training.student_justinas_skierus.webdriver.sdfeu.SDFInboxPage;

public class Main
{
	public static void main(String[] args) throws IOException, InterruptedException
	{
		System.setProperty("webdriver.chrome.driver","C:\\Users\\R61\\Downloads\\chromedriver_win32\\chromedriver.exe");
		
		java.util.logging.Logger.getLogger("org.openqa.selenium").setLevel(Level.OFF);
		ChromeOptions options = new ChromeOptions();options.setLogLevel(ChromeDriverLogLevel.OFF);
		WebDriver driver = new ChromeDriver(options);
		driver.manage().window().setSize(new Dimension(900,800));

//			
//		String randomSubject = UUID.randomUUID().toString();
//		String randomText = UUID.randomUUID().toString();
//		
//		SDFInboxPage sdfInboxPage;
//		
//		FastMailLoginPage loginPage = new FastMailLoginPage(driver);
//		FastMailInboxPage inboxPage = new FastMailInboxPage(driver, loginPage);
//		FastMailComposePage composePage = new FastMailComposePage(driver, inboxPage);
//
//		composePage.get();
//		composePage.sendEmail("jusski@sdf-eu.org", randomSubject, randomText);
//		
//		SDFLoginPage sdfLoginPage = new SDFLoginPage(driver);
//		sdfInboxPage = new SDFInboxPage(driver, sdfLoginPage);
//		sdfInboxPage.get();
//		SDFMailPage mailPage = sdfInboxPage.searchForEmailsWithSubject(randomSubject);
		
//		System.out.println(mailPage.isPageStateCorrect());
//		System.out.println(mailPage.getSubject().contains(randomSubject));
//		
		
		 String randomSubject = UUID.randomUUID().toString();
		    String randomText = UUID.randomUUID().toString();
		    
		 FastMailComposePage composeMailPage = new FastMailComposePage(driver);
	        composeMailPage.get();
	        
	        InboxPage inboxPage = composeMailPage.sendEmail("jusski@sdf-eu.org", randomSubject, randomText);
	        Assert.assertTrue(inboxPage.isPageStateCorrect(), "Error in sending email.");
	        
	        SDFInboxPage inboxPage2 = new SDFInboxPage(driver);
	        inboxPage2.get();
	        
	        MailPage email = inboxPage2.searchForEmailsWithSubject(randomSubject);
	        Assert.assertTrue(email.isPageStateCorrect(), "Could not load email page.");
	        Assert.assertTrue(email.getSubject().contains(randomSubject), "Subject in email is wrong. Expected: " + 
	                                                                       randomSubject + " but got: " + email.getSubject());
	        Assert.assertTrue(email.getBody().contains(randomText), "Body in email is wrong. Expected: " + randomText + 
	                                                                " but got: " + email.getBody());
		System.out.println("end");
//		driver.close();
//		driver.quit();
		
	}
}