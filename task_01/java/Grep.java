// default package is using to simplify directory structure
// it is assumed that package is "xyz.wrabzy.learning" (full classname is "xyz.wrabzy.learning.Grep")

import java.util.List;			// Not used in the class, imported for testing
import java.util.ArrayList;		// Not used in the class, imported for testing	

import java.util.stream.Stream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Arrays;

/**
  * Class {@code Grep} encapsulates static methods for line-by-line filtering
  * of the input stream of text information by a given word or some words 
  *	case-sensitive or case-insensitive or by regular expression.
  * @author WRABZY
  * @version 1.0
*/
public class Grep {
	
	/**
	  * Filters the text-stream
	  * depending on the presence of the given word in it
	  * case-sensitive or case-insensitive
	  * @param source - text-stream for filtering. It is assumed that text divided by lines.
	  * @param caseSensitive - flag of case-sensitivity. True - filtering will be case-sensitive.
	  * @param word - if line contains this word, she will be included in result text-stream.
	  * @return A stream of lines from source text-stream that contain given word. 
	*/
	public static Stream<String> byWord(Stream<String> source, boolean caseSensitive, String word) {
		return caseSensitive ? 
			   source.filter(line -> {
						String[] wordsOfLine = line.split("\\W+");
						Arrays.sort(wordsOfLine);
						return Arrays.binarySearch(wordsOfLine, word) >= 0;
					  }) :
			   source.filter(line -> {
					    String[] wordsOfLine = line.toLowerCase().split("\\W+");
					    Arrays.sort(wordsOfLine);
					    return Arrays.binarySearch(wordsOfLine, word.toLowerCase()) >= 0;
					  });
	}
	
	/**
	  * Filters the text-stream
	  * depending on the presence all of given words in it
	  * case-sensitive or case-insensitive
	  * @param source - text-stream for filtering. It is assumed that text divided by lines.
	  * @param caseSensitive - flag of case-sensitivity. True - filtering will be case-sensitive.
	  * @param words - if line contains all of this words, she will be included in result text-stream.
	  * @return A stream of lines from source text-stream that contain all of given words. 
	*/
	public static Stream<String> allWords(Stream<String> source, boolean caseSensitive, String... words) {
		return caseSensitive ?
			   source.filter(line -> {
					    String[] wordsOfLine = line.split("\\W+");
					    Arrays.sort(wordsOfLine);
					    boolean containWord = true;
					    for (String word: words) containWord = containWord && ((Arrays.binarySearch(wordsOfLine, word)) >= 0);
					    return containWord;
					  }) :
			   source.filter(line -> {
					    String[] wordsOfLine = line.toLowerCase().split("\\W+");
					    Arrays.sort(wordsOfLine);
					    boolean containWord = true;
					    for (String word: words) containWord = containWord && (Arrays.binarySearch(wordsOfLine, word.toLowerCase()) >= 0);
					    return containWord;
					  });
	}
	
	/**
	  * Filters the text-stream
	  * depending on the presence at least one of given words in it
	  * case-sensitive or case-insensitive
	  * @param source - text-stream for filtering. It is assumed that text divided by lines.
	  * @param caseSensitive - flag of case-sensitivity. True - filtering will be case-sensitive.
	  * @param words - if line contains at least one of this words, she will be included in result text-stream.
	  * @return A stream of lines from source text-stream that contain at least one of given words. 
	*/
	public static Stream<String> someWords(Stream<String> source, boolean caseSensitive, String... words) {
		return caseSensitive ?
			   source.filter(line -> {
					    String[] wordsOfLine = line.split("\\W+");
					    Arrays.sort(wordsOfLine);
					    boolean containWord = false;
					    for (String word: words) containWord = containWord || (Arrays.binarySearch(wordsOfLine, word) >= 0);
					    return containWord;
				      }) :
			   source.filter(line -> {
					    String[] wordsOfLine = line.toLowerCase().split("\\W+");
					    Arrays.sort(wordsOfLine);
					    boolean containWord = false;
					    for (String word: words) containWord = containWord || (Arrays.binarySearch(wordsOfLine, word.toLowerCase()) >= 0);
					    return containWord;
				      });
	}
	
	/**
	  * Filters the text-stream
	  * depending on match given pattern
	  * @param source - text-stream for filtering. It is assumed that text divided by lines.
	  * @param pattern - regular expression, @see java.util.regex.Pattern.
	  * @return A stream of lines from source text-stream in which there was founded a match with the given pattern. 
	*/
	public static Stream<String> regex (Stream<String> source, String pattern) {
		return source.filter(line -> Pattern.compile(pattern).matcher(line).find());
	}
	
	
	// Simple testing
	public static void main(String[] args) {
		List<String> testString = new ArrayList<>();
		testString.add("One   DARK and LIGHT RED    Ubahobo"); 
		testString.add("Two   dark AND light ORANGE ubahobo");
		testString.add("Three dark and light YELLOW UBAHOBO");
		testString.add("Four  DARK AND LIGHT GREEN  Ubahobo");
		testString.add("Five  DARK and light CYAN   ubahobo");
		testString.add("Six   dark AND light BLUE   UBAHOBO");
		testString.add("Seven dark and LIGHT VIOLET Ubahobo");
							
		// Testing byWord()					
	    assert byWord(testString.stream(), true,  "AND") .count() == 3 : "1 - One word case-sensitive.";
		assert byWord(testString.stream(), false, "AND") .count() == 7 : "2 - One word case-insensitive.";
		
		// Testing allWords()					
	    assert allWords(testString.stream(), true,  "DARK", "LIGHT",  "Ubahobo").count() == 2 : "1 - All words case-sensitive.";
		assert allWords(testString.stream(), false, "DARK", "LIGHT",  "Ubahobo").count() == 7 : "2 - All words case-insensitive.";
		
		// Testing someWords()					
	    assert someWords(testString.stream(), true,  "five", "green",   "VIOLET").count() == 1 : "1 - Some words case-sensitive.";
		assert someWords(testString.stream(), false, "five", "green",   "VIOLET").count() == 3 : "2 - Some words case-insensitive.";
		
		// Testing regex()	
		assert regex(testString.stream(), "^.+RED.+$"                 ).count() == 1 : "1 - Regex.";
		assert regex(testString.stream(), "^[TF].+$"                  ).count() == 4 : "2 - Regex.";
		assert regex(testString.stream(), "^\\w+ d"                   ).count() == 2 : "3 - Regex.";
		assert regex(testString.stream(), "^(?:(?:T.+)|(?:\\w+e .+$))").count() == 4 : "4 - Regex.";
	}
}