package com.epam.training.student_justinas_skierus.webdriver;

public interface LoginPage extends Page
{
	Page authenticate(String email,String password);
}