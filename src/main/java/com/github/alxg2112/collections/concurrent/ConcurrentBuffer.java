package com.github.alxg2112.collections.concurrent;

/**
 * A simple thread-safe data structure, that supports {@code put} and {@code take}
 * operations. Does not provide any guaranties regrading the order of inserted
 * elements consumption, unless otherwise specified.
 * <p>
 * {@link ConcurrentBuffer} does not allow {@code null} elements.
 * <p>
 * Intended to be used primarily as inter-thread exchange mechanism
 * (e.g. producer-consumer scenarios).
 *
 * @author Alexander Gryshchenko
 */
public interface ConcurrentBuffer<E> {

	/**
	 * Inserts the specified element into this queue, waiting if necessary
	 * for space to become available.
	 *
	 * @param element the element to add
	 * @throws InterruptedException if interrupted while waiting
	 * @throws NullPointerException if specified element is null
	 */
	void put(E element) throws InterruptedException;

	/**
	 * Retrieves and removes an element from this buffer, wait for one
	 * to become available, if necessary.
	 *
	 * @return the element that was previously put into the buffer
	 * @throws InterruptedException if interrupted while waiting
	 */
	E take() throws InterruptedException;
}
