package com.epam.training.student_justinas_skierus.java_io.optional_tasks;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * Создать и заполнить файл случайными целыми числами. Отсортировать содержимое
 * файла по возрастанию.
 *
 */
public class SortedRandomNumbersFile
{

	public static void main(String[] args) 
	{
		String path = args.length == 1 ? args[0] : "RandomNumbers.txt";
		
		try
		{
			writeRandomNumbersToFile(path);
		}
		catch (IOException e)
		{
			File file = new File(path);
			if(!file.exists() && !file.isFile())
			{
				System.err.println("Path is not file: " + path);
			
			}
			if(!file.canRead())
			{
				System.err.println("Can't read file(" + path +"). Check file permissions.");
			}
			else
			{
				System.err.println("Unexpected program error while writing to file: " + path);
				e.printStackTrace();
			}
			System.exit(1);
		}
		
		try
		{
			sortNumbersFromFileAndOutputToConsole(path);
		}
		catch (IOException e)
		{
			File file = new File(path);
			if(file.isDirectory())
			{
				System.err.println("File \"" + path +"\" was deleted and same name directory was created while working on it.");
			}
			if(!file.exists())
			{
				System.err.println("File \"" + path +"\" was deleted.");
			}
			if(!file.canRead())
			{
				System.err.println("Can't read file(" + path +"). Check file permissions.");
			}
			else
			{
				System.err.println("Unexpected program error while reading file: " + path);
				e.printStackTrace();
			}
			System.exit(1);
		}
	}
	
	private static void writeRandomNumbersToFile(String path) throws IOException
	{
		String randomNumbers = new Random().ints(30)
				.boxed()
				.map(e -> e.toString())
				.collect(Collectors.joining("\n"));
		
		Files.writeString(Paths.get(path), randomNumbers, UTF_8);
	}
	
	private static void sortNumbersFromFileAndOutputToConsole(String path) throws IOException
	{
		List<String> linesOfRandomIntegers = Files.readAllLines(Paths.get(path));
		List<Integer> sortedIntegersString = linesOfRandomIntegers.stream()
				.mapToInt(Integer::valueOf)
				.sorted().boxed().toList();
	    sortedIntegersString.forEach(System.out::println);
	}
}
