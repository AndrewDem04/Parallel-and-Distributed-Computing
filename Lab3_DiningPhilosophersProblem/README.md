# Lab3_DiningPhilosophersProblem

This folder contains three different Java solutions (`Sol1`, `Sol2`, and `Sol3`) to the classic **Dining Philosophers Problem**, each demonstrating different approaches to avoid deadlock and starvation in concurrent systems.


## Problem Overview

The Dining Philosophers Problem is a classic synchronization problem involving philosophers sitting around a table, each needing two forks (shared resources) to eat. The challenge is to design a protocol that allows the philosophers to eat without causing deadlock (where no one can proceed) or starvation (where some philosophers never get to eat).


## Solution Summaries

### 1. `Sol1` - Alternating Fork Acquisition Order

- **Approach:**  
    Philosophers are assigned to pick up forks in alternating order: even-numbered philosophers pick up their left fork first, then right; odd-numbered philosophers do the opposite.
- **Deadlock Avoidance:**  
    This breaks the circular wait condition, significantly reducing the risk of deadlock, especially with an odd number of philosophers. However, with an even number, deadlock is still possible but less likely.
- **Implementation Details:**  
    - Uses `ReentrantLock` for each fork.
    - Each philosopher is a thread that alternates between thinking and eating.
    - Fork acquisition order is determined by philosopher index parity.


### 2. `Sol2` - Token-Based Solution (Global Token)

- **Approach:**  
    Introduces a single global token. Only the philosopher holding the token can attempt to pick up forks and eat. After eating, the token is passed to the next philosopher.
- **Deadlock Avoidance:**  
    Deadlock is completely avoided since only one philosopher can eat at a time.
- **Drawbacks:**  
    This solution can be slow and inefficient for a large number of philosophers, as it unnecessarily restricts concurrency.
- **Implementation Details:**  
    - Uses a static `tokenIndex` to track which philosopher has the token.
    - Philosophers check if they have the token before picking up forks.
    - After eating, the token is passed to the next philosopher in sequence.

---

### 3. `Sol3` - Token-Based Solution (Per-Fork Tokens with ConcurrentHashMap)

- **Approach:**  
    Uses a `ConcurrentHashMap` to manage tokens for each fork, allowing more than one philosopher to eat concurrently if they do not share forks.
- **Deadlock Avoidance:**  
    By controlling access to each fork via tokens, this solution allows for greater concurrency and avoids deadlock.
- **Implementation Details:**  
    - Each fork has a token indicating which philosopher can use it.
    - Philosophers can eat only if they hold tokens for both adjacent forks.
    - After eating, tokens are passed to the next eligible philosophers for each fork.
    - `ConcurrentHashMap` ensures thread-safe token management.

## How to Run

1. Compile the desired solution file (e.g., `Sol1.java`).
2. Run the compiled class:
     ```sh
     javac Sol1.java
     java Sol1
     ```
3. Observe the console output to see how philosophers alternate between thinking and eating.

---

## References

- [Dining Philosophers Problem - Wikipedia](https://en.wikipedia.org/wiki/Dining_philosophers_problem)
- Java Concurrency Utilities: `ReentrantLock`, `ConcurrentHashMap`
