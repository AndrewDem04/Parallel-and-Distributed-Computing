# Lab7_ParallelSieveVariants

This project demonstrates three parallel implementations of the Sieve of Eratosthenes algorithm in Java, each using a different strategy for distributing work among threads. It also includes a Python benchmarking script to compare their performance.

## Implementations

### 1. SieveStatic

- **Static block partitioning:** The range `[sqrt(N)+1, N]` is divided into contiguous blocks, one per thread.
- Each thread marks non-primes in its assigned block using the list of small primes found up to `sqrt(N)`.

### 2. SieveCyclic

- **Cyclic partitioning:** Each thread processes every `numThreads`-th number in `[sqrt(N)+1, N]`.
- This can help balance the workload if some blocks are denser with composites.

### 3. SieveDynamic

- **Dynamic task assignment:** Threads dynamically fetch the next available small prime and mark its multiples.
- Uses a lock to synchronize task assignment, improving load balancing.

## Usage

### Compiling

```sh
javac SieveStatic.java
javac SieveCyclic.java
javac SieveDynamic.java
```

### Running

```sh
java SieveStatic <size> <numThreads>
java SieveCyclic <size> <numThreads>
java SieveDynamic <size> <numThreads>
```

- `<size>`: Upper bound for primes (e.g., `10000000`)
- `<numThreads>`: Number of threads to use

### Example

```sh
java SieveStatic 10000000 4
```

## Benchmarking

A Python script is provided to automate benchmarking of all three implementations with various input sizes and thread counts.

### Requirements

- Python 3.x

### Running the Benchmark

```sh
python benchmark.py
```

The script will:
- Run each Java implementation for multiple sizes and thread counts
- Repeat each test several times
- Print average execution times

## Files

- `SieveStatic.java` — Static partitioning implementation
- `SieveCyclic.java` — Cyclic partitioning implementation
- `SieveDynamic.java` — Dynamic task assignment implementation
- `benchmark.py` — Python script for benchmarking

## Notes

- All implementations expect two command-line arguments: `<size>` and `<numThreads>`.
- For large values of `<size>`, ensure your system has enough memory.
- The output includes the number of primes found and the elapsed time in milliseconds.
