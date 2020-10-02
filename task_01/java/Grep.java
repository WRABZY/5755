// default package to simplify manual testing during development

import java.time.Instant;
import java.time.Duration;

import javax.naming.OperationNotSupportedException;
import java.nio.file.Files;
import java.nio.charset.StandardCharsets;
import java.util.stream.Stream;
import java.util.stream.Collectors;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Logger;
import java.util.logging.Level;
import java.util.logging.Handler;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.List;
import java.util.Arrays;

/**
  * Class {@code Grep} encapsulates methods for line-by-line filtering
  * of the input stream of text information by a given word or some words 
  *	case-sensitive or case-insensitive or by regular expression.
  * @author WRABZY
  * @version 1.0
*/
public class Grep {
	
	// For logging
	private static final String className = "xyz.wrabzy.learning.Grep";
	private static final Logger logger = Logger.getLogger(className);
	private static       Handler handler; 
	static {
		try {
			handler = new FileHandler("%h/wrbjgrep_%g_%u.log", 50000, 3, true);
		}
		catch (IOException ioe) {
			handler = new ConsoleHandler();
		}
		logger.setUseParentHandlers(false);
		logger.setLevel(Level.ALL);
		handler.setLevel(Level.ALL);
		logger.addHandler(handler);
	}
	
	
	// The core of this class
	private List<String> lines;
	
	
	// Constructors
	public Grep() {
		this.lines = null;
		logger.log(Level.FINE, "Empty Grep-object created.");
	}
	
	public Grep(List<String> lines) {
		this.lines = lines;
		logger.log(Level.FINE, "Grep-object created with List<String>.");
	}
	
	
	// Setter & getter
	public void setText(List<String> lines) throws OperationNotSupportedException {
		if (this.lines == null){
			logger.log(Level.FINE, "Successfull setting text in Grep-object.");
			this.lines = lines;
		} 
		else {
			OperationNotSupportedException onse = new OperationNotSupportedException("Overwrite prohibited!");
			logger.throwing(className, "setText", onse);
			throw onse;
		}
	}
	
