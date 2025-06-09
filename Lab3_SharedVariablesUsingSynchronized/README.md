# Lab 3: SharedVariablesUsingSynchronized

This folder contains Java examples demonstrating different approaches to sharing variables between threads using synchronization. The code is organized into three subfolders, each illustrating a different method for managing shared state in a multithreaded environment.

## Folder Structure

- `WithArgs/`  
    Examples where shared data is passed as arguments to threads.

- `WithGlobal/`  
    Examples where shared data is managed using global (static) variables.

- `WithObj/`  
    Examples where shared data is encapsulated in objects and passed to threads.

---

## WithArgs

This folder contains two Java classes:

- **Args.java**  
    Demonstrates sharing an array between threads by passing it as an argument. Synchronization is used to ensure thread safety when incrementing array elements.

- **ArgsWhile.java**  
    Uses a shared counter and array, both synchronized, to distribute work among threads using a `while` loop.

## WithGlobal

This folder contains two Java classes:

- **Global.java**  
    Uses static variables to share an array among threads. Synchronization is achieved using a lock object.

- **GlobalWhile.java**  
    Similar to `Global.java`, but uses a shared counter and array with separate lock objects for improved concurrency.

## WithObj

This folder contains two Java classes:

- **Obj.java**  
    Encapsulates shared data in an object, which is passed to each thread. Synchronization is done on the object itself.

- **ObjWhile.java**  
    Similar to `Obj.java`, but uses a shared counter and array with synchronized methods to distribute work among threads.

---

## Key Concepts Demonstrated

- **Synchronization**: Ensuring that only one thread can access a critical section at a time to prevent race conditions.
- **Shared Data**: Different ways to share data between threads (arguments, global variables, objects).
- **Thread Coordination**: Using `join()` to wait for all threads to finish before checking results.

---

## How to Run

1. Navigate to the desired folder (`WithArgs`, `WithGlobal`, or `WithObj`).
2. Compile the Java files:
     ```sh
     javac *.java
     ```
3. Run the desired class:
     ```sh
     java Args
     # or
     java ArgsWhile
     # or
     java Global
     # or
     java GlobalWhile
     # or
     java Obj
     # or
     java ObjWhile
     ```

---

## Notes

- Each example prints the number of errors found after all threads have completed.
- The code demonstrates both `for`-loop and `while`-loop approaches to distributing work among threads.
- Synchronization is critical to ensure correct results when multiple threads modify shared data.
