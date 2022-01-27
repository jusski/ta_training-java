package com.epam.training.student_justinas_skierus.webdriver;

public interface MailPage extends Page
{
	String getFrom();
	String getSubject();
	String getBody();
	
	boolean hasNext();
	MailPage next();
}
