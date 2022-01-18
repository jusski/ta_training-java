package com.epam.training.student_justinas_skierus.java_io.optional_tasks;

import static java.nio.charset.StandardCharsets.UTF_8;

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
    private static final String DEBUG_OUTPUT_FILE_PATH = "RandomNumbers.txt";
    private static final int DEBUG_COUNT = 30;
	
    public static void main(String[] args) throws IOException
	{
		String path = args.length >= 1 ? args[0] : DEBUG_OUTPUT_FILE_PATH;
		int count = args.length == 2 ? Integer.valueOf(args[1]) : DEBUG_COUNT;

		writeRandomNumbersToFile(path, count);
		sortNumbersFromFileAndOutputToConsole(path);
	}

	private static void writeRandomNumbersToFile(String path, int count) throws IOException
	{
		String randomNumbers = new Random().ints(count)
				.boxed()
				.map(e -> e.toString())
				.collect(Collectors.joining("\n"));
		
		Files.writeString(Paths.get(path), randomNumbers, UTF_8);
	}
	
	private static void sortNumbersFromFileAndOutputToConsole(String path) throws IOException
	{
		List<String> linesOfRandomIntegers = Files.readAllLines(Paths.get(path));
		linesOfRandomIntegers.stream()
				.mapToInt(Integer::valueOf)
				.sorted()
				.forEach(System.out::println);
	}
}
