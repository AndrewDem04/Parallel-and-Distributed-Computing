# Lab 3: Fine-Grained Locking

This folder demonstrates two approaches to concurrent counting using Java threads and fine-grained locking mechanisms. The goal is to illustrate the use of concurrent data structures and synchronization techniques to ensure thread safety and performance in a multi-threaded environment.


## UsingConcurrentHashMap

### Overview

This class demonstrates how to use a `ConcurrentHashMap` in combination with a `ReentrantLock` to manage concurrent access to shared data. Each thread retrieves a unique key using a locked counter and increments the corresponding value in the map.

### Key Components

- **SharedData**: Holds the shared `ConcurrentHashMap`, a counter, and a lock. Provides thread-safe methods for accessing and updating the counter and map.
- **CounterThread**: Each thread repeatedly fetches the next available key and increments the corresponding value in the map.
- **check_array**: Verifies that each value in the map has been incremented exactly once.

### Synchronization

- The counter is protected by a `ReentrantLock` to ensure that each thread gets a unique key.
- The `ConcurrentHashMap` allows safe concurrent updates, but the increment operation is not atomic, which may lead to race conditions if not handled carefully.


## UsingSynchronizedList

### Overview

This class demonstrates fine-grained locking by using a synchronized list (`Collections.synchronizedList`) and an array of lock objects, one for each element in the list. This allows multiple threads to update different elements of the array concurrently without blocking each other.

### Key Components

- **SharedArray**: Wraps a synchronized list and an array of lock objects. Provides methods to safely increment individual elements using per-element locks.
- **CounterThread**: Each thread performs a nested loop, incrementing each array element a number of times proportional to its index.
- **check_array**: Verifies that each element in the array has been incremented the expected number of times.

### Synchronization

- Each array element has its own lock object, allowing threads to increment different elements in parallel.
- The use of `Collections.synchronizedList` ensures thread-safe access to the underlying list structure.


## How to Run

1. Compile the Java files.
2. Run either `UsingConcurrentHashMap` or `UsingSynchronizedList` to observe the behavior of concurrent counting with different synchronization strategies.
3. The output will indicate whether the concurrent updates were performed correctly.
