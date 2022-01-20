package com.epam.training.student_justinas_skierus.java_io.optional_tasks;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Прочитать строки из файла и поменять местами первое и последнее слова в
 * каждой строке.
 *
 */
public class SwitchFirstAndLastWordInLine
{
	private static final String DEBUG_INPUT_FILE_PATH = "src/main/java/com/epam/training/student_justinas_skierus/java_io/optional_tasks/SwitchFirstAndLastWordInLine.java";
	private static final String DEBUG_OUTPUT_FILE_PATH = "ReversedFirstAndLastWords.txt";

	public static void main(String[] args) throws IOException
	{
		List<String> lines = Files.readAllLines(Path.of(DEBUG_INPUT_FILE_PATH));
		lines = lines.stream()
				.map(SwitchFirstAndLastWordInLine::switchFirstAndLastWordInLine)
				.peek(System.out::println)
				.toList();
        Files.write(Path.of(DEBUG_OUTPUT_FILE_PATH), lines, java.nio.charset.StandardCharsets.UTF_8);
        String importantString = "dont//commnet;\\\\\" delete me"; // comment
	}

	public static String switchFirstAndLastWordInLine(String line)
	{
		//Check if line contains 1 or 0 words
		if (line.matches("^\\W*?\\w+\\W*?$"))
		{
            return line;
		}

		int regexFlags = Pattern.UNICODE_CHARACTER_CLASS | Pattern.MULTILINE;
		return Pattern.compile("^(.*?)(\\w+)(.*)\\b(\\w+)(.*?)$", regexFlags) // Find first and last word in string as $1 and $4 group
				.matcher(line)
				.replaceFirst("$1$4$3$2$5"); // Spaces + LastWord + MiddleSentence + FirstWord + Spaces
	}

}
