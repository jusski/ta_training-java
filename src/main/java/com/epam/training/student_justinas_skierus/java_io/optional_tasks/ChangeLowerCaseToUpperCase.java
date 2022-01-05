package com.epam.training.student_justinas_skierus.java_io.optional_tasks;

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

	public static void main(String[] args) throws IOException
	{
		String path = args.length >= 1 ? args[0]
				: "src/main/java/com/epam/training/student_justinas_skierus/java_io/optional_tasks/ChangeLowerCaseToUpperCase.java";
		
		int[] codePoints = Files.readString(Paths.get(path)).codePoints().toArray();
		
		
		for(int i = 0; i < codePoints.length - 2; ++i)
		{
			int c = codePoints[i];
			if(Character.isWhitespace(c)) continue;
			
			if(!Character.isWhitespace(codePoints[i + 1]) && 
			   !Character.isWhitespace(codePoints[i + 2]))
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
       Files.writeString(Paths.get(outputPath), output, UTF_8);
	}
	
	
}
