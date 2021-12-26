package com.epam.training.student_justinas_skierus.java_io.optional_tasks;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.nio.file.StandardOpenOption.CREATE;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Collections;
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

	public static void main(String[] args) throws IOException
	{
		String path = args.length == 1 ? args[0] : "RandomNumbers.txt";
		
		writeRandomNumbersToFile(path);
		sortNumbersFromFileAndOutputToConsole(path);
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
