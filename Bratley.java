// INSTRUCTIONS
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
		boolean isValid = false;
		ArrayList<Task> tasks = new ArrayList<Task>(b.tasks);
		b.schedule = new ArrayDeque<Integer>();
		return hasValidSchedule(b.tasks, isValid, b.schedule, curTime);
	}

	private static boolean hasValidSchedule(ArrayList<Task> tasks, boolean isValid, Deque<Integer> schedule, int curTime)
	{
		if (isValid)
		{
			return true;
		}
		ArrayList<Task> ogTasks = new ArrayList<Task>(tasks);
		for (Task t : ogTasks)
		{
			if (t.arrivalTime > curTime)
			{
				curTime += t.arrivalTime - curTime;
			}

			curTime += t.completionTime;

			if (curTime <= t.deadline)
			{
				if (tasks.size() == 1)
				{
					return true;
				}
				else
				{
					schedule.push(t.taskNum);
					tasks.remove(t);
					return hasValidSchedule(tasks, isValid, schedule, curTime);
				}
			}

		}
		return false;
	}

	// Will print a valid schedule if it exists
	// 
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
			System.out.println(b.schedule);
		}
	}

	public static void main(String [] args) throws Exception
	{
		if (args.length < 1)
		{
			System.out.println("You're the worst");
			System.exit(0);
		}

		Bratley b = new Bratley(args[0]);
		System.out.println(hasValidSchedule(b));
		printValidSchedule(b);
	}
}
