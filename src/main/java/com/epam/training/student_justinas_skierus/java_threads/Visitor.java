package com.epam.training.student_justinas_skierus.java_threads;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;

public class Visitor implements Runnable
{
	public Cashier cashier;
	public volatile boolean served = false;
	
	private int magicNumber = 123; // NOTE magic number for repeated debug
	private Random random = new Random(magicNumber); 

	public void enterFastFoodRestaurant() throws InterruptedException
	{
		while (true)
		{
			
			if(allCashiersHasSameQueueLength())
			{
				int cashierIndex = random.nextInt(FastFoodRestaurant.getCashiers().size());
				cashier = FastFoodRestaurant.getCashiers().get(cashierIndex);
			}
			else
			{
				cashier = getCashierWithMinQueueLength();
			}
			
			if (cashier.tryAdd(this)) break;
			Thread.sleep(100);
		}

	}

	private boolean allCashiersHasSameQueueLength()
	{
		ArrayList<Cashier> cashiers = FastFoodRestaurant.getCashiers();
		int firstCashierQueueLength = cashiers.get(0).getQueueLength();
		boolean allMatch = cashiers.stream().allMatch(e -> e.getQueueLength() == firstCashierQueueLength);
		return allMatch;
	}

	private Cashier getCashierWithMinQueueLength()
	{
		return FastFoodRestaurant.getCashiers().stream()
				.min(Comparator.comparingInt(Cashier::getQueueLength))
				.get();
	}

	public void run()
	{
		try
		{
			enterFastFoodRestaurant();
			while (served == false)
			{
				Thread.sleep(100);

				Cashier cashierWithMinlQueue = getCashierWithMinQueueLength();
				if (cashier.indexOf(this) > cashierWithMinlQueue.getQueueLength())
				{
					try
					{
						cashier.writeLock.lock();
						if (cashierWithMinlQueue.writeLock.tryLock())
						{
							if (cashier.indexOf(this) > cashierWithMinlQueue.getQueueLength()) cashier.remove(this);

							cashierWithMinlQueue.add(this);
							cashierWithMinlQueue.writeLock.unlock();
						}

					}
					finally
					{
						cashier.writeLock.unlock();
					}

				}
			}
			System.out.println("Happy fast food restaurant visitor number: " + Thread.currentThread().getName());
		}
		catch (InterruptedException e)
		{
			ExceptionUtils.rethrowUnchecked(e);
		}

	}
}
