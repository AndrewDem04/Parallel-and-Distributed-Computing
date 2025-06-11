# Parallel Pi Integration

This project demonstrates different approaches to parallelizing the numerical integration for estimating the value of π (Pi) using the midpoint rectangle method in Java. Three implementations are provided:

- **ParallelPiIntegration**: Uses a shared object for accumulating the result.
- **ParallelPiIntegrationWithArray**: Uses an array for thread-local partial sums (no locking required).
- **ParallelPiIntegrationWithSynchronized**: Uses a synchronized method for thread-safe accumulation.

## How It Works

Each implementation divides the integration interval among multiple threads. Each thread computes a partial sum, and the results are combined to estimate π.

### Usage

Compile all Java files:

```sh
javac *.java
```

Run any implementation with:

```sh
java <ClassName> <number_of_steps> <number_of_threads>
```

Example:

```sh
java ParallelPiIntegration 10000000 4
```

## Performance Results

The following tables show the average execution time (in seconds) for different numbers of steps and threads.

### ParallelPiIntegration

| Steps       | Threads | Average Time (s) |
|-------------|---------|------------------|
| 1,000,000   |   1     | 0.0110           |
|             |   2     | 0.0134           |
|             |   4     | 0.0160           |
|             |   8     | 0.0234           |
| 10,000,000  |   1     | 0.0496           |
|             |   2     | 0.0310           |
|             |   4     | 0.0264           |
|             |   8     | 0.0262           |
| 100,000,000 |   1     | 0.3918           |
|             |   2     | 0.2122           |
|             |   4     | 0.1322           |
|             |   8     | 0.0830           |
| 1,000,000,000|  1     | 3.8772           |
|             |   2     | 2.0148           |
|             |   4     | 1.1652           |
|             |   8     | 0.6844           |

### ParallelPiIntegrationWithArray

| Steps       | Threads | Average Time (s) |
|-------------|---------|------------------|
| 1,000,000   |   1     | 0.0120           |
|             |   2     | 0.0120           |
|             |   4     | 0.0156           |
|             |   8     | 0.0210           |
| 10,000,000  |   1     | 0.0446           |
|             |   2     | 0.0306           |
|             |   4     | 0.0262           |
|             |   8     | 0.0256           |
| 100,000,000 |   1     | 0.3954           |
|             |   2     | 0.2226           |
|             |   4     | 0.1324           |
|             |   8     | 0.0800           |
| 1,000,000,000|  1     | 4.0608           |
|             |   2     | 2.2078           |
|             |   4     | 1.2360           |
|             |   8     | 0.7740           |

### ParallelPiIntegrationWithSynchronized

| Steps       | Threads | Average Time (s) |
|-------------|---------|------------------|
| 1,000,000   |   1     | 0.0128           |
|             |   2     | 0.0136           |
|             |   4     | 0.0174           |
|             |   8     | 0.0242           |
| 10,000,000  |   1     | 0.0514           |
|             |   2     | 0.0342           |
|             |   4     | 0.0286           |
|             |   8     | 0.0316           |
| 100,000,000 |   1     | 0.4070           |
|             |   2     | 0.2290           |
|             |   4     | 0.1414           |
|             |   8     | 0.0954           |
| 1,000,000,000|  1     | 4.6030           |
|             |   2     | 2.5590           |
|             |   4     | 1.6022           |
|             |   8     | 1.0352           |

## Benchmarking Script

A Python script is provided to automate running the Java programs and collecting timing results. It uses the `subprocess` and `re` modules to execute the Java classes and parse output.

Example usage:

```python
def run_java_program(num_steps, num_threads, runs=5):
    # ... see script in source code ...
```

## Notes

- For best performance, use a number of threads equal to the number of CPU cores.
- The array-based approach avoids synchronization overhead.
- The synchronized approach ensures thread safety but may be slower due to locking.
