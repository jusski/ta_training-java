package com.epam.training.student_justinas_skierus.fundamentals;

import java.util.Scanner;

/**
 * Greet any user when entering their name through the command line.
 */
public class UserGreeting
{

	public static void main(String[] args)
	{
		System.out.printf("Enter your name: ");
		System.out.flush();
		
		Scanner scanner = new Scanner(System.in);

		// NOTE For brevity's sake we will parse only first line.
		if (scanner.hasNextLine())
		{
			String name = scanner.nextLine(); 
			
			boolean containsInvalidCharacters = name.codePoints().anyMatch(Character::isISOControl);
			if(containsInvalidCharacters)
			{
				System.err.println("Name should not contain control characters.");
				System.exit(1);
			}
			
			System.out.printf("Hello %s%n", name);
		}
	}

}
