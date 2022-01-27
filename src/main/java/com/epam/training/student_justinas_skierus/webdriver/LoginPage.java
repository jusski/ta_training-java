package com.epam.training.student_justinas_skierus.webdriver;

public interface LoginPage extends Page
{
	Page authorize(String email,String password);
}