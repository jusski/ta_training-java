package com.epam.training.student_justinas_skierus.java_threads;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;

public class Visitor implements Runnable
{
	public Cashier cashier;
	public volatile boolean isServed = false;

	private int magicNumber = 123; // NOTE magic number for repeated debug
	private Random random = new Random(magicNumber);

	public void enterFastFoodRestaurant() throws InterruptedException
	{
		while (true)
		{
			if(allCashiersHaveSameQueueLength())
			{
				int cashierIndex = random.nextInt(FastFoodRestaurant.getCashiers().size());
				cashier = FastFoodRestaurant.getCashiers().get(cashierIndex);
			}
			else
			{
				cashier = getCashierWithMinQueueLength();
			}
			try
			{
				FastFoodRestaurant.restaurantLock.lock();
				if (cashier.tryAdd(this)) break;
				FastFoodRestaurant.queueChanged.await();
			}
			finally
			{
				FastFoodRestaurant.restaurantLock.unlock();
			}
			Thread.sleep(100);
		}

	}

	private boolean allCashiersHaveSameQueueLength()
	{
		ArrayList<Cashier> cashiers = FastFoodRestaurant.getCashiers();
		int firstCashierQueueLength = cashiers.get(0).size();
		return cashiers.stream().allMatch(e -> e.size() == firstCashierQueueLength);
	}

	private Cashier getCashierWithMinQueueLength()
	{
		return FastFoodRestaurant.getCashiers().stream().min(Comparator.comparingInt(Cashier::size)).get();
	}

	@Override
	public void run()
	{
		try
		{
			enterFastFoodRestaurant();
			while (false == isServed )
			{
				Thread.sleep(100); 
                
				Cashier cashierWithMinQueueSize = getCashierWithMinQueueLength();
				if (cashier.indexOf(this) > cashierWithMinQueueSize.size())
				{
					try
					{
						cashier.writeLock.lock();
						if (cashierWithMinQueueSize.writeLock.tryLock()) // NOTE care for deadlock
						{
							if (cashier.indexOf(this) > cashierWithMinQueueSize.size())
							{
								cashier.remove(this);
								cashierWithMinQueueSize.add(this);
							}

							cashierWithMinQueueSize.writeLock.unlock();
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
