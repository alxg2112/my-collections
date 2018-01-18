package com.github.alxg2112.testutils;

import java.util.concurrent.BlockingQueue;

import com.github.alxg2112.collections.concurrent.ConcurrentBuffer;

/**
 * @author Alexander Gryshchenko
 */
public class ConcurrentBufferAdapter<E> implements ConcurrentBuffer<E> {

	private final BlockingQueue<E> adapteeQueue;

	public ConcurrentBufferAdapter(BlockingQueue<E> adapteeQueue) {
		this.adapteeQueue = adapteeQueue;
	}

	public void put(E element) throws InterruptedException {
		adapteeQueue.put(element);
	}

	public E take() throws InterruptedException {
		return adapteeQueue.take();
	}
}
