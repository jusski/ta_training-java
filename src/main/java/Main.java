
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
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

			

		driver.quit();
         
		
	}
}