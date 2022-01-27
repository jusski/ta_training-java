package com.epam.training.student_justinas_skierus.webdriver.utilities;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryAnalyzerOnTestFailure implements IRetryAnalyzer
    {
        int counter = 0;
        int retryLimit = 4;

        @Override
        public boolean retry(ITestResult result)
        {
            if (counter < retryLimit)
            {
                counter += 1;
                return true;
            }
            return false;
        }
    }