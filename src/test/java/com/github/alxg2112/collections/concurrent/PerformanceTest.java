package com.github.alxg2112.collections.concurrent;

import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.LinkedBlockingQueue;

import com.github.alxg2112.testutils.Benchmark;
import com.github.alxg2112.testutils.ConcurrentBufferAdapter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

/**
 * @author Alexander Gryshchenko
 */
@RunWith(Parameterized.class)
public class PerformanceTest {

	private Benchmark benchmark;
	private ConcurrentBuffer<Object> adaptedLinkedBlockingQueue;
	private ConcurrentBuffer<Object> adaptedArrayBlockingQueue;
	private ConcurrentBuffer<Object> blockingRingBuffer;
	private ConcurrentBuffer<Object> atomicRingBuffer;

	@Parameterized.Parameters
	public static Collection primeNumbers() {
		return Arrays.asList(new Object[][]{
				{1, 1, 1_000_000, 16},
				{1, 1, 1_000_000, 4096},
				{4, 4, 1_000_000, 16},
				{4, 4, 1_000_000, 4096}
		});
	}

	public PerformanceTest(int numberOfProducers, int numberOfConsumers, int elementsPerProducer, int size) {
		this.benchmark = new Benchmark(numberOfProducers, numberOfConsumers, elementsPerProducer, size);
		this.adaptedArrayBlockingQueue = new ConcurrentBufferAdapter<>(new ArrayBlockingQueue<>(size));
		this.adaptedLinkedBlockingQueue = new ConcurrentBufferAdapter<>(new LinkedBlockingQueue<>(size));
		this.atomicRingBuffer = new AtomicRingBuffer<>(size);
		this.blockingRingBuffer = new BlockingRingBuffer<>(size);
	}

	@Test
	public void performanceTest() throws ExecutionException, InterruptedException {
		Benchmark.BenchmarkResult linkedBlockingQueueBenchmark = benchmark.benchmark(adaptedLinkedBlockingQueue);
		Benchmark.BenchmarkResult arrayBlockingQueueBenchmark = benchmark.benchmark(adaptedArrayBlockingQueue);
		Benchmark.BenchmarkResult blockingRingBufferBenchmark = benchmark.benchmark(blockingRingBuffer);
		Benchmark.BenchmarkResult atomicRingBufferBenchmark = benchmark.benchmark(atomicRingBuffer);
		System.out.printf(benchmark + "%n"
						+ "LinkedBlockingQueue: %s%n"
						+ "ArrayBlockingQueue: %s%n"
						+ "BlockingRingBuffer: %s%n"
						+ "AtomicRingBuffer: %s%n%n",
				linkedBlockingQueueBenchmark,
				arrayBlockingQueueBenchmark,
				blockingRingBufferBenchmark,
				atomicRingBufferBenchmark);
	}
}