	public List<String> getText() {
		logger.entering(className, "getText");
		
		if (lines != null) {
			logger.exiting(className, "getText", lines);
			return lines;
		}
		else {
			throw new NullPointerException("Grep-object is empty. (List of strings not specified.)");
		}
	}
	
	
	// Work methods
	
	
	public List<String> word(String w) {
		logger.entering(className, "word", w);
		
		if (lines != null) {
			List<String> answer = lines.stream()
									   .filter(l -> {
										   String[] words = l.toLowerCase().split("\\W+");
										   Arrays.sort(words);
										   return Arrays.binarySearch(words, w.toLowerCase()) >= 0;
									   })
									   .collect(Collectors.toList());
		
			logger.exiting(className, "word", answer);
		
			return answer;
		}
		else {
			throw new NullPointerException("Grep-object is empty. (List of strings not specified.)");
		}
	}
	
	
	public List<String> wordcs(String w) {
		logger.entering("xyz.wrabzy.Grep", "wordcs", w);
		
		if (lines != null) {
			List<String> answer = lines.stream()
									   .filter(l -> {
										   String[] words = l.split("\\W+");
										   Arrays.sort(words);
										   return Arrays.binarySearch(words, w) >= 0;
									   })
									   .collect(Collectors.toList());
		
			logger.exiting("xyz.wrabzy.Grep", "wordcs", answer);
		
			return answer;
		}
		else {
			throw new NullPointerException("Grep-object is empty. (List of strings not specified.)");
		}
	}
	
	
	public List<String> wwand(String... w) {
		logger.entering("xyz.wrabzy.Grep", "wwand", w);
		
		if (lines != null) {
			List<String> answer = lines.stream()
									   .filter(l -> {
										   String[] words = l.toLowerCase().split("\\W+");
										   Arrays.sort(words);
										   boolean containWord = true;
										   for (String word: w) containWord = containWord && (Arrays.binarySearch(words, word.toLowerCase()) >= 0);
										   return containWord;
									   })
									   .collect(Collectors.toList());
		
			logger.exiting("xyz.wrabzy.Grep", "wwand", answer);
		
			return answer;
		}
		else {
			throw new NullPointerException("Grep-object is empty. (List of strings not specified.)");
		}
	}
	
	
	public List<String> wwandcs(String... w) {
		logger.entering("xyz.wrabzy.Grep", "wwandcs", w);
		
		if (lines != null) {
			List<String> answer = lines.stream()
									   .filter(l -> {
										   String[] words = l.split("\\W+");
										   Arrays.sort(words);
										   boolean containWord = true;
										   for (String word: w) containWord = containWord && (Arrays.binarySearch(words, word) >= 0);
										   return containWord;
									   })
									   .collect(Collectors.toList());
		
			logger.exiting("xyz.wrabzy.Grep", "wwandcs", answer);
		
			return answer;
		}
		else {
			throw new NullPointerException("Grep-object is empty. (List of strings not specified.)");
		}
	}
	
	
	public List<String> wwor(String... w) {
		logger.entering("xyz.wrabzy.Grep", "wwor", w);
		
		if (lines != null) {
			List<String> answer = lines.stream()
									   .filter(l -> {
										   String[] words = l.toLowerCase().split("\\W+");
										   Arrays.sort(words);
										   boolean containWord = false;
										   for (String word: w) containWord = containWord || (Arrays.binarySearch(words, word.toLowerCase()) >= 0);
										   return containWord;
									   })
									   .collect(Collectors.toList());
		
			logger.exiting("xyz.wrabzy.Grep", "wwor", answer);
		
			return answer;
		}
		else {
			throw new NullPointerException("Grep-object is empty. (List of strings not specified.)");
		}
	}
	
	
	public List<String> wworcs(String... w) {
		logger.entering("xyz.wrabzy.Grep", "wworcs", w);
		
		if (lines != null) {
			List<String> answer = lines.stream()
									   .filter(l -> {
										   String[] words = l.split("\\W+");
										   Arrays.sort(words);
										   boolean containWord = false;
										   for (String word: w) containWord = containWord || (Arrays.binarySearch(words, word) >= 0);
										   return containWord;
									   })
									   .collect(Collectors.toList());
		
			logger.exiting("xyz.wrabzy.Grep", "wworcs", answer);
		
			return answer;
		}
		else {
			throw new NullPointerException("Grep-object is empty. (List of strings not specified.)");
		}
	}
	
