# Parallel Circuit Checker

This project implements a parallelized circuit satisfiability checker in Java. The program checks all possible input combinations for a given logical circuit and reports the combinations that satisfy the circuit constraints. The workload is distributed across multiple threads for improved performance.

## How It Works

- The program takes two command-line arguments:
    1. The number of input variables (`vector size`)
    2. The number of threads to use

- It generates all possible input combinations (from `0` to `2^size - 1`) and splits the work among the specified number of threads.

- Each thread checks its assigned range of combinations and records those that satisfy the circuit.

- The circuit logic is hardcoded in the `check_circuit` method.

## Usage

Compile the desired program:

```sh
javac P_C_C_WithGlobalList.java
javac P_C_C_WithLocalLists.java
```

Run the program with the desired vector size and number of threads:

```sh
java P_C_C_WithGlobalList <vector size> <Threads>
java P_C_C_WithLocalLists <vector size> <Threads>
```

Example:

```sh
java P_C_C_WithGlobalList 23 4
java P_C_C_WithLocalLists 23 4
```

## Output

- The program prints all satisfying input combinations.
- It also reports the total execution time in milliseconds.

## Files

- `P_C_C_WithGlobalList.java`: Implementation using a single global list (thread-safe) to collect results.
- `P_C_C_WithLocalLists.java`: Implementation using a local list per thread, merged at the end for results.

## Notes

- Ensure that the vector size is large enough to cover all indices used in the circuit logic (e.g., at least 23).
- Both implementations use the same circuit logic but differ in how results are collected and merged.

