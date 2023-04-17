import java.util.*;
import java.io.*;

public class Bratley
{
	// A member to keep track of the number of tasks and an ArrayList of the task objects
	int numTasks;
	ArrayList<Task> tasks;

	// Creating an object from a file that contains a set of tasks
	public Bratley(String filename) throws Exception
	{
		Scanner in = new Scanner(new File(filename));
		numTasks = in.nextInt();
		tasks = new ArrayList<>();

		for(int i = 0; i < numTasks; i++)
		{
			tasks.add(new Task(in.nextInt(), in.nextInt(), in.nextInt()));
		}
	}

	// Checks if there is a valid schedule, this is where the
	// implementation of the algorithm goes
	public boolean hasValidSchedule(Bratley brat)
	{
		return false;
	}

	// Will print a valid schedule if it exists
	public void printValidSchedule(Bratley brat)
	{
		System.out.println("This is the printValidSchedule method");
	}

	public static void main(String [] args) throws Exception
	{
		if (args.length < 1)
		{
			System.out.println("You're the worst");
			System.exit(0);
		}

		Bratley b = new Bratley(args[0]);
	}
}
