# alxg2112 collections
Some collections implementations (including concurrent ones), made just for fun.

## Currently existing collections

### Thread safe
| Collection | Description |
| ------ | ------ |
| `AtomicRingBuffer` | Lock-free [ring buffer] implementation. Has decent performance-to-memory footpring ratio.|
| `BlockingRingBuffer` | Old school blocking implementation of [ring buffer]. Shows better performance than `ArrayBlockingQueue` and `LinkedBlockingQueue` when capacity is big enough. |
#### Benchmark
```
Benchmark{numberOfProducers=1, numberOfConsumers=1, elementsPerProducer=1000000, size=16}
LinkedBlockingQueue: 343ms
ArrayBlockingQueue: 369ms
BlockingRingBuffer: 331ms
AtomicRingBuffer: 125ms

Benchmark{numberOfProducers=1, numberOfConsumers=1, elementsPerProducer=1000000, size=4096}
LinkedBlockingQueue: 234ms
ArrayBlockingQueue: 110ms
BlockingRingBuffer: 125ms
AtomicRingBuffer: 37ms

Benchmark{numberOfProducers=4, numberOfConsumers=4, elementsPerProducer=1000000, size=16}
LinkedBlockingQueue: 1353ms
ArrayBlockingQueue: 5140ms
BlockingRingBuffer: 6110ms
AtomicRingBuffer: 870ms

Benchmark{numberOfProducers=4, numberOfConsumers=4, elementsPerProducer=1000000, size=4096}
LinkedBlockingQueue: 691ms
ArrayBlockingQueue: 375ms
BlockingRingBuffer: 375ms
AtomicRingBuffer: 360ms
```
   [ring buffer]: <https://en.wikipedia.org/wiki/Circular_buffer>
