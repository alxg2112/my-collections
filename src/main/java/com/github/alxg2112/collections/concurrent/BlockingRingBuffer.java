package com.github.alxg2112.collections.concurrent;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Blocking implementation of {@link ConcurrentBuffer}. Has a better performance
 * than {@link java.util.concurrent.ArrayBlockingQueue} and
 * {@link java.util.concurrent.LinkedBlockingQueue}.
 * <p>
 * Maintains the order in which elements are consumed ({@link #take()} retrieves
 * the element that has been in the buffer for the longest time).
 *
 * @author Alexander Gryshchenko
 */
public class BlockingRingBuffer<E> implements ConcurrentBuffer<E> {

	private final E[] buffer;
	private final Lock lock = new ReentrantLock();
	private final Condition notEmpty = lock.newCondition();
	private final Condition notFull = lock.newCondition();
	private int writePos;
	private int readPos;
	private int elementCount;

	@SuppressWarnings("unchecked")
	public BlockingRingBuffer(int bufferSize) {
		this.buffer = (E[]) new Object[bufferSize];
	}

	@Override
	public void put(E element) throws InterruptedException {
		lock.lock();
		try {
			while (elementCount == buffer.length) {
				notFull.await();
			}
			int pos = writePos;
			writePos = ++writePos % buffer.length;
			buffer[pos] = element;
			++elementCount;
			notEmpty.signalAll();
		} finally {
			lock.unlock();
		}
	}

	@Override
	public E take() throws InterruptedException {
		lock.lock();
		try {
			while (elementCount == 0) {
				notEmpty.await();
			}
			int pos = readPos;
			readPos = ++readPos % buffer.length;
			E element = buffer[pos];
			buffer[pos] = null;
			--elementCount;
			notFull.signalAll();
			return element;
		} finally {
			lock.unlock();
		}
	}
}
