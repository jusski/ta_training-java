package com.epam.training.student_justinas_skierus.fundamentals;

import java.time.Month;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.Scanner;

/**
 * Enter a number from 1 to 12. Output to the console the name of the month
 * corresponding to the given date. Check in code the correctness of entered
 * number.
 *
 */
public class MonthOfYear
{

	public static void main(String[] args)
	{
		System.out.printf("Enter a number from 1 to 12: ");
		System.out.flush();

		Scanner scanner = new Scanner(System.in);
		
		// NOTE For brevity's sake we will parse only first argument
		if (scanner.hasNextInt())
		{
			int number = scanner.nextInt();
			if ((number < 1) || (number > 12))
			{
				System.err.println("Number must be from 1 to 12.");
				System.exit(1);
			}
			Locale locale = Locale.getDefault(Locale.Category.FORMAT);
			System.out.println(Month.of(number).getDisplayName(TextStyle.FULL_STANDALONE, locale));
		}
		else
		{
			System.err.println("Invalid data.");
			System.exit(1);
		}

		scanner.close();
	}

}
