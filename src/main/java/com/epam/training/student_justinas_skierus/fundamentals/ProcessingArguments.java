package com.epam.training.student_justinas_skierus.fundamentals;

import java.math.BigInteger;

/**
 * Enter integers as command line arguments, calculate their sum (product) and
 * print the result to the console.
 *
 */
public class ProcessingArguments
{

	public static void main(String[] args)
	{
		if (args.length != 2)
		{
			System.err.println("Enter 2 integers as command line arguments.");
			System.exit(1);
		}

		try
		{
			BigInteger firstInteger = new BigInteger(args[0]);
			BigInteger secondInteger = new BigInteger(args[1]);

			BigInteger sum = firstInteger.add(secondInteger);
			BigInteger product = firstInteger.multiply(secondInteger);

			System.out.printf("sum = %s%n", sum);
			System.out.printf("product = %s%n", product);
		}
		catch (NumberFormatException e)
		{
			System.err.println("Not a number");
			e.printStackTrace();
		}

	}

}
