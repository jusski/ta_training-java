package com.epam.training.student_justinas_skierus.java_io;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class DirectoryTree
{
	private static PrintStream printStream = null;

	public static void printEntries(File entry, String prefix) throws IOException
	{
		List<File> files = Arrays.asList(entry.listFiles()).stream().filter(File::isFile).filter(e -> !e.isHidden())
				.sorted().collect(Collectors.toList());

		List<File> directories = Arrays.asList(entry.listFiles()).stream().filter(File::isDirectory)
				.filter(e -> !e.isHidden()).sorted().collect(Collectors.toList());

		if (directories.size() == 0)
		{
			printFiles(files, prefix + "   ");
		}
		else
		{
			printFiles(files, prefix + "  │");
		}

		printDirectories(directories, prefix + "  ");

	}

	public static void printFiles(List<File> files, String prefix) throws IOException
	{
		String fileNamePrefix = "    ";
		if (!files.isEmpty())
		{
			for (File file : files)
			{
				System.out.println(prefix + fileNamePrefix + file.getName());
				printStream.println(prefix + fileNamePrefix + file.getName());
			}
			System.out.println(prefix);
			printStream.println(prefix);
		}

	}

	public static void printDirectories(List<File> directories, String prefix) throws IOException
	{
		String directoryNamePrefix = "├───";
		Iterator<File> iterator = directories.iterator();
		if (iterator.hasNext())
		{
			for (int i = 0; i < directories.size() - 1; ++i)
			{
				File directory = iterator.next();
				System.out.println(prefix + directoryNamePrefix + directory.getName());
				printStream.println(prefix + directoryNamePrefix + directory.getName());
				printEntries(directory, prefix + "│" + " ");
			}
			File directory = iterator.next();
			printLastDirectory(directory, prefix);
		}
	}

	public static void printLastDirectory(File directory, String prefix) throws IOException
	{
		String directoryNamePrefix = "└───";
		System.out.println(prefix + directoryNamePrefix + directory.getName());
		printStream.println(prefix + directoryNamePrefix + directory.getName());
		printEntries(directory, prefix + "  ");
	}

	public static void main(String[] args)
	{
		if (args.length < 1)
		{
			String path = args.length == 1 ? args[0] : ".";
			File file = new File(path);
			if (file.isDirectory())
			{
				try
				{
					printStream = new PrintStream(new File("tree.txt"), StandardCharsets.UTF_16);
					printEntries(file, "");
				}
				catch (IOException e)
				{
					e.printStackTrace();
					System.exit(1);
				}
				finally
				{
					if (printStream != null)
					{
						printStream.flush();
						printStream.close();
					}
				}
			}
			else if (file.isFile())
			{
				ReverseDirectoryTree reverseDirectoryTree = new ReverseDirectoryTree();
				reverseDirectoryTree.printSummary(file);
			}
			else if (!file.exists())
			{
				System.err.printf("Invalid path %s.", file.toString());
				System.exit(1);
			}
			else
			{
				System.err.println("Invalid program argument.");
				System.exit(1);
			}
		}
		else
		{
			System.err.println("Too many parameters.");
		}
	}

}