	// Testing
	public static void main(String[] args) throws OperationNotSupportedException, IOException {
		logger.entering(className, "main", args);
		
		Logger testLogger = Logger.getLogger(className + "Test");
		testLogger.setLevel(Level.ALL);
		testLogger.setUseParentHandlers(false);
		testLogger.addHandler(handler);
		Level testLogLevel = Level.FINE;
		
		// First set of tests
		testLogger.logp(testLogLevel, className, "main", "First set of tests.");
		
		Instant firstStart = Instant.now();
		
		Path firstPath = Paths.get("..", "files", "Green_Eggs_and_Ham.txt");
		
		List<String> firstStringList = Files.readAllLines(firstPath, StandardCharsets.UTF_8);
		
		// Test of creating empty Grep-object
		Grep firstTest = new Grep();
		
		// Test of text setting in empty Grep-object
		firstTest.setText(firstStringList);
		
		// Test of text rewriting in Grep-object
		try {
			firstTest.setText(Arrays.asList("testLine1", "testLine2", "testLine3"));
		} catch (OperationNotSupportedException onse) {
			testLogger.log(testLogLevel, "Rewriting test successfully comleted.", onse);
		}
		
		// Test of filtering by one one-letter word case-insensitive
		String letterCaseInsensitive = "a";
		int mustFind = 52;
		boolean letterCaseInsensitiveSearching = firstTest.word(letterCaseInsensitive).size() == mustFind;
		testLogger.logp(testLogLevel, 
						className, 
						"main", 
						"Testing case-insensitive filtering by one-letter word \"" + letterCaseInsensitive + "\": " + letterCaseInsensitiveSearching);
		
		// Test of filtering by one word case-insensitive
		String wordCaseInsensitive = "anyWhere";
		mustFind = 8;
		boolean wordCaseInsensitiveSearching = firstTest.word(wordCaseInsensitive).size() == mustFind;
		testLogger.logp(testLogLevel, 
						className, 
						"main", 
						"Testing case-insensitive filtering by word \"" + wordCaseInsensitive + "\": " + wordCaseInsensitiveSearching);
		
		// Test of filtering by one word case-sensitive
		String wordCaseSensitive = "ANYWHERE";
		mustFind = 2;
		boolean wordCaseSensitiveSearching = firstTest.wordcs(wordCaseSensitive).size() == mustFind;
		testLogger.logp(testLogLevel, 
						className, 
						"main", 
						"Testing case-sensitive filtering by word \"" + wordCaseSensitive + "\": " + wordCaseSensitiveSearching);
		
		// Test of filtering by few words each of which included in line case-insensitive
		String[] wordsCaseInsensitive = {"say", "HAM"};
		mustFind = 1;
		boolean wordsCaseInsensitiveSearching = firstTest.wwand(wordsCaseInsensitive).size() == mustFind;
		testLogger.logp(testLogLevel, 
						className, 
						"main", 
						"Testing case-insensitive filtering by few words \"" + Arrays.toString(wordsCaseInsensitive)  + "\"" + " using \"AND\": " + wordsCaseInsensitiveSearching);
		
		// Test of filtering by few words each of which included in line case-sensitive
		String[] wordsCaseSensitive = {"A", "train"};
		mustFind = 2;
		boolean wordsCaseSensitiveSearching = firstTest.wwandcs(wordsCaseSensitive).size() == mustFind;
		testLogger.logp(testLogLevel, 
						className, 
						"main", 
						"Testing case-sensitive filtering by few words \"" + Arrays.toString(wordsCaseSensitive)  + "\"" + " using \"AND\": " + wordsCaseSensitiveSearching);
		
		// Test of filtering by few words at least one of which included in line case-insensitive
		String[] wordsOrCaseInsensitive = {"orange", "could", "BOX"};
		mustFind = 20;
		boolean wordsOrCaseInsensitiveSearching = firstTest.wwor(wordsOrCaseInsensitive).size() == mustFind;
		testLogger.logp(testLogLevel, 
						className, 
						"main", 
						"Testing case-insensitive filtering by few words \"" + Arrays.toString(wordsOrCaseInsensitive)  + "\"" + " using \"OR\": " + wordsOrCaseInsensitiveSearching);
		
		// Test of filtering by few words at least one of which included in line case-sensitive
		String[] wordsOrCaseSensitive = {"orange", "could", "BOX"};
		mustFind = 10;
		boolean wordsOrCaseSensitiveSearching = firstTest.wworcs(wordsOrCaseSensitive).size() == mustFind;
		testLogger.logp(testLogLevel, 
						className, 
						"main", 
						"Testing case-sensitive filtering by few words \"" + Arrays.toString(wordsOrCaseSensitive)  + "\"" + " using \"OR\": " + wordsOrCaseSensitiveSearching);
										
		Instant firstEnd = Instant.now();
		Duration firstTimeElapsed = Duration.between(firstStart,firstEnd);
		long firstMillis = firstTimeElapsed.toMillis();
		long firstSeconds = firstTimeElapsed.getSeconds();
		testLogger.logp(testLogLevel, 
						className, 
						"main", 
						String.format("First set of tests successfully completed in %1$d.%2$d seconds.", firstSeconds, firstMillis));
		
		Logger.getGlobal().info("Testing completed!");
		logger.exiting("xyz.wrabzy.Grep", "main");
	}
}