package com.github.alxg2112.collections.concurrent;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReferenceArray;

/**
 * Lock-free exchange data structure, that provides decent performance-to-memory footprint
 * ratio when the size of buffer is very small (e.g. 8, 16), excelling
 * {@link java.util.concurrent.LinkedBlockingQueue} and
 * {@link java.util.concurrent.ArrayBlockingQueue} in these cases.
 *
 * @author Alexander Gryshchenko
 */
public class AtomicRingBuffer<E> implements ConcurrentBuffer<E> {

	private final AtomicReferenceArray<E> buffer;

	private final AtomicInteger writePos = new AtomicInteger(0);
	private final AtomicInteger readPos = new AtomicInteger(0);

	public AtomicRingBuffer(int bufferSize) {
		this.buffer = new AtomicReferenceArray<>(bufferSize);
	}

	@Override
	public void put(E element) throws InterruptedException {
		if (element == null) {
			throw new NullPointerException();
		}
		int pos;
		do {
			if (Thread.currentThread().isInterrupted()) {
				throw new InterruptedException();
			}
			pos = writePos.getAndUpdate(x -> ++x % buffer.length());
		} while (!buffer.compareAndSet(pos, null, element));
	}

	@Override
	public E take() throws InterruptedException {
		E element;
		do {
			if (Thread.currentThread().isInterrupted()) {
				throw new InterruptedException();
			}
			int pos = readPos.getAndUpdate(x -> ++x % buffer.length());
			element = buffer.getAndSet(pos, null);
		} while (element == null);
		return element;
	}
}
