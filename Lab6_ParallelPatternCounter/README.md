# Lab6_ParallelPatternCounter

This implements a parallel pattern matching algorithm in Java. It searches for occurrences of a given pattern in a text file using multiple threads to speed up the process.

## How It Works

- The program reads a text file and a pattern from the command line.
- It splits the search space among the specified number of threads.
- Each thread searches for the pattern in its assigned chunk of the text.
- The total number of matches and the execution time are reported.

## Usage

```sh
java ParallelPatternCounter <file name> <pattern> <threads>
```

- `<file name>`: Path to the input text file.
- `<pattern>`: The string pattern to search for.
- `<threads>`: Number of threads to use for parallel search.

## Example

```sh
java ParallelPatternCounter input.txt hello 4
```

This command searches for the pattern `hello` in `input.txt` using 4 threads.

## Notes

- The program prints the execution time in milliseconds.
- The code can be modified to print the positions of matches and the total number of matches by uncommenting the relevant lines.

## Files

- `ParallelPatternCounter.java`: Main program file containing the parallel pattern matching logic.

## Requirements

- Java 8 or higher
