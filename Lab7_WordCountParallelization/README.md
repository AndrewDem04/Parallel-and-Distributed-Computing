# Lab7_WordCountParallelization

This project demonstrates two approaches to parallelizing the word count problem in Java using threads:

1. **ConcurrentMapWordCount**: Uses a shared `ConcurrentHashMap` for all threads, with explicit synchronization.
2. **LocalMapReductionWordCount**: Each thread builds its own local `HashMap`, and the results are merged after all threads complete.

## How It Works

- The input text file is read and split into words.
- The workload is divided among a specified number of threads.
- Each thread processes a chunk of the words and counts occurrences.
- The total word counts are printed, along with the time taken.

### Approaches

#### 1. ConcurrentMapWordCount

- All threads share a single `ConcurrentHashMap` to store word counts.
- Access to the map is synchronized to avoid race conditions.
- Suitable for demonstrating concurrent updates to a shared data structure.

#### 2. LocalMapReductionWordCount

- Each thread maintains its own local `HashMap` for counting.
- After all threads finish, the main thread merges the local maps into a global map.
- Reduces contention and synchronization overhead.

## Usage

Compile all Java files:

```sh
javac ConcurrentMapWordCount.java LocalMapReductionWordCount.java
```

Run either implementation with:

```sh
java ConcurrentMapWordCount <input_file> <num_threads>
java LocalMapReductionWordCount <input_file> <num_threads>
```

## Benchmarking

A Python script (`BenchMarkWordCount.py`) is included to automate performance testing. It runs each Java program multiple times, extracts the reported execution time, and computes the average. This helps you objectively compare the efficiency of both approaches.

**Note:**  
The benchmark script provides a fair, repeatable way to measure and compare the performance of both Java implementations. Adjust the `input_file` variable to point to your test file.