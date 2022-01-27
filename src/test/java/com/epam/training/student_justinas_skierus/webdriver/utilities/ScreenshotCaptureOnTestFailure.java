package com.epam.training.student_justinas_skierus.webdriver.utilities;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class ScreenshotCaptureOnTestFailure implements ITestListener
{
    public static WebDriver driver;

    @Override
    public void onTestFailure(ITestResult result)
    {
        String date = DateTimeFormatter.ofPattern("dd_hh_mm_ss").format(LocalDateTime.now());
        File screenshotFile = new File("C://temp//" + date + ".png");
        File tempFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try
        {
            Files.copy(tempFile.toPath(), screenshotFile.toPath());
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}