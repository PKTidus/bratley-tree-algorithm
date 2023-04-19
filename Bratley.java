// INSTRUCTIONS
// Pass in input text file as first command line argument
// Run at command line with syntax
// java Bratley [filename]
// Ex: java Bratley input.txt
//
// Input file format is the number of tasks on the first line followed by the tasks
// on subsequent lines in the format: arrivalTime completionTime deadline
// Example input.txt is included which contains the following set of tasks:
//
//       +---+---+---+---+
//       | J1| J2| J3| J4|
//   +---+---+---+---+---+
//   | ai|	4|	1|	1|	2|
//   +---+---+---+---+---+
//   | ci|	2|	1|	2|	1|
//   +---+---+---+---+---+
//   | di|	7|	5|	6|	3|
//   +---+---+---+---+---+
//
// In the file format used this looks like:
//
// 		4
// 		4 2 7
// 		1 1 5
// 		1 2 6
// 		2 1 3
//
// Included input.txt has a feasible schedule of 2->4->3->1
// Included input2.txt has a feasible schedule of 3->2->4->1
// Included input3.txt does not have a feasible schedule
// Included input4.txt does not have a feasible schedule
// Included input5.txt has a feasible schedule of 2->4->3->1

import java.util.*;
import java.io.*;

public class Bratley
{
	// A member to keep track of the number of tasks and an ArrayList of the task objects
	int numTasks;
	ArrayList<Task> tasks;
	Deque<Integer> schedule;

	// Creating an object from a file that contains a set of tasks
	public Bratley(String filename) throws Exception
	{
		Scanner in = new Scanner(new File(filename));
		numTasks = in.nextInt();
		tasks = new ArrayList<>();

		for(int i = 0; i < numTasks; i++)
		{
			tasks.add(new Task(in.nextInt(), in.nextInt(), in.nextInt(), i+1));
		}
	}

	// Wrapper method creates all relevant variables and then calls the private method
	public static boolean hasValidSchedule(Bratley b)
	{
		int curTime = 0;
		boolean [] used = new boolean[b.numTasks+1];
		ArrayList<Task> tasks = new ArrayList<Task>(b.tasks);
		b.schedule = new ArrayDeque<Integer>();
		return hasValidSchedule(b.tasks, b.schedule, curTime, b.numTasks, used);
	}

	private static boolean hasValidSchedule(ArrayList<Task> tasks, Deque<Integer> schedule,
											int curTime, int numTasks, boolean [] used)
	{
		// Hooray! The schedule is valid when our schedule contains all of our tasks
		if (schedule.size() == numTasks)
		{
			return true;
		}
		// Try all possible moves
		for (int i = 0; i < numTasks; i++)
		{
			// Temporary variables for easy access later
			Task curTask = tasks.get(i);
			int tempTime = curTime;
			// Check if move is legal
			if (!isLegalMove(curTask, curTime, used))
			{
				// If it's not legal, skip to the next iteration of the loop.
				continue;
			}
			else
			{
				// If the move is legal, make calculations, set the task as used and push it to the stack
				if (curTime < curTask.arrivalTime)
				{
					curTime = curTask.arrivalTime;
				}
				curTime += curTask.completionTime;
				used[curTask.taskNum] = true;
				schedule.push(curTask.taskNum);
			}

			// Perform recursive descent
			if (hasValidSchedule(tasks, schedule, curTime, numTasks, used))
			{
				return true;
			}
			// Otherwise we need to revert those changes.
			schedule.pop();
			used[curTask.taskNum] = false;
			curTime = tempTime;
		}
		return false;
	}

	public static boolean isLegalMove(Task t, int curTime, boolean [] used)
	{
		if (used[t.taskNum])
		{
			return false;
		}
		if (t.arrivalTime > curTime)
		{
			curTime += t.arrivalTime;
		}
		curTime += t.completionTime;
		if (curTime > t.deadline)
		{
			return false;
		}
		else
		{
			return true;
		}
	}

	// Will print a valid schedule if it exists
	public static void printValidSchedule(Bratley b)
	{
		if (b.schedule == null)
		{
			hasValidSchedule(b);
			printValidSchedule(b);
		}
		else if (b.schedule.isEmpty())
		{
			System.out.println("A valid schedule does not exist!");
		}
		else
		{
			System.out.println("A feasible schedule for the given tasks is:");
			Iterator<Integer> it = b.schedule.descendingIterator();
			while(it.hasNext())
			{
				System.out.print(it.next());
				if (it.hasNext())
				{
					System.out.print("->");
				}
				else
				{
					System.out.println();
				}
			}
		}
	}

	public static void main(String [] args) throws Exception
	{
		if (args.length < 1)
		{
			System.out.println("You're the worst");
			System.exit(1);
		}

		Bratley b = new Bratley(args[0]);
		System.out.println(hasValidSchedule(b));
		printValidSchedule(b);
	}
}
