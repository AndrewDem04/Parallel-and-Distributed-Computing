# Parallel Circuit Checker

In this folder its implementing a parallelized circuit satisfiability checker in Java. The program checks all possible input combinations for a given logical circuit and reports the combinations that satisfy the circuit constraints. The workload is distributed across multiple threads for improved performance.

## How It Works

- The program takes two command-line arguments:
    1. The number of input variables (`vector size`)
    2. The number of threads to use

- It generates all possible input combinations (from `0` to `2^size - 1`) and splits the work among the specified number of threads.

- Each thread checks its assigned range of combinations and records those that satisfy the circuit.

- The circuit logic is hardcoded in the `check_circuit` method.

## Usage

Compile the program:

```sh
javac ParallelCircuitChecker.java
```

Run the program with the desired vector size and number of threads:

```sh
java ParallelCircuitChecker <vector size> <Threads>
```

Example:

```sh
java ParallelCircuitChecker 23 4
```

## Output

- The program prints all satisfying input combinations.
- It also reports the total execution time in milliseconds.

## Files

- `ParallelCircuitChecker.java`: Main program file containing all classes.

## Notes

- Ensure that the vector size is large enough to cover all indices used in the circuit logic (e.g., at least 23).
