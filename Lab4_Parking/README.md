## Lab4_Parking

This folder contains three different Java implementations of a parking lot simulation, each demonstrating a different synchronization mechanism for managing concurrent access to a limited number of parking spaces.

Each solution simulates multiple cars (threads) repeatedly arriving at and departing from a parking lot with limited capacity. The goal is to ensure that no more cars than the lot's capacity can park at the same time, and that cars wait appropriately when the lot is full or empty.


## 1. ParkLockCond

**File:** `ParkLockCond.java`

**Description:**
- Implements the parking lot using a `ReentrantLock` and two `Condition` variables (`bufferFull` and `bufferEmpty`).
- Cars (threads) call `arrive()` to attempt to park. If the lot is full, they wait on `bufferFull`.
- When a car departs, it signals `bufferFull` to notify waiting cars that a space is available.
- Similarly, if the lot is empty, departing cars wait on `bufferEmpty`.
- Demonstrates fine-grained control over thread signaling and waiting.


## 2. ParkMonitor

**File:** `ParkMonitor.java`

**Description:**
- Implements the parking lot using Java's intrinsic monitor methods: `synchronized`, `wait()`, and `notifyAll()`.
- The `arrive()` and `depart()` methods are synchronized, ensuring mutual exclusion.
- Cars wait if the lot is full (in `arrive()`) or empty (in `depart()`), and are notified when the state changes.
- This approach is simpler but less flexible than explicit locks and conditions.


## 3. ParkSemaphore

**File:** `ParkSemaphore.java`

**Description:**
- Implements the parking lot using `Semaphore` objects.
- Uses a counting semaphore (`bufferEmpty`) to represent available spaces and another (`bufferFull`) for occupied spaces.
- A mutex semaphore ensures mutual exclusion when updating the shared state.
- Cars acquire a permit before parking and release it upon departure, ensuring the lot's capacity is never exceeded.
- Demonstrates the use of semaphores for both resource counting and mutual exclusion.

## How to Run

1. Compile the desired Java file (e.g., `javac ParkLockCond.java`).
2. Run the program (e.g., `java ParkLockCond`).
3. Observe the console output showing car arrivals, parking, departures, and waiting behavior.
