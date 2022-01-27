package com.epam.training.student_justinas_skierus.webdriver.dummy;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.epam.training.student_justinas_skierus.webdriver.Page;

public class InvalidPage implements Page
{

    @Override
    public boolean isPageStateCorrect()
    {
        return false;
    }

    @Override
    public WebDriverWait sleep()
    {
        throw new NoSuchElementException("This is InvalidPage.");
    }

}
