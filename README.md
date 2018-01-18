# alxg2112 collections
Some collections implementations (including concurrent ones), made just for fun.

## Currently existing collections

### Thread safe
| Collection | Description |
| ------ | ------ |
| `AtomicRingBuffer` | Lock-free [ring buffer] implementation. Has decent performance-to-memory footpring ratio.|
| `BlockingRingBuffer` | Old school blocking implementation of [ring buffer]. Shows better performance than `ArrayBlockingQueue` and `LinkedBlockingQueue` when capacity is big enough. |
#### Benchmark
##### 1 producer, 1 consumer, 1 000 000 elements per producer, capacity - 16 elements
```
====================[Testing LinkedBlockingQueue]====================
Consumers: 1
Producers: 1
Elements per producer: 1000000
Buffer size: 16

Starting...

Time elapsed to produce and consume 1000000 elements is 382 millis
======================================================================

====================[Testing ArrayBlockingQueue]====================
Consumers: 1
Producers: 1
Elements per producer: 1000000
Buffer size: 16

Starting...

Time elapsed to produce and consume 1000000 elements is 359 millis
======================================================================

====================[Testing BlockingRingBuffer]====================
Consumers: 1
Producers: 1
Elements per producer: 1000000
Buffer size: 16

Starting...

Time elapsed to produce and consume 1000000 elements is 370 millis
======================================================================

====================[Testing AtomicRingBuffer]====================
Consumers: 1
Producers: 1
Elements per producer: 1000000
Buffer size: 16

Starting...

Time elapsed to produce and consume 1000000 elements is 84 millis
======================================================================
```
##### 1 producer, 1 consumer, 1 000 000 elements per producer, capacity - 4096 elements
```
====================[Testing LinkedBlockingQueue]====================
Consumers: 1
Producers: 1
Elements per producer: 1000000
Buffer size: 4096

Starting...

Time elapsed to produce and consume 1000000 elements is 160 millis
======================================================================

====================[Testing ArrayBlockingQueue]====================
Consumers: 1
Producers: 1
Elements per producer: 1000000
Buffer size: 4096

Starting...

Time elapsed to produce and consume 1000000 elements is 118 millis
======================================================================

====================[Testing BlockingRingBuffer]====================
Consumers: 1
Producers: 1
Elements per producer: 1000000
Buffer size: 4096

Starting...

Time elapsed to produce and consume 1000000 elements is 126 millis
======================================================================

====================[Testing AtomicRingBuffer]====================
Consumers: 1
Producers: 1
Elements per producer: 1000000
Buffer size: 4096

Starting...

Time elapsed to produce and consume 1000000 elements is 79 millis
======================================================================
```
##### 4 producers, 4 consumers, 1 000 000 elements per producer, capacity - 16 elements
```
====================[Testing LinkedBlockingQueue]====================
Consumers: 4
Producers: 4
Elements per producer: 1000000
Buffer size: 16

Starting...

Time elapsed to produce and consume 4000000 elements is 1469 millis
======================================================================

====================[Testing ArrayBlockingQueue]====================
Consumers: 4
Producers: 4
Elements per producer: 1000000
Buffer size: 16

Starting...

Time elapsed to produce and consume 4000000 elements is 5095 millis
======================================================================

====================[Testing BlockingRingBuffer]====================
Consumers: 4
Producers: 4
Elements per producer: 1000000
Buffer size: 16

Starting...

Time elapsed to produce and consume 4000000 elements is 6021 millis
======================================================================

====================[Testing AtomicRingBuffer]====================
Consumers: 4
Producers: 4
Elements per producer: 1000000
Buffer size: 16

Starting...

Time elapsed to produce and consume 4000000 elements is 766 millis
======================================================================
```
##### 4 producers, 4 consumers, 1 000 000 elements per producer, capacity - 4096 elements
```
====================[Testing LinkedBlockingQueue]====================
Consumers: 4
Producers: 4
Elements per producer: 1000000
Buffer size: 4096

Starting...

Time elapsed to produce and consume 4000000 elements is 577 millis
======================================================================

====================[Testing ArrayBlockingQueue]====================
Consumers: 4
Producers: 4
Elements per producer: 1000000
Buffer size: 4096

Starting...

Time elapsed to produce and consume 4000000 elements is 363 millis
======================================================================

====================[Testing BlockingRingBuffer]====================
Consumers: 4
Producers: 4
Elements per producer: 1000000
Buffer size: 4096

Starting...

Time elapsed to produce and consume 4000000 elements is 327 millis
======================================================================

====================[Testing AtomicRingBuffer]====================
Consumers: 4
Producers: 4
Elements per producer: 1000000
Buffer size: 4096

Starting...

Time elapsed to produce and consume 4000000 elements is 369 millis
======================================================================


Process finished with exit code 0

```

   [ring buffer]: <https://en.wikipedia.org/wiki/Circular_buffer>
