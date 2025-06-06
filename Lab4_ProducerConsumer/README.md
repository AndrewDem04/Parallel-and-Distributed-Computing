# Lab 4: Producer-Consumer Problem

This project demonstrates the Producer-Consumer problem, a classic example of a multi-process synchronization scenario in parallel and distributed computing.

## Overview

The Producer-Consumer problem involves two types of processes: producers, which generate data and place it into a buffer, and consumers, which remove data from the buffer for processing. Proper synchronization is required to ensure that producers do not add data into a full buffer and consumers do not remove data from an empty buffer.

## Features

- Implementation of producer and consumer threads/processes
- Shared buffer with synchronization mechanisms
- Use of semaphores/mutexes to prevent race conditions
- Configurable buffer size and number of producers/consumers

## Usage

- Modify the buffer size, number of producers, and consumers in the source code as needed.
- Run the program to observe the synchronization between producers and consumers.

## Learning Objectives

- Understand synchronization primitives (mutexes, semaphores)
- Learn about inter-process communication
- Practice implementing concurrent algorithms
