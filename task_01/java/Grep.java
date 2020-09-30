// default package to simplify manual testing during development

import java.time.Instant;
import java.time.Duration;

import javax.naming.OperationNotSupportedException;
import java.nio.file.Files;
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

/**
  * Class {@code Grep} encapsulates methods for line-by-line filtering
  * of the input stream of text information by a given word or some words 
  *	case-sensitive or case-insensitive or by regular expression.
  * @author WRABZY
  * @version 1.0
*/
public class Grep {
	
	// Logging
	private static final Logger logger = Logger.getLogger("xyz.wrabzy.Grep");
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
	
	private static final String className = "xyz.wrabzy.Grep";
	
	
	// The core of this class
	private Stream<String> lines;
	
	
	//Constructors
	public Grep() {
		this.lines = null;
		logger.log(Level.FINE, "Empty Grep-object created.");
	}
	
	public Grep(Stream<String> lines) {
		this.lines = lines;
		logger.log(Level.FINE, "Grep-object created with Stream<String>.");
	}
	
	
	// Set/get
	public void setText(Stream<String> lines) throws OperationNotSupportedException {
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
	
	public String getText() {
		return lines.collect(Collectors.joining("\n"));
	}
	
	
	// Work methods
	
	
	public String word(String w) {
		logger.entering("xyz.wrabzy.Grep", "word", w);
		
		String answer = lines.filter(l -> l.contains(w)).collect(Collectors.joining("\n"));
		
		logger.exiting("xyz.wrabzy.Grep", "word", answer);
		
		return answer;
	}
	
	
	public String wordcs(String w) {
		logger.entering("xyz.wrabzy.Grep", "wordcs", w);
		
		String answer = lines.filter(l -> l.contains(w)).collect(Collectors.joining("\n"));
		
		logger.exiting("xyz.wrabzy.Grep", "wordcs", answer);
		
		return answer;
	}
	
	
	// Testing
	public static void main(String[] args) throws OperationNotSupportedException, IOException {
		logger.entering("xyz.wrabzy.Grep", "main", args);
		
		Logger testLogger = Logger.getLogger("xyz.wrabzy.GrepTest");
		testLogger.setLevel(Level.ALL);
		testLogger.setUseParentHandlers(false);
		testLogger.addHandler(handler);
		Level testLogLevel = Level.FINE;
		
		// First set of tests
		testLogger.logp(testLogLevel, className, "main", "First set of tests.");
		
		Instant firstStart = Instant.now();
		
		Path firstPath = Paths.get("..", "files", "Green_Eggs_and_Ham.txt");
		
		try (Stream<String> firstStream = Files.lines(firstPath)) {
			Grep firstTest = new Grep();
			firstTest.setText(firstStream);
			try {
				firstTest.setText(Stream.of("testLine1", "testLine2", "testLine3"));
			} catch (OperationNotSupportedException onse) {
				testLogger.log(testLogLevel, "Rewriting test successfully comleted.", onse);
			}
			
			boolean mouseSearching = firstTest.word("mouse").split("\n").length == 8;
			testLogger.logp(testLogLevel, className, "main", "Testing case-sensitive filtering by word \"mouse\": " + mouseSearching);
		}
		
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