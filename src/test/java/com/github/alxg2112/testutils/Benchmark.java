package com.github.alxg2112.testutils;

import java.text.NumberFormat;
import java.util.Locale;
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

	private static final NumberFormat NUMBER_FORMAT = NumberFormat.getNumberInstance(Locale.US);

	private final int numberOfProducers;
	private final int numberOfConsumers;
	private final int elementsPerProducer;
	private final int size;

	public static class BenchmarkResult {

		private final long timeElapsed;
		private final long transactionsPerSecond;

		public BenchmarkResult(long timeElapsed, long transactionsPerSecond) {
			this.timeElapsed = timeElapsed;
			this.transactionsPerSecond = transactionsPerSecond;
		}

		public long getTimeElapsed() {
			return timeElapsed;
		}

		public long getTransactionsPerSecond() {
			return transactionsPerSecond;
		}

		@Override
		public String toString() {
			return "BenchmarkResult{" +
					"timeElapsed=" + NUMBER_FORMAT.format(timeElapsed) + "ms" +
					", transactionsPerSecond=" + NUMBER_FORMAT.format(transactionsPerSecond) +
					'}';
		}
	}

	public Benchmark(int numberOfProducers, int numberOfConsumers, int elementsPerProducer, int size) {
		this.numberOfProducers = numberOfProducers;
		this.numberOfConsumers = numberOfConsumers;
		this.elementsPerProducer = elementsPerProducer;
		this.size = size;
	}

	public BenchmarkResult benchmark(ConcurrentBuffer<Object> buffer) throws ExecutionException, InterruptedException {
		int numberOfElements = elementsPerProducer * numberOfProducers;
		CountDownLatch leftToConsumeLatch = new CountDownLatch(numberOfElements);
		int numberOfThreads = numberOfProducers + numberOfConsumers;
		ExecutorService executorService = Executors.newFixedThreadPool(numberOfThreads);
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
		long startTimestamp = System.currentTimeMillis();
		IntStream.range(0, numberOfProducers).forEach(num -> executorService.submit(producer));
		IntStream.range(0, numberOfConsumers).forEach(num -> executorService.submit(consumer));
		leftToConsumeLatch.await();
		long timeElapsed = System.currentTimeMillis() - startTimestamp;
		long transactionsPerSecond = numberOfElements / timeElapsed * 1000;
		executorService.shutdownNow();
		return new BenchmarkResult(timeElapsed, transactionsPerSecond);
	}

	@Override
	public String toString() {
		return "Benchmark{" +
				"numberOfProducers=" + NUMBER_FORMAT.format(numberOfProducers) +
				", numberOfConsumers=" + NUMBER_FORMAT.format(numberOfConsumers) +
				", elementsPerProducer=" + NUMBER_FORMAT.format(elementsPerProducer) +
				", size=" + NUMBER_FORMAT.format(size) +
				'}';
	}
}
