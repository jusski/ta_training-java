package com.epam.training.student_justinas_skierus.java_io.optional_tasks;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * Прочитать текст Java-программы и в каждом слове длиннее двух символов все
 * строчные символы заменить прописными.
 *
 */
public class ChangeLowerCaseToUpperCase
{
	// NOTE(jusski) Java code can have \u1233 style symbols which are converted to
	// symbols before parsing. What we must do in this program about them? After
	// conversion it will not compile anyway so we could just pretend its not 1
	// symbol but 5?
	public static void main(String[] args) 
	{
		if(args.length > 1)
		{
			System.err.println("Program accepts 1 argument of type \"Java source code\"");
			System.exit(1);
		}
		
		String path = args.length == 1 ? args[0]
				: "src/main/java/com/epam/training/student_justinas_skierus/java_io/optional_tasks/ChangeLowerCaseToUpperCase.java";

		// We are using codepoints, and not char array as to not check for high/low
		// surrogate pairs.
		int[] codePoints = null;
		try
		{
			codePoints = Files.readString(Paths.get(path)).codePoints().toArray();
		}
		catch (IOException e)
		{
			File file = new File(path);
			if (!file.exists() && !file.isFile())
			{
				System.err.println("Invalid java source code file path: " + path);

			}
			if (!file.canRead())
			{
				System.err.println("Cant read java source code file(" + path + "). Check file permissions.");
			}
			else
			{
				System.err.println("Unexpected program error while reading file: " + path);
				e.printStackTrace();
			}
			System.exit(1);
		}

		for (int i = 0; i < codePoints.length - 2; ++i)
		{
			int c = codePoints[i];
			if (Character.isWhitespace(c)) continue;

			if (!Character.isWhitespace(codePoints[i + 1]) && !Character.isWhitespace(codePoints[i + 2]))
			{
				for (; i < codePoints.length; ++i)
				{
					c = codePoints[i];
					if (Character.isWhitespace(codePoints[i])) break;

					codePoints[i] = Character.toUpperCase(c);
				}
			}

		}

		String output = new String(codePoints, 0, codePoints.length);
		System.out.println(output);

		String outputPath = args.length == 2 ? args[1] : "ChangedLeterCases.txt";
		try
		{
			Files.writeString(Paths.get(outputPath), output, UTF_8);
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
	
	
}
