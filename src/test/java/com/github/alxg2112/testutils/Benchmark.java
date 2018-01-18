package com.github.alxg2112.testutils;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

import com.github.alxg2112.collections.concurrent.ConcurrentBuffer;

/**
 * @author Alexander Gryshchenko
 */
public class Benchmark {

	private final int numberOfProducers;
	private final int numberOfConsumers;
	private final int elementsPerProducer;
	private final int size;

	public Benchmark(int numberOfProducers, int numberOfConsumers, int elementsPerProducer, int size) {
		this.numberOfProducers = numberOfProducers;
		this.numberOfConsumers = numberOfConsumers;
		this.elementsPerProducer = elementsPerProducer;
		this.size = size;
	}

	public long benchmark(ConcurrentBuffer<Object> buffer, String implementationName) throws ExecutionException, InterruptedException {
		System.out.printf("====================[Testing %s]====================%n" +
						"Consumers: %s%n" +
						"Producers: %s%n" +
						"Elements per producer: %s%n" +
						"Buffer size: %s%n%n",
				implementationName, numberOfConsumers, numberOfProducers, elementsPerProducer, size);
		CountDownLatch leftToConsumeLatch = new CountDownLatch(elementsPerProducer * numberOfProducers);
		ExecutorService executorService = Executors.newFixedThreadPool(numberOfProducers + numberOfConsumers);
		Runnable producer = () -> {
			for (int i = 0; i < elementsPerProducer; i++) {
				Object newElement = new Object();
				try {
					buffer.put(newElement);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		};
		Runnable consumer = () -> {
			while (!Thread.currentThread().isInterrupted()) {
				try {
					buffer.take();
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
				}
				leftToConsumeLatch.countDown();
			}
		};
		System.out.printf("Starting...%n%n");
		long start = System.currentTimeMillis();
		IntStream.range(0, numberOfProducers).forEach(num -> executorService.submit(producer));
		IntStream.range(0, numberOfConsumers).forEach(num -> executorService.submit(consumer));
		leftToConsumeLatch.await();
		executorService.shutdownNow();
		long timeElapsed = System.currentTimeMillis() - start;
		System.out.printf("Time elapsed to produce and consume %s elements is %s millis%n" +
						"======================================================================%n%n",
				elementsPerProducer * numberOfProducers,
				timeElapsed);
		return timeElapsed;
	}
}
