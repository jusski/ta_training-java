package com.epam.training.student_justinas_skierus.java_io;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.stream.Collectors;



public class ReverseDirectoryTree
{
	private List<String> fileNames = new ArrayList<>();
	private List<String> directoryNames = new ArrayList<>();
	private int directoriesWithFiles = 0;

	public void reversePrintEntries(ListIterator<String> lineIterator, String prefix)
	{
		reversePrintFiles(lineIterator, prefix + "   ");
		reversePrintFiles(lineIterator, prefix + "  │");
		reversePrintDirectories(lineIterator, prefix + "  ");
	}

	private void reversePrintDirectories(ListIterator<String> lineIterator, String prefix)
	{
		String directoryNamePrefix = prefix + "├───";

		while (lineIterator.hasNext())
		{
			String line = lineIterator.next();
			if (line.startsWith(directoryNamePrefix))
			{
				directoryNames.add(line.substring(directoryNamePrefix.length()));
				reversePrintEntries(lineIterator, prefix + "│" + " ");
			}
			else
			{
				lineIterator.previous();
				break;
			}
		}

		reversePrintLastDirectory(lineIterator, prefix);
	}

	private void reversePrintLastDirectory(ListIterator<String> lineIterator, String prefix)
	{
		String directoryNamePrefix = prefix + "└───";
		if (lineIterator.hasNext())
		{
			String line = lineIterator.next();
			if (line.startsWith(directoryNamePrefix))
			{
				directoryNames.add(line.substring(directoryNamePrefix.length()));
				reversePrintEntries(lineIterator, prefix + "  ");
			}
			else
			{
				lineIterator.previous();
			}
		}
	}

	private void reversePrintFiles(ListIterator<String> lineIterator, String prefix)
	{
		String fileNamePrefix = prefix + "    ";
		boolean foundFileEntry = false;
		while (lineIterator.hasNext())
		{
			String line = lineIterator.next();
			if (line.startsWith(fileNamePrefix))
			{
				fileNames.add(line.substring(fileNamePrefix.length()));
				foundFileEntry = true;
			}
			else
			{
				if (line.startsWith(prefix)) // empty line
				{
				}
				else
				{
					lineIterator.previous();
				}
				break;
			}
		}
		if (foundFileEntry)
		{
			directoriesWithFiles += 1;
		}
	}

	private void reverseEntriesFromTxtFile(File txtFile) throws IOException
	{
		List<String> allLines = Files.readAllLines(txtFile.toPath(), StandardCharsets.UTF_8);
		ListIterator<String> lineIterator = allLines.listIterator();
		reversePrintEntries(lineIterator, "");

		while (lineIterator.hasNext())
		{
			String line = lineIterator.next();
			if (!line.matches("^\\s*"))
			{
				System.err.println("Unexcpected line: " + line);
				System.err.println("Error parsing file format. Program will exit.");
				System.exit(1);
			}
		}

	}

	public void printSummary(File txtFile) throws IOException
	{
		reverseEntriesFromTxtFile(txtFile);
		System.out.println("Number of files: " + fileNames.size());
		System.out.println("Number of directories: " + directoryNames.size());
		System.out.printf("Average number of files in directories: %.1f%n",
				(float) fileNames.size() / directoriesWithFiles);

		Double averageFileNameLength = fileNames.stream().collect(Collectors.averagingInt(String::length));
		System.out.printf("Average file name length: %.1f%n", averageFileNameLength);

	}

	public static void main(String args[]) throws IOException
	{
		ReverseDirectoryTree reverseDirectoryTree = new ReverseDirectoryTree();
		reverseDirectoryTree.printSummary(new File("tree.txt"));
	}
}
