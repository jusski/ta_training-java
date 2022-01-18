package com.epam.training.student_justinas_skierus.java_io;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import lombok.Cleanup;

/**
 * Реализовать программу которая будет получать в качестве аргумента командной
 * строки путь к директории, например "D:/movies". Записать в текстовый файл
 * структуру папок и файлов в виде, похожем на выполнение программы tree /F.
 *
 * Если в качестве параметра в программу передается не путь к директории, а путь
 * к txt файлу по образцу выше - прочитать файл и вывести в консоль следующие
 * данные:
 * 
 * Количество папок 
 * Количество файлов 
 * Среднее количество файлов в папке 
 * Среднюю длинну названия файла
 *
 */
public class DirectoryTree
{
	private static List<File> filterEntries(File entry, Predicate<File> predicate)
	{
		return Arrays.asList(entry.listFiles()).stream()
				.filter(predicate)
				.filter(e -> !e.isHidden())
				.sorted()
				.collect(Collectors.toCollection(ArrayList::new));
	}

	public static void printEntries(File entry, String prefix, PrintStream printStream) throws IOException 
	{
		List<File> files = filterEntries(entry, File::isFile);
		List<File> directories = filterEntries(entry, File::isDirectory);

		if (directories.size() == 0)
		{
			printFiles(files, prefix + "   ", printStream);
		}
		else
		{
			printFiles(files, prefix + "  │", printStream);
		}

		printDirectories(directories, prefix + "  ", printStream);
	}

	public static void printFiles(List<File> files, String prefix, PrintStream printStream) throws IOException
	{
		String fileNamePrefix = prefix + "    ";
		if (!files.isEmpty())
		{
			files.stream().forEachOrdered(file -> printStream.println(fileNamePrefix + file.getName()));
			printStream.println(prefix);
		}
	}

	public static void printDirectories(List<File> directories, String prefix, PrintStream printStream) throws IOException
	{
		String directoryNamePrefix = prefix + "├───";
		if (!directories.isEmpty())
		{
			int LastElementIndex = directories.size() - 1;
			for (int i = 0; i < LastElementIndex; ++i)
			{
				File directory = directories.get(i);
				printStream.println(directoryNamePrefix + directory.getName());
				printEntries(directory, prefix + "│" + " ", printStream);
			}
			File directory = directories.get(LastElementIndex);
			printLastDirectory(directory, prefix, printStream);
		}
	}

	public static void printLastDirectory(File directory, String prefix, PrintStream printStream) throws IOException
	{
		String directoryNamePrefix = "└───";
		printStream.println(prefix + directoryNamePrefix + directory.getName());
		printEntries(directory, prefix + "  ", printStream);
	}

	public static void main(String[] args) throws IOException
	{

		String path = args.length == 1 ? args[0] : ".";
		File file = new File(path);
		if (file.isDirectory())
		{
			@Cleanup PrintStream printStream = new PrintStream(new File("tree.txt"), StandardCharsets.UTF_8);
			printEntries(file, "", printStream);
		}
		else if (file.isFile())
		{
			ReverseDirectoryTree reverseDirectoryTree = new ReverseDirectoryTree();
			reverseDirectoryTree.printSummary(file);
		}
		
		Files.copy(Path.of("tree.txt"), System.out);
	}

}
