package com.epam.training.student_justinas_skierus.webdriver.dummy;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.epam.training.student_justinas_skierus.webdriver.MailPage;

public class InvalidMailPage implements MailPage
{

    @Override
    public boolean isPageStateCorrect()
    {
        return false;
    }

    @Override
    public WebDriverWait sleep()
    {
        throw new NoSuchElementException("This is Invalid MailPage.");
    }

    @Override
    public String getFrom()
    {
        return "";
    }

    @Override
    public String getSubject()
    {
        return "";
    }

    @Override
    public String getBody()
    {
        return "";
    }

    @Override
    public boolean hasNext()
    {
        return false;
    }

    @Override
    public MailPage next()
    {
        return new InvalidMailPage();
    }

}
