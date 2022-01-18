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

	public static void main(String[] args) 
	{
		String inputFilePath = args.length == 2 ? args[0] : "src/main/java/com/epam/training/student_justinas_skierus/java_io/optional_tasks/ReverseLines.java";
		String outputFilePath = args.length == 2 ? args[1] : "ReversedFile.txt";
		List<String> lines = null;
		List<String> reversedLinesList = null;
		try
		{
			lines = Files.readAllLines(Paths.get(inputFilePath));
			reversedLinesList = lines.stream().map(ReverseLines::reverseString).toList();
		}
		catch (IOException e)
		{
			File file = new File(inputFilePath);
			if(!file.exists() && !file.isFile())
			{
				System.err.println("Invalid java source code file path: " + inputFilePath);
			
			}
			if(!file.canRead())
			{
				System.err.println("Cant read java source code file(" + inputFilePath +"). Check file permissions.");
			}
			else
			{
				System.err.println("Unexpected program error while reading file: " + inputFilePath);
				e.printStackTrace();
			}
			System.exit(1);
		}
		
		try
		{
			Files.write(Paths.get(outputFilePath), reversedLinesList, UTF_8);
			
		}
		catch (IOException e)
		{
			File file = new File(outputFilePath);
			if(file.exists() && !file.canWrite())
			{
				System.err.println("File (" + outputFilePath + ") already exits and cant be overridden becouse of permissions.");
			}
			else
			{
				e.printStackTrace();
			}
			System.exit(1);
		}
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
