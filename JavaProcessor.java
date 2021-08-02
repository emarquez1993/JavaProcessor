// Eric Marquez and Nick Keele
// COSC 3420.001
// Project #4
// Due date: 4/4/2018
/*
 * This class corrects the following syntax errors for a .java file:
 * Missing semicolons.
 * Missing compound statements.
 * All comments have to start with // and end with a period.
 * Correct indentation format.
 * 
 * NOTICE: The user's .java file needs to be in the same
 * folder as the project file or in the same folder as JavaProcessor.java
 * file if the user is not using eclipse.
 */
import java.io.*;
import java.util.ArrayList;
public class JavaProcessor {
	
	private static ArrayList<String> javaFile = new ArrayList<String>(); // used to document changes.
	private static String fileName; // used to keep track of the users file name.
	
	/*
	 * One parameter constructor that initializes the users file into 
	 * an ArrayList with each line of their file as an index.
	 */
	public JavaProcessor(String file)
	{
		try {
			BufferedReader inputStream = new BufferedReader(new FileReader(file));
			String line = inputStream.readLine();
			while (line != null)
			{
				javaFile.add(line);
				line = inputStream.readLine();
			}
			fileName = file;
			inputStream.close();
		}
		
		catch(FileNotFoundException e)
		{
			System.exit(0);
		}
		
		catch(IOException e)
		{
			System.exit(0);
		}
	}
	
	/*
	 * Checks the user's file to see if all comments start with "//"
	 * and if all comments end with a period. 
	 * If either or both are missing then they will be added to the line.
	 * 
	 * NOTICE: This method needs to run second for proper formatting.
	 */
	public void commentCheck()
	{
		for(int n = 0; n < javaFile.size(); n++)
		{
			if(javaFile.get(n).charAt(0) == '/')
			{
				if(javaFile.get(n).charAt(1) != '/')
					javaFile.set(n, "/" + javaFile.get(n));
				if(javaFile.get(n).charAt(javaFile.get(n).length() - 1) != '.')
					javaFile.set(n, javaFile.get(n) + ".");
			}
		}
	}
	
	/*
	 * Checks the users file for missing semicolons and formats statements 
	 * to each have their own line.
	 * 
	 * NOTICE: This method needs to run third for proper formatting.
	 * This method needs to also be run twice for obscure cases.
	 */
	public void missingSemicolon()
	{
		int count = 0;
		String temp;
		for(int n = 0; n < javaFile.size(); n++)
		{
			if(javaFile.get(n).contains("public") || javaFile.get(n).contains("private") || javaFile.get(n).contains("protected"))
				count = 0;
			else
			if(javaFile.get(n).length() > 1)
			{
				if(javaFile.get(n).charAt(javaFile.get(n).length() - 1) != ';')
					if(javaFile.get(n).charAt(0) != '/' && javaFile.get(n).charAt(1) != '/')
						javaFile.set(n, javaFile.get(n) + ";");
			}
		}
		for(int g = 0; g < javaFile.size(); g++)
		{
			temp = javaFile.get(g);
			char[] array = javaFile.get(g).toCharArray();
			for(int f = 0; f < array.length; f++)
			{
				if(array[f] == ';')
					count++;
			}
			while(count > 1)
			{
				if(g + 1 < javaFile.size() - 1)
					javaFile.add(g+1,javaFile.get(g).substring(javaFile.get(g).lastIndexOf(' ') + 1));
				else
					javaFile.add(javaFile.get(g).substring(javaFile.get(g).lastIndexOf(' ') + 1));
				javaFile.set(g, javaFile.get(g).substring(0, javaFile.get(g).lastIndexOf(' '))); // - 1
				count--;
			}
			count = 0;
		}
	}
	
