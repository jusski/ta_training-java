package com.epam.training.student_justinas_skierus.java_io.optional_tasks;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Прочитать текст Java-программы и записать в другой файл в обратном порядке
 * символы каждой строки.
 *
 */
public class ReverseLines
{
	private static final String DEBUG_INPUT_FILE_PATH = "src/main/java/com/epam/training/student_justinas_skierus/java_io/optional_tasks/ReverseLines.java";
	private static final String DEBUG_OUTPUT_FILE_PATH = "ReversedFile.txt";
  
	public static void main(String[] args) throws IOException
	{
		String inputFilePath = args.length >= 1 ? args[0] : DEBUG_INPUT_FILE_PATH;
		String outputFilePath = args.length == 2 ? args[1] : DEBUG_OUTPUT_FILE_PATH;
		
		List<String> lines = Files.readAllLines(Paths.get(inputFilePath));
		List<String> reversedLinesList = lines.stream().map(ReverseLines::reverseString).toList();

		Files.write(Paths.get(outputFilePath), reversedLinesList, UTF_8);
		
		//Debug
		Files.copy(Paths.get(outputFilePath), System.out);
	}

	private static String reverseString(String string)
	{
		List<Integer> codePointslist = string.codePoints().boxed().toList();
		Collections.reverse(codePointslist);
		int[] codePointsArray = codePointslist.stream().mapToInt(Integer::intValue).toArray();
		String reversedString = new String(codePointsArray, 0, codePointsArray.length);

		return reversedString;
	}

}
