# Lab4_ProducerConsumer

This folder demonstrates four different implementations of the Producer-Consumer problem in Java, each using a different synchronization mechanism:

1. **BlockingQueue**
2. **Locks and Conditions**
3. **Semaphores**
4. **wait/notify**

## 1. BlockingQueue (`ProdConsBlockingQueue`)

Uses Java's built-in `ArrayBlockingQueue` to manage synchronization between multiple producers and consumers.

- Multiple producers and consumers are supported.
- The queue handles all synchronization internally.
- Producers put items into the queue; consumers take items out.
- Special value `-1` is used to signal consumers to stop.


## 2. Locks and Conditions (`ProdConsLock`)

Implements a bounded buffer using explicit `Lock` and `Condition` objects.

- Only one producer and one consumer.
- The buffer uses a lock to ensure mutual exclusion.
- `bufferFull` and `bufferEmpty` conditions manage waiting and signaling between threads.


## 3. Semaphores (`ProdConsSemaphores`)

Uses Java's `Semaphore` class to control access to the buffer.

- Only one producer and one consumer.
- Two semaphores: `bufferFull` (initialized to 0) and `bufferEmpty` (initialized to 1).
- Producer waits for an empty slot; consumer waits for a full slot.


## 4. wait/notify (`ProdConsWaitNotify`)

Uses Java's intrinsic monitor methods `wait()` and `notify()` for synchronization.

- Only one producer and one consumer.
- The buffer is protected by synchronized methods.
- Producer waits if the buffer is full; consumer waits if the buffer is empty.


## How to Run

Each implementation is a standalone Java class with a `main` method. Compile and run the desired class:

```sh
javac ProdConsBlockingQueue.java
java ProdConsBlockingQueue

javac ProdConsLock.java
java ProdConsLock

javac ProdConsSemaphores.java
java ProdConsSemaphores

javac ProdConsWaitNotify.java
java ProdConsWaitNotify
```

---

## Notes

- Adjust the number of producers, consumers, buffer size, and delays in the code as needed.
- Output will show the production and consumption of items, demonstrating synchronization.

---

## References

- [Java Concurrency Utilities](https://docs.oracle.com/javase/tutorial/essential/concurrency/)
- [Producer-Consumer Problem - Wikipedia](https://en.wikipedia.org/wiki/Producer%E2%80%93consumer_problem)
