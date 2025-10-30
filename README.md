# Profiling the Impact of Caching, Memory Accesses, and Choice of Data Structures

## Abstract
**Research Question:** How do caching, memory access patterns, and data structure choice influence program performance in Java?  
**Conclusion:** Caching and data structure design significantly affect runtime efficiency. Using non-volatile variables, accessing localized memory regions, and employing tree-based collections (`TreeSet`) dramatically reduce access times compared to volatile variables, random memory access, and linked data structures.  
**Tools:** Java, JVM, IntelliJ IDEA, JDK 17+  
**Skills:** Performance benchmarking, memory profiling, data structure analysis, experimental design, computational efficiency measurement  

---

## Project Overview
This project explores fundamental computer systems concepts — specifically the effects of caching, the memory hierarchy, and data structure design — through a series of controlled experiments implemented in Java.

Each task measures how hardware-level mechanisms (like caching and locality) and software-level choices (like `TreeSet` vs. `LinkedList`) influence execution time. The project demonstrates core competencies in **performance analysis**, **empirical benchmarking**, and **systems-level reasoning**, key skills for understanding Java’s memory model and performance behavior.

---

## Research Question
How do caching, memory access patterns, and data structure design affect execution time and performance efficiency in Java programs?

---

## Methodology

**Language:** Java  
**Environment:** IntelliJ IDEA / Command Line (JDK 17+)
**Execution Command:**
```bash
java Memory <size> <experiments> <seed>
````

### Tasks

#### Task 1: Impact of Caching (`volatile` vs. non-volatile)

Contrasted performance between loops using a **volatile** variable (forces main-memory access) and a regular variable (cached).
Measured total runtime for addition/subtraction operations across `<experiments>` iterations.

#### Task 2: Memory Access Patterns

Created an `Integer` array of size `<size>`, filled with random values using `<seed>`.
Compared access times for:

1. Elements in the first 10% (spatial locality)
2. Random elements in the last 10% (non-local access)
   Averaged results across multiple experiments.

#### Task 3: Data Structure Search Performance

Populated a `TreeSet` and a `LinkedList` with `[0, size)` integers.
Timed `.contains()` lookups for random elements across `<experiments>`, comparing average lookup times between structures.

---

## Core Analysis

| Task       | Measurement                                  | Key Observations                                                      |
| ---------- | -------------------------------------------- | --------------------------------------------------------------------- |
| **Task 1** | Volatile vs. non-volatile loops              | Non-volatile variables ran ~3× faster due to caching advantages.      |
| **Task 2** | Array access (first 10% vs. random last 10%) | Localized access was ~10× faster than random access.                  |
| **Task 3** | `TreeSet` vs. `LinkedList` lookup            | `TreeSet` (O(log n)) vastly outperformed `LinkedList` (O(n)) lookups. |

### Example Output

**Command:**

```bash
java cs250.hw2.Memory 25000000 20 42
```

**Task 1**

| Condition | Avg Time (s) | Avg Sum     |
| --------- | ------------ | ----------- |
| Regular   | 0.04676      | -12,500,000 |
| Volatile  | 0.16412      | -12,500,000 |

**Task 2**

| Metric                    | Avg Time (ns) |
| ------------------------- | ------------- |
| Known element (first 10%) | 15.27         |
| Random element (last 10%) | 146.75        |
| Sum                       | -6.23×10¹¹    |

**Task 3**

| Data Structure | Avg Lookup Time (ns) |
| -------------- | -------------------- |
| TreeSet        | 9,709.15             |
| LinkedList     | 99,872,813.60        |

---

## Conclusion

The experiments confirm that **caching and memory locality** are major determinants of performance.

* Volatile variables bypass CPU caches, increasing access latency.
* Sequential (localized) memory access benefits from cache prefetching.
* Tree-based data structures (`TreeSet`) outperform linked structures (`LinkedList`) due to logarithmic vs. linear search time.

This project highlights the tangible effects of **hardware-aware programming** and **data structure selection** on runtime performance.

---

## Potential Follow-Up Questions

* How does JVM garbage collection impact timing consistency?
* How would other data structures (`HashSet`, `ArrayList`) compare?
* What changes when using multithreading or concurrent collections?

---

## Limitations

* Results collected in a single hardware environment; performance may vary.
* JVM JIT optimization introduces runtime variability.
* Single-threaded only; cache coherence in parallel systems not explored.

---

## How to Run

1. **Compile and Run**

   ```bash
   javac Memory.java
   java Memory <size> <experiments> <seed>
   ```

2. **Example**

   ```bash
   java Memory 25000000 20 42
   ```

3. **Output**
   Console output displays timing summaries for all three tasks.

---

## Project Structure

```
memory-performance-profiler/
├── Memory.java                  # Main experiment driver
├── README.md                    # Documentation and summary
└── Writeup.pdf                  # Analysis report
```
