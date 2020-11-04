// default package is using to simplify directory structure
// it is assumed that package is "xyz.wrabzy.learning" (full classname is "xyz.wrabzy.learning.Grep")

import java.util.stream.Stream;
import java.util.stream.Collectors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Arrays;
import java.util.logging.Logger;

/**
* Class {@code Grep} encapsulates static methods for line-by-line filtering
* of the input stream of text information by a given word or some words 
* case-sensitive or case-insensitive or by regular expression.
* @author WRABZY
* @version 1.0
*/
public class Grep {
	
	/**
	* @return A stream of lines from source text-stream that contain given word.
	* @param caseSensitive True means filtering will be case sensitive. 
	*/
	public static Stream<String> onlyLinesContainingAWord(Stream<String> source, boolean caseSensitive, String word) {
	  return caseSensitive ? 
           source.filter(line -> wordsContainWord(caseSensitiveWordsOfLine(line), word)) :
           source.filter(line -> wordsContainWord(caseInsensitiveWordsOfLine(line), word.toLowerCase()));
	}
	
	/**
	* @return A stream of lines from source text-stream that contain all of given words.
	* @param caseSensitive True means filtering will be case sensitive. 
	*/
	public static Stream<String> onlyLinesContainingAllWords(Stream<String> source, boolean caseSensitive, String... words) {
	  return caseSensitive ?
           source.filter(line -> wordsContainAllWords(caseSensitiveWordsOfLine(line), words)) :
           source.filter(line -> wordsContainAllWords(caseInsensitiveWordsOfLine(line), setLowCase(words))); 
	}

	/**
	* @return A stream of lines from source text-stream that contain at least one of given words.
	* @param caseSensitive True means filtering will be case sensitive. 
	*/
	public static Stream<String> onlyLinesContainingSomeWords(Stream<String> source, boolean caseSensitive, String... words) {
	  return caseSensitive ?
           source.filter(line -> wordsContainSomeWords(caseSensitiveWordsOfLine(line), words)) :
           source.filter(line -> wordsContainSomeWords(caseInsensitiveWordsOfLine(line), setLowCase(words))); 
	}
	
	/**
	* @return A stream of lines from source text-stream in which there was founded a match with the given pattern. 
	*/
	public static Stream<String> onlyLinesMatchingRegex (Stream<String> source, String pattern) {
	  return source.filter(line -> Pattern.compile(pattern).matcher(line).find());
	}
	
	private static String[] caseSensitiveWordsOfLine(String line) {
	  String[] wordsOfLine = line.split("\\W+");
	  Arrays.sort(wordsOfLine);
	  return wordsOfLine;
	}
	
	private static String[] caseInsensitiveWordsOfLine(String line) {
	  String[] wordsOfLine = line.toLowerCase().split("\\W+");
	  Arrays.sort(wordsOfLine);
	  return wordsOfLine;
	}
	
	private static boolean wordsContainWord(String[] words, String word) {
	  return Arrays.binarySearch(words, word) >= 0;
	}
	
	private static boolean wordsContainAllWords(String[] words, String[] allWords) {
	  boolean status = true;
    for (String word: allWords) {
      status = status && wordsContainWord(words, word);
    }
    return status;
	}
		
	private static boolean wordsContainSomeWords(String[] words, String[] someWords) {
	  boolean status = false;
    for (String word: someWords) {
      status = status || wordsContainWord(words, word);
    }
    return status;
	}

  private static String[] setLowCase (String... wordsToSetLowCase) {
    String[] lowCaseWords = new String[wordsToSetLowCase.length];
    for (int i = 0; i < wordsToSetLowCase.length; i++) {
      lowCaseWords[i] = wordsToSetLowCase[i].toLowerCase();
    }
    return lowCaseWords;
  }
  
	public static void main(String[] args) {
	  if (args.length != 0 && args[0].equals("grepselftest")) {
      Logger.getGlobal().info("Grep's owns tests started.\n");
      
      filteringByOneWordTest01();
      filteringByOneWordTest02();
      filteringByOneWordTest03();
      filteringByOneWordTest04();
      
      filteringByAllWordsTest01();
      filteringByAllWordsTest02();
      
      filteringBySomeWordsTest01();
      filteringBySomeWordsTest02();
      
      filteringByRegexTest01();
      filteringByRegexTest02();
      
      Logger.getGlobal().info("Selftesting completed successfully.\n");
    }
	}
  
  // for testing
  private static Stream<String> getTestText() {
    return Stream.of(
                     "Shawshank Redemption, The, 1994"
                    ,"Green Mile, The, 1999"
                    ,"Forrest Gump, 1994"
                    ,"Schindler's List, 1993"
                    ,"Intouchables, 2011"
                    ,"Inception, 2010"
                    ,"Léon, 1994"
                    ,"Lion King, The, 1994"
                    ,"Fight Club, 1999"
                    );
  }
  
