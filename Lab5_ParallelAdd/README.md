# Lab5_ParallelAdd

This folder demonstrates parallel addition of vectors and matrices using Java threads. It contains two main classes:

- `VectorAddParallel`: Performs parallel addition of two vectors.
- `MatrixAddParallel`: Performs parallel addition of two matrices.

## VectorAddParallel

### Description

`VectorAddParallel` adds two vectors (`b` and `c`) and stores the result in vector `a`. The computation is divided among multiple threads for improved performance on multi-core systems.

`MatrixAddParallel` adds two matrices (`B` and `C`) and stores the result in matrix `A`. The addition is performed in parallel by distributing rows among multiple threads, enabling faster computation on systems with multiple processors.

## How to Run

1. Compile the Java files:
    ```sh
    javac VectorAddParallel.java MatrixAddParallel.java
    ```
2. Run the desired class:
    ```sh
    java VectorAddParallel
    ```
    or
    ```sh
    java MatrixAddParallel
    ```

## Notes

- Ensure that the input vectors or matrices are of the same size before running the programs.
- The number of threads can be adjusted in the source code to match your system's capabilities.
- Example input data and expected output are provided in the comments of each class.