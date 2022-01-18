package com.epam.training.student_justinas_skierus.java_io;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Реализовать программу которая будет получать в качестве аргумента командной
 * строки путь к директории, например "D:/movies". Записать в текстовый файл
 * структуру папок и файлов в виде, похожем на выполнение программы tree /F.
 *
 * Если в качестве параметра в программу передается не путь к директории, а путь
 * к txt файлу по образцу выше - прочитать файл и вывести в консоль следующие
 * данные:
 * 
 * Количество папок 
 * Количество файлов 
 * Среднее количество файлов в папке 
 * Среднюю длинну названия файла
 *
 */
public class DirectoryTree
{
	private static PrintStream printStream = null;

	public static void printEntries(File entry, String prefix) throws IOException
	{
		List<File> files = Arrays.asList(entry.listFiles()).stream()
				.filter(File::isFile)
				.filter(e -> !e.isHidden()) // program tree doesn't show hidden files by default
				.sorted()
				.collect(Collectors.toList());

		List<File> directories = Arrays.asList(entry.listFiles()).stream()
				.filter(File::isDirectory)
				.filter(e -> !e.isHidden())
				.sorted()
				.collect(Collectors.toList());

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
		Iterator<File> iterator = directories.iterator(); //NOTE(jusski) List.get only works "ok" on RandomAccess Lists
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
					//NOTE(jusski) Should we use default charset of "console" or UTF-8?
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
				System.err.printf("Invalid path: %s.", file.toString());
				System.exit(1);
			}
			else
			{
				//NOTE(jusski) better safe then sorry (i am not sure this path can happen)
				System.err.println("Unexpected error. Program will exit.");
				System.exit(1);
			}
		}
		else
		{
			System.err.println("Too many parameters. Program accepts only 1 parameter: path to directory"
					         + "or path to *.txt file (which was created by this program)");
		}
	}

}