  // for testing
  private static void filteringByOneWordTest01() {
    String testWord = "The";
    assert onlyLinesContainingAWord(getTestText(), true, testWord)
           .collect(Collectors.joining("\n"))
           .equals ("Shawshank Redemption, The, 1994" + "\n" +
                    "Green Mile, The, 1999"           + "\n" +
                    "Lion King, The, 1994") 
    : "Failed filtering by word \"" + testWord + "\" case sensitive.";
  }
  
  // for testing
  private static void filteringByOneWordTest02() {
    String testWord = "the";
    assert onlyLinesContainingAWord(getTestText(), true, testWord)
           .collect(Collectors.joining("\n"))
           .equals ("")
    : "Failed filtering by word \"" + testWord + "\" case sensitive.";
  }
  
  // for testing
  private static void filteringByOneWordTest03() {
    String testWord = "green";
    assert onlyLinesContainingAWord(getTestText(), false, testWord)
           .collect(Collectors.joining("\n"))
           .equals ("Green Mile, The, 1999")
    : "Filtering by word \"" + testWord + "\" case insensitive.";
  }
  
  // for testing
  private static void filteringByOneWordTest04() {
    String testWord = "1994";
    assert onlyLinesContainingAWord(getTestText(), false, testWord)
           .collect(Collectors.joining("\n"))
           .equals ("Shawshank Redemption, The, 1994" + "\n" + 
                    "Forrest Gump, 1994"              + "\n" +
                    "Léon, 1994"                      + "\n" +
                    "Lion King, The, 1994")
    : "Failed filtering by word \"" + testWord + "\" case insensitive.";
  }

  // for testing
  private static void filteringByAllWordsTest01() {
    String[] testWords = {"The", "1994"};
    assert onlyLinesContainingAllWords(getTestText(), true, testWords)
           .collect(Collectors.joining("\n"))
           .equals ("Shawshank Redemption, The, 1994" + "\n" +
                    "Lion King, The, 1994")
    : "Failed filtering by all words \"" + Arrays.toString(testWords) + "\" case sensitive.";
  }
  
  // for testing
  private static void filteringByAllWordsTest02() {
    String[] testWords = {"inception", "2010"};
    assert onlyLinesContainingAllWords(getTestText(), false, testWords)
           .collect(Collectors.joining("\n"))
           .equals ("Inception, 2010")
    : "Failed filtering by all words \"" + Arrays.toString(testWords) + "\" case insensitive.";
  }

  // for testing
  private static void filteringBySomeWordsTest01() {
    String[] testWords = {"The", "1994"};
    assert onlyLinesContainingSomeWords(getTestText(), true, testWords)
           .collect(Collectors.joining("\n"))
           .equals ("Shawshank Redemption, The, 1994" + "\n" +
                    "Green Mile, The, 1999"           + "\n" +
                    "Forrest Gump, 1994"              + "\n" +
                    "Léon, 1994"                      + "\n" +
                    "Lion King, The, 1994")
    : "Failed filtering by some of words \"" + Arrays.toString(testWords) + "\" case sensitive.";
  }
  
  // for testing
  private static void filteringBySomeWordsTest02() {
    String[] testWords = {"mile", "list", "2011", "inception", "1994", "club"};
    assert onlyLinesContainingSomeWords(getTestText(), false, testWords)
           .collect(Collectors.joining("\n"))
           .equals ("Shawshank Redemption, The, 1994" + "\n" + 
                    "Green Mile, The, 1999"           + "\n" + 
                    "Forrest Gump, 1994"              + "\n" + 
                    "Schindler's List, 1993"          + "\n" + 
                    "Intouchables, 2011"              + "\n" + 
                    "Inception, 2010"                 + "\n" + 
                    "Léon, 1994"                      + "\n" + 
                    "Lion King, The, 1994"            + "\n" + 
                    "Fight Club, 1999")
    : "Failed filtering by some of words \"" + Arrays.toString(testWords) + "\" case insensitive.";
  }
  
  // for testing
  private static void filteringByRegexTest01() {
    String regex = "^L.*4$";
    assert onlyLinesMatchingRegex(getTestText(), regex)
           .collect(Collectors.joining("\n"))
           .equals ("Léon, 1994" + "\n" + 
                    "Lion King, The, 1994")
    : "Failed filtering by regex: " + regex;
  }
  
  // for testing
  private static void filteringByRegexTest02() {
    String regex = ".+ion.+";
    assert onlyLinesMatchingRegex(getTestText(), regex)
           .collect(Collectors.joining("\n"))
           .equals ("Shawshank Redemption, The, 1994" + "\n" + 
                    "Inception, 2010"                 + "\n" + 
                    "Lion King, The, 1994")
    : "Failed filtering by regex: " + regex;
  }

}