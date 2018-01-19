# alxg2112 collections
Some collections implementations (including concurrent ones), made just for fun.

## Currently existing collections

### Thread safe
| Collection | Description |
| ------ | ------ |
| `AtomicRingBuffer` | Lock-free [ring buffer] implementation. Has decent performance-to-memory footprint ratio.|
| `BlockingRingBuffer` | Old school blocking implementation of [ring buffer]. Shows better performance than `ArrayBlockingQueue` and `LinkedBlockingQueue` when capacity is big enough. |
#### Benchmark
```
Benchmark{numberOfProducers=1, numberOfConsumers=1, elementsPerProducer=1,000,000, size=16}
LinkedBlockingQueue: BenchmarkResult{timeElapsed=434ms, transactionsPerSecond=2,304,000}
ArrayBlockingQueue: BenchmarkResult{timeElapsed=429ms, transactionsPerSecond=2,331,000}
BlockingRingBuffer: BenchmarkResult{timeElapsed=340ms, transactionsPerSecond=2,941,000}
AtomicRingBuffer: BenchmarkResult{timeElapsed=125ms, transactionsPerSecond=8,000,000}

Benchmark{numberOfProducers=1, numberOfConsumers=1, elementsPerProducer=1,000,000, size=4,096}
LinkedBlockingQueue: BenchmarkResult{timeElapsed=172ms, transactionsPerSecond=5,813,000}
ArrayBlockingQueue: BenchmarkResult{timeElapsed=125ms, transactionsPerSecond=8,000,000}
BlockingRingBuffer: BenchmarkResult{timeElapsed=125ms, transactionsPerSecond=8,000,000}
AtomicRingBuffer: BenchmarkResult{timeElapsed=31ms, transactionsPerSecond=32,258,000}

Benchmark{numberOfProducers=4, numberOfConsumers=4, elementsPerProducer=1,000,000, size=16}
LinkedBlockingQueue: BenchmarkResult{timeElapsed=1,563ms, transactionsPerSecond=2,559,000}
ArrayBlockingQueue: BenchmarkResult{timeElapsed=3,952ms, transactionsPerSecond=1,012,000}
BlockingRingBuffer: BenchmarkResult{timeElapsed=5,828ms, transactionsPerSecond=686,000}
AtomicRingBuffer: BenchmarkResult{timeElapsed=391ms, transactionsPerSecond=10,230,000}

Benchmark{numberOfProducers=4, numberOfConsumers=4, elementsPerProducer=1,000,000, size=4,096}
LinkedBlockingQueue: BenchmarkResult{timeElapsed=641ms, transactionsPerSecond=6,240,000}
ArrayBlockingQueue: BenchmarkResult{timeElapsed=344ms, transactionsPerSecond=11,627,000}
BlockingRingBuffer: BenchmarkResult{timeElapsed=350ms, transactionsPerSecond=11,428,000}
AtomicRingBuffer: BenchmarkResult{timeElapsed=401ms, transactionsPerSecond=9,975,000}
```
   [ring buffer]: <https://en.wikipedia.org/wiki/Circular_buffer>
