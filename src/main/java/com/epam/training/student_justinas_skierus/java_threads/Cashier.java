package com.epam.training.student_justinas_skierus.java_threads;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock;

public class Cashier implements Runnable
{
	static final int MAX_QUEUE_LENGTH = 10;

	public List<Visitor> visitorQueue = new ArrayList<>(MAX_QUEUE_LENGTH);

	public final ReentrantReadWriteLock lock = new ReentrantReadWriteLock(true);

	public ReadLock readLock = lock.readLock();
	public WriteLock writeLock = lock.writeLock();

	public final Condition notEmpty = writeLock.newCondition();
	public final Condition notFull = writeLock.newCondition();

	public int size()
	{
		try
		{
			readLock.lock();
			return visitorQueue.size();
		}
		finally
		{
			readLock.unlock();
		}
	}

	public int indexOf(Visitor visitor)
	{
		try
		{
			readLock.lock();
			return visitorQueue.indexOf(visitor);
		}
		finally
		{
			readLock.unlock();
		}
	}

	public void add(Visitor visitor)
	{
		try
		{
			writeLock.lock();
			while (visitorQueue.size() > MAX_QUEUE_LENGTH)
			{
				notFull.awaitUninterruptibly();
			}
			visitorQueue.add(visitor);
			notEmpty.signal();
		}
		finally
		{
			writeLock.unlock();
		}
	}

	public boolean tryAdd(Visitor visitor)
	{
		boolean addToVisitorQueueSucceded = false;
		if (writeLock.tryLock())
		{
			if (visitorQueue.size() < MAX_QUEUE_LENGTH)
			{
				visitorQueue.add(visitor);
				notEmpty.signal();
				addToVisitorQueueSucceded = true;
			}
			writeLock.unlock();
		}

		return addToVisitorQueueSucceded;
	}

	public Visitor remove()
	{
		try
		{
			writeLock.lock();
			while (visitorQueue.isEmpty())
			{
				notEmpty.awaitUninterruptibly();
			}
			Visitor visitor = visitorQueue.remove(0);

			notFull.signal();
			FastFoodRestaurant.restaurantLock.lock();
			FastFoodRestaurant.queueChanged.signal();
			FastFoodRestaurant.restaurantLock.unlock();
			return visitor;
		}
		finally
		{
			writeLock.unlock();
		}

	}

	public boolean remove(Visitor visitor)
	{
		try
		{
			writeLock.lock();
			int index = visitorQueue.indexOf(visitor);
			if (index != -1)
			{
				visitorQueue.remove(index);

				notFull.signal();
				FastFoodRestaurant.restaurantLock.lock();
				FastFoodRestaurant.queueChanged.signal();
				FastFoodRestaurant.restaurantLock.unlock();
				return true;
			}
		}
		finally
		{
			writeLock.unlock();
		}

		return false;
	}

	@Override
	public void run()
	{
		while (true)
		{
			Visitor visitor = remove();
			try
			{
				Thread.sleep(100);
				visitor.isServed = true;
			}
			catch (InterruptedException e)
			{
				ExceptionUtils.rethrowUnchecked(e);
			}
		}

	}
}
