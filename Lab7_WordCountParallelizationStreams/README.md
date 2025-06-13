# Lab 7: WordCountParallelizationStreams

This lab explores parallel word counting in Java using streams, demonstrating two approaches for efficient text processing on large files. The goal is to compare performance and implementation style between reading all lines at once and streaming lines from a file.

## Overview

You will implement and benchmark two Java programs:

- **ParallelStreamWordCountList**: Loads all lines into memory, then processes them in parallel.
- **ParallelStreamWordCountBuffered**: Streams lines from the file and processes them in parallel.

A Python script is provided to automate benchmarking and compare average execution times.

---

## Java Implementations

### ParallelStreamWordCountList

Reads the entire file into a list, then uses a parallel stream to count word occurrences.

### ParallelStreamWordCountBuffered

Streams lines from the file using a buffered reader, then processes them in parallel.

## Benchmarking Script

A Python script (`benchmark.py`) is included to automate performance testing. It runs each Java program multiple times, extracts the reported execution time, and computes the average. This helps you objectively compare the efficiency of both approaches.

**Note:**  
The benchmark script provides a fair, repeatable way to measure and compare the performance of both Java implementations. Adjust the `input_file` variable to point to your test file.

