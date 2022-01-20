package com.epam.training.student_justinas_skierus.java_io.optional_tasks;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Прочитать текст Java-программы и в каждом слове длиннее двух символов все
 * строчные символы заменить прописными.
 *
 */
public class ChangeLowerCaseToUpperCase
{
	private static final String DEBUG_INPUT_FILE_PATH = "src/main/java/com/epam/training/student_justinas_skierus/java_io/optional_tasks/ChangeLowerCaseToUpperCase.java";
    private static final String DEBUG_OUTPUT_FILE_PATH = "ChangedSomeLowerCaseToUpperCase.txt";
	
   
	public static void main(String[] args) throws IOException
	{
		String path = args.length == 1 ? args[0] : DEBUG_INPUT_FILE_PATH;

		// We are using codepoints, and not char array as to not check for high/low
		// surrogate pairs.
		int[] codePoints = null;
		codePoints = Files.readString(Paths.get(path)).codePoints().toArray();

		// NOTE(jusski) Java code can have \u1233 style symbols which are converted to
		// symbols before parsing. What we must do in this program about them? After
		// conversion it will not compile anyway so we could just pretend its not 1
		// symbol but 5? f.e \u12a2 would be \U12A2 which is wrong (should be lowercase
		// 1 symbol 'ኢ' )
		for (int i = 0; i < codePoints.length - 2; ++i)
		{
			int codepoint = codePoints[i];
			if (Character.isWhitespace(codepoint)) continue;

			if (isStartOfWordBoundary(codePoints, i))
			{
				for (; i < codePoints.length; ++i)
				{
					codepoint = codePoints[i];
					if (Character.isWhitespace(codePoints[i])) break;

					codePoints[i] = Character.toUpperCase(codepoint);
				}
			}
		}

		String output = new String(codePoints, 0, codePoints.length);
		System.out.println(output);

		String outputPath = args.length == 2 ? args[1] : DEBUG_OUTPUT_FILE_PATH;
		Files.writeString(Paths.get(outputPath), output, UTF_8);
	}

	private static boolean isStartOfWordBoundary(int[] codePoints, int i)
	{
		return !Character.isWhitespace(codePoints[i + 1]) && !Character.isWhitespace(codePoints[i + 2]);
	}

}
