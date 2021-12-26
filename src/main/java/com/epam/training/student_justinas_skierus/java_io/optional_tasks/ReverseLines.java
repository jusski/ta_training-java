package com.epam.training.student_justinas_skierus.java_io.optional_tasks;

import static java.nio.charset.StandardCharsets.UTF_8;

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

	public static void main(String[] args) throws IOException
	{
		String inputFilePath = args.length == 2 ? args[0] : "pom.xml";
		String outputFilePath = args.length == 2 ? args[1] : "ReversedPom.txt";
		List<String> lines = Files.readAllLines(Paths.get(inputFilePath));
		
		List<String> reversedLinesList = lines.stream().map(ReverseLines::reverseString).toList();
		
		Files.write(Paths.get(outputFilePath), reversedLinesList, UTF_8);
	}
	
	private static String reverseString(String string)
	{
		List<Integer> codePointslist = string.codePoints().boxed().collect(Collectors.toList());
		Collections.reverse(codePointslist);
		int[] codePointsArray = codePointslist.stream().mapToInt(Integer::intValue).toArray();
		String reversedString = new String(codePointsArray, 0, codePointsArray.length);
		
		return reversedString;
	}

}