	/*
	 * Checks the users file for missing '{' and '}' and appropriately
	 * adds them to where they are missing. Also formats the file so that each
	 * curly brace is on their own line.
	 * 
	 * NOTICE: This method needs to run first for proper formatting.
	 * All missing '}' will be added at the end of the users file
	 * since this program cannot discern whether or not the user's
	 * loop statements are meant to be compound statements.
	 */
	public void missingCurlyBraces()
	{
		for(int y = 0; y < javaFile.size(); y++)
			javaFile.set(y, javaFile.get(y).trim());
		int leftBraceCount = 0;
		int rightBraceCount = 0;
		for(int n = 0; n < javaFile.size(); n++)
		{
			if(javaFile.get(n).length() > 1)
			{
				leftBraceCount = 0;
				rightBraceCount = 0;
				char[] array = javaFile.get(n).toCharArray();
				
				for(int i = 0; i < array.length; i++)
				{
					if(array[i] == '{')
						leftBraceCount++;
					if(array[i] == '}')
						rightBraceCount++;
				}
				
				if(leftBraceCount > 0)
				{
					if(javaFile.get(n).endsWith("{"))
					{
						javaFile.set(n, javaFile.get(n).replaceAll("\\{", "").trim());
						while(leftBraceCount > 0)
						{
							if(n == javaFile.size() - 1)
								javaFile.add("{");
							else
								javaFile.add(n + 1, "{");
							leftBraceCount--;
						}
					}
					else if(javaFile.get(n).startsWith("{"))
					{
						javaFile.set(n, javaFile.get(n).replaceAll("\\{", "").trim());
						while(leftBraceCount > 0)
						{
							javaFile.add(n, "{");
							leftBraceCount--;
						}
					}
					else
					{
						if(n == javaFile.size() - 1)
							javaFile.add(javaFile.get(n).substring(javaFile.get(n).indexOf('{')));
						else
							javaFile.add(n+1, javaFile.get(n).substring(javaFile.get(n).indexOf('{')));
						javaFile.set(n, javaFile.get(n).substring(0, javaFile.get(n).indexOf('{')));
						n--;
					}
				}
				
				if(rightBraceCount > 0)
				{
					if(javaFile.get(n).endsWith("}"))
					{
						javaFile.set(n, javaFile.get(n).replaceAll("\\}", "").trim());
						while(rightBraceCount > 0)
						{
							if(n == javaFile.size() - 1)
								javaFile.add("}");
							else
								javaFile.add(n + 1, "}");
							rightBraceCount--;
						}
					}
					else if(javaFile.get(n).startsWith("}"))
					{
						javaFile.set(n, javaFile.get(n).replaceAll("\\}", "").trim());
						while(rightBraceCount > 0)
						{
							javaFile.add(n, "}");
							rightBraceCount--;
						}
					}
					else
					{
						if(n == javaFile.size() - 1)
							javaFile.add(javaFile.get(n).substring(javaFile.get(n).indexOf('}')));
						else
							javaFile.add(n+1, javaFile.get(n).substring(javaFile.get(n).indexOf('}')));
						javaFile.set(n, javaFile.get(n).substring(0, javaFile.get(n).indexOf('}')));
						n--;
					}
				}
			}
			javaFile.trimToSize();
		}
		
		for(int p = 0; p < javaFile.size(); p++)
		{
			if(p < javaFile.size()-1)
				if((javaFile.get(p).startsWith("public") || javaFile.get(p).startsWith("private") || javaFile.get(p).startsWith("protected")) && !javaFile.get(p+1).startsWith("{"))
					javaFile.add(p+1, "{");
		}
		
		rightBraceCount = 0;
		leftBraceCount = 0;
		
		for(int j = 0; j < javaFile.size(); j++)
		{
			if(javaFile.get(j).startsWith("{"))
				leftBraceCount++;
			if(javaFile.get(j).startsWith("}"))
				rightBraceCount++;
		}
		
		if(rightBraceCount < leftBraceCount)
		{
			while(rightBraceCount < leftBraceCount)
			{
				javaFile.add("}");
				rightBraceCount++;
			}
		}
	}
	
	/*
	 * This method will properly format a file to have proper indentation.
	 * 
	 * NOTICE: This method needs to run fourth in order for proper formatting.
	 */
	public void indentationFormat()
	{
		int count = 0;
		for(int n = 0; n < javaFile.size(); n++) //indentation for curly braces.
		{
			if(javaFile.get(n).contains("}"))
				count--;
			for(int i = count; i > 0; i--)
				javaFile.set(n, "    " + javaFile.get(n));
			if(javaFile.get(n).contains("{"))
				count++;
		}
		for(int i = 0; i< javaFile.size(); i++) // indentation for if else, and loop structures.
		{
			if(javaFile.get(i).contains("for") || javaFile.get(i).contains("while") || javaFile.get(i).contains("if") || javaFile.get(i).contains("else"))
				if(i != 19)
					if(!javaFile.get(i + 1).contains("{"))
						javaFile.set(i + 1, "    " + javaFile.get(i + 1));
		}
	}
	
	/*
	 * This method will over write the users file with the contents of
	 * the ArrayList.
	 */
	public void writeToFile() // last
	{
		try {
			PrintWriter outputStream = new PrintWriter(new FileOutputStream(fileName));
			for(int i = 0; i < javaFile.size(); i++)
				outputStream.println(javaFile.get(i));
			outputStream.close();
		} catch (FileNotFoundException e) {
			System.exit(0);
		}
	}
	
}
