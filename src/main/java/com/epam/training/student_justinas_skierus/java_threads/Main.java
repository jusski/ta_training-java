package com.epam.training.student_justinas_skierus.java_threads;

import lombok.val;

/**
 * Free cash desk. The fast-food restaurant has several checkouts. Visitors
 * stand in a queue at a specific cashier but can move to another queue when the
 * queue decreases or disappears there.
 *
 */
public class Main
{
	public static void main(String[] args) throws InterruptedException
	{

		for (int i = 0; i < 500; ++i)
		{
			val visitor = new Visitor();
			new Thread(visitor, "visitor " + i).start();
		}

	}
}
