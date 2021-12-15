package com.epam.training.student_justinas_skierus.fundamentals;

/**
 * Display command line arguments in reverse order in a console window.
 */
public class ReverseOrder
{

	public static void main(String[] args)
	{
		// NOTE This program is broken. There is no sane way to pass command line
		// arguments to java console application if they are not in ASCII encoding, and
		// even then you can't for example send control sequences(windows cmd ☺☻♥♦♣♠•)
		// and get them back.

		if (args.length > 0)
		{
			for (int i = args.length - 1; i != -1; --i)
			{
				System.out.printf("%s ", args[i]);
			}
			System.out.flush();
		}

	}

}
