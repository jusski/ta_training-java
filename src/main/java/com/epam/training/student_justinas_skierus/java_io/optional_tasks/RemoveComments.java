package com.epam.training.student_justinas_skierus.java_io.optional_tasks;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Из текста Java-программы удалить все виды комментариев.
 *
 */
public class RemoveComments
{
	private static final String DEBUG_INPUT_FILE_PATH = "src/main/java/com/epam/training/student_justinas_skierus/java_io/optional_tasks/RemoveComments.java";
	private static final String DEBUG_OUTPUT_FILE_PATH = "RemovedComments.txt";
	private static int charAt;
	private static char[] chars;

	String veryImportantString = "//\\/*//// dont delete me \\\\\"*/\\//";
	public static void main(String[] args) throws IOException 
	{
		String path = args.length == 1 ? args[0] : DEBUG_INPUT_FILE_PATH;
	    chars = Files.readString(Paths.get(path)).toCharArray();

		checkForObfuscatedJava();
		for (charAt = 0; charAt < chars.length; ++charAt)
		{
			char c = chars[charAt];
			switch (c)
			{
				case '"':
				{
					if (isStartOfString())
					{
						if (isStartOfMultilineString())
						{
							skipMultiLineStringUntilClosingQuotes();
						}
						else
						{
							skipStringUntilClosingQuotes();
						}
					}
					break;
				}
				case '/':
				{
					if (isStartOfSingleLineComment())
					{
						replaceSingleLineCommentWithSpaces();
					}
					else if (isStartOfMultiLineComment())
					{
						replaceMultiLineCommentWithSpaces();
					}
					break;
				}

			}
		}
		
        String output = new String(chars);
        Files.writeString(Path.of(DEBUG_OUTPUT_FILE_PATH), output, StandardCharsets.UTF_8);
        Files.copy(Path.of(DEBUG_OUTPUT_FILE_PATH), System.out);
	}

	private static boolean isStartOfMultiLineComment()
	{
		return chars[charAt + 1] == '*';
	}

	private static boolean isStartOfSingleLineComment()
	{
		return chars[charAt + 1] == '/';
	}

	private static boolean isStartOfMultilineString()
	{
		return chars[charAt + 1] == '"' && chars[charAt + 2] == '"';
	}

	private static boolean isStartOfString()
	{
		return !(chars[charAt - 1] == '\'' || (chars[charAt - 1] == '\\' && chars[charAt - 2] == '\''));
	}

	private static void checkForObfuscatedJava()
	{
		for (charAt = 0; charAt < chars.length - 1; ++charAt)
		{
			if (chars[charAt] == '\\' && chars[charAt + 1] == 'u')
			{
				if (!currentCharIsEscaped() && ((charAt + 3) < chars.length))
				{
					String hexNumberString = new String(chars, charAt, 4);
					int c = Integer.valueOf(hexNumberString, 16).intValue();
					
					if ((c == 10) || // new line feed
						(c == '\'') || 
						(c == '\\') || 
						(c == '/') || 
						(c == '"') || 
						(c == '*'))
					{
                        System.err.println("Java code obfuscation contest detected. Program will exit.");
                        System.exit(1);
					}

				}
			}
		}
	}

	private static void replaceMultiLineCommentWithSpaces()
	{
		while ((charAt < chars.length) && 
			   !(chars[charAt] == '*'  && isStartOfSingleLineComment()))
		{
			chars[charAt++] = ' ';
		}
		chars[charAt++] = ' ';
		chars[charAt] = ' ';
	}

	private static void replaceSingleLineCommentWithSpaces()
	{
		while ((charAt < chars.length) && 
			   (chars[charAt] != '\n') && 
			   (chars[charAt] != '\r' || chars[charAt + 1] != '\n'))
		{
			chars[charAt++] = ' ';
		}
	}

	private static void skipMultiLineStringUntilClosingQuotes()
	{
		while (++charAt < chars.length)
		{
			char c = chars[charAt];
			if (c != '"') continue;

			if (currentCharIsEscaped())
			{
				continue;
			}
			if (isStartOfMultilineString())
			{
				charAt += 2;
				break;
			}

		}

	}

	private static boolean currentCharIsEscaped()
	{
		boolean isEscaped = false;
		if (chars[charAt - 1] == '\\')
		{
			int reverseIndex = charAt - 1;
			int escapeSymbolCount = 1;
			while (chars[--reverseIndex] == '\\')
			{
				escapeSymbolCount += 1;
			}
			if ((escapeSymbolCount & 1) == 1)
			{
				isEscaped = true;
			}
		}

		return isEscaped;
	}

	private static void skipStringUntilClosingQuotes()
	{
		while (++charAt < chars.length)
		{
			char c = chars[charAt];
			if (c != '"') continue;

			if (!currentCharIsEscaped())
			{
				break;
			}
		}

	}

}
