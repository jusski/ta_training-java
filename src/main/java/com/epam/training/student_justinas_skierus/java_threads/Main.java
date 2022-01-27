package com.epam.training.student_justinas_skierus.java_threads;

import java.util.ArrayList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

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
		new Thread(() -> 
		{
			ArrayList<Cashier> cashiers = FastFoodRestaurant.getCashiers();
			ReentrantLock lock = FastFoodRestaurant.restaurantLock;
			Condition condition = FastFoodRestaurant.queueChanged;
			while(true)
			{
				for(Cashier cashier : cashiers)
				{
					Debug.println("*".repeat(cashier.size()));
				}
				lock.lock();
				Debug.println("Visitors outside: " + lock.getWaitQueueLength(condition));
				lock.unlock();
				Debug.renderFrame();
		
				try
				{
					Thread.sleep(33);
				}
				catch (InterruptedException e)
				{
					e.printStackTrace();
				}
			}
		}).start();

		for (int i = 0; i < 200; ++i)
		{
			val visitor = new Visitor();
			new Thread(visitor, "visitor " + i).start();
		}

	}
}
