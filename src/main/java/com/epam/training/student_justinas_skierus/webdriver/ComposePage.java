package com.epam.training.student_justinas_skierus.webdriver;

public interface ComposePage extends Page
{
	Page sendEmail(String to, String subject, String textBody);
}
