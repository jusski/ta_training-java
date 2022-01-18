package com.epam.training.student_justinas_skierus.java_io.optional_tasks;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Из текста Java-программы удалить все виды комментариев.
 *
 */
public class RemoveComments
{
	static int charAt;
	static char[] chars;

	public static void main(String[] args) throws IOException
	{
		String path = args.length == 1 ? args[0] : "src/main/java/com/epam/training/student_justinas_skierus/java_io/optional_tasks/RemoveComments.java";
		chars = Files.readString(Paths.get(path)).toCharArray();

		checkForObfuscatedJava();
		for (charAt = 0; charAt < chars.length; ++charAt)
		{
			char c = chars[charAt];
			switch (c)
			{
				case '"':
				{
					if (false == (chars[charAt - 1] == '\'' || (chars[charAt - 1] == '\\' && chars[charAt - 2] == '\'')))
					{
						if (chars[charAt + 1] == '"' && chars[charAt + 2] == '"')
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
					if (chars[charAt + 1] == '/')
					{
						replaceSingleLineCommentWithSpaces();
					}
					else if (chars[charAt + 1] == '*')
					{
						replaceMultiLineCommentWithSpaces();
					}
					break;
				}

			}
		}
        System.out.println(new String(chars));
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
			   !(chars[charAt] == '*'  && chars[charAt + 1] == '/'))
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
			if (chars[charAt + 1] == '"' && chars[charAt + 2] == '"')
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
			int escapeSymbolCount = 0;
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
