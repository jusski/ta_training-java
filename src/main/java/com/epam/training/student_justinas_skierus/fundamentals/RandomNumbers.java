package com.epam.training.student_justinas_skierus.fundamentals;

import java.util.Random;
import java.util.Scanner;

/**
 * Print a given number of random numbers with and without a newline break.
 */
public class RandomNumbers
{

	public static void main(String[] args)
	{
		System.out.printf("Enter one positive integer[0-%d]: ", Integer.MAX_VALUE);
		System.out.flush();

		Scanner scanner = new Scanner(System.in);

		// NOTE For brevity's sake we will parse only first argument
		if (scanner.hasNextInt())
		{
			int number = scanner.nextInt();

			if (number < 0)
			{
				System.err.println("Integer must be positive.");
				System.exit(1);
			}

			Random random = new Random(); //
			for (int i = 0; i < number; ++i)
			{
				System.out.printf("%d ", random.nextInt());
			}
			System.out.println();
			for (int i = 0; i < number; ++i)
			{
				System.out.printf("%d%n", random.nextInt());
			}

		}
		else
		{
			System.err.println("Number is too big or it's not an integer.");
			System.exit(1);
		}

	}

}
