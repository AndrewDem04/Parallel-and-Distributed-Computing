# Lab 8: Recursive Fork/Join

This lab demonstrates the use of Java's Fork/Join framework to parallelize two classic problems:

1. **Parallel Merge Sort**  
2. **Parallel Calculation of π (Pi) using Numerical Integration**

---

## 1. ForkJoinMergeSort

### Overview

This program implements the Merge Sort algorithm using Java's Fork/Join framework to parallelize the sorting process. The array is recursively divided into subarrays, which are sorted in parallel and then merged.

### How it Works

- The main class initializes an integer array and prints it.
- A `ForkJoinPool` is created to manage parallel tasks.
- The sorting is performed by the `RecursiveMergeSortTask`, which extends `RecursiveAction`.
- The array is recursively split into halves. Each half is sorted in parallel using `invokeAll`.
- After sorting, the halves are merged back together.
- The sorted array is printed.

### Usage

No command-line arguments are required. Simply run the program to see the original and sorted arrays.

---

## 2. ForkJoinPiCalculator

### Overview

This program calculates the value of π (Pi) using numerical integration (the midpoint rectangle method) in parallel, leveraging the Fork/Join framework for improved performance on multicore systems.

### How it Works

- The program expects three command-line arguments:
    1. `numSteps`: Number of intervals for the integration (higher means more accurate).
    2. `numThreads`: Number of threads to use in the ForkJoinPool.
    3. `acceptableSize`: The minimum size of a task before it is split further.
- The calculation is performed by the `RecursivePiCalc` class, which extends `RecursiveTask<Double>`.
- The integration interval is recursively split into subtasks until the acceptable size is reached.
- Each subtask computes a partial sum, and the results are combined to produce the final value of π.
- The program prints the computed value, the difference from Java's `Math.PI`, and the computation time.
