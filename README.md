# Bratley's scheduling algorithm
An implementation of Bratley's algorithm for task scheduling in real-time systems, in Java
## The algorithm
The algorithm uses backtracking to find the first feasible schedule, not necessarily the most efficient schedule as that is not the purpose of the algorithm. This algorithm does not use preemption and will not be able to find a valid schedule for a set of tasks that may have a valid schedule when preemption is used.
## Testing with custom schedules
1. Compile at the command line with `javac Bratley.java`
2. Run with syntax `java Bratley input.txt`
3. Replace `input.txt` with your text file containing your schedule, in the file format specified below
## Schedule file format
The input text file should have an integer describing the number of tasks on the first line and then describe each task on subsequent lines by having integers describing its arrival time, completion time and deadline separated by spaces.

For example, included input.txt looks like:
<pre>4
4 2 7
1 1 5
1 2 6
2 1 3</pre>

Which describes the following schedule:
<pre>     | J1 | J2 | J3 | J4 |
| ai | 4  | 1  | 1  | 2  |
| ci | 2  | 1  | 2  | 1  |
| di | 7  | 5  | 6  | 3  |</pre>
Extra information about the code itself can be found in comments in the `Bratley.java` file.

