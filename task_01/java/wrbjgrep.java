// default package is using to simplify directory structure
// it is assumed that package is "xyz.wrabzy.learning" (full classname is "xyz.wrabzy.learning.wrbjgrep")

import java.util.stream.Stream;
import java.util.Arrays;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.nio.charset.StandardCharsets;

import java.io.PrintWriter;
import java.io.IOException;
import java.io.FileNotFoundException;

/**
* Class {@code wrbjgrep} is console-UI for Grep-class.
* @author WRABZY
* @version 1.0
* @see xyz.wrabzy.learning.Grep
*/
public class wrbjgrep {
	
	private static String name = "wrbjgrep";
	
	public static void main(String[] args) {
		if(args.length == 0) {
      System.out.printf("%n%1$s> Use arg \"-help\" to get help%n", name);
    }
    else if(args.length == 1) {
      switch (args[0]) {
				case      "h":
				case     "-h":
				case  "-help":
				case "--help":
					System.out.printf("%n%1$s> WRABZY's grep help:%n"
                            + "%1$s>%n"
                            + "%1$s> You can command: \"%1$s <mode> <source> <key1> <key2> <target>\"%n"
                            + "%1$s>%n"
                            + "%1$s> Available modes:%n"
                            + "%1$s> w       -w       -word      --word       - finds lines, that contains given word case insensitive.%n"
                            + "%1$s> wcs     -wcs     -wordcs    --wordcs     - finds lines, that contains given word case sensitive.%n"
                            + "%1$s> wwa     -wwa     -wwand     --wwand      - finds lines, that contains all given words case insensitive.%n"
                            + "%1$s> wwacs   -wwacs   -wwandcs   --wwandcs    - finds lines, that contains all given words case sensitive.%n"
                            + "%1$s> wwo     -wwo     -wwor      --wwor       - finds lines, that contains at least one of given words case insensitive.%n"
                            + "%1$s> wwocs   -wwocs   -wworcs    --wworcs     - finds lines, that contains at least one of given words case sensitive.%n"
                            + "%1$s> r       -r       -regex     --regex      - finds lines, that contains matching with given regular expression.%n"
                            + "%1$s>%n"
                            + "%1$s> Source is path to file, that needs to filter.%n"
                            + "%1$s> For example #1: C:\\New Folder\\New File.txt%n"
                            + "%1$s> For example #2: D:/My Documents/text.txt%n"
                            + "%1$s>%n"
                            + "%1$s> Keys - what to filter by. At least one key is required. Multiple keys can be used.%n"
                            + "%1$s> If multiple keys are used with mode --word, then only first one will be used for filtering.%n"
                            + "%1$s>%n"
                            + "%1$s> Target is path to the file where the result will be placed.%n" 
                            + "%1$s> If target not specified or it is not possible to find / create a file, then the result will be displayed on the terminal.%n%n", name);
					break;
				default:
					System.out.printf("%n%1$s> Unknown command or wrong arguments. Use arg \"-help\" to get help.%n%n", name);
			}
    }
    else if(args.length == 2) {
      System.out.printf("%n%1$s> Wrong arguments. Use arg \"-help\" to get help.%n%n", name);
    }
    else {
      if (args[args.length - 1].contains(".")) {
        try(PrintWriter writer = new PrintWriter(args[args.length - 1])){
          filter(Arrays.copyOfRange(args, 0, args.length - 1)).forEach(line -> writer.append(line + "\n"));
        }
        catch(FileNotFoundException fnfe) {
					System.out.printf("%n%1$s> Cant create file " + args[args.length - 1] + ". Console output is using.%n%n", name);
					filter(args).forEach(System.out::println);
				}
      }
      else{
        filter(args).forEach(System.out::println);
      }
    }
  }
  
  private static Stream<String> getLinesFromFile(String fileName) {
    Path source = Paths.get(fileName);
		try {
      return Files.lines(source, StandardCharsets.UTF_8);
    }
    catch (IOException ioe) {
      System.out.printf("%n%1$s> File " + fileName + " not found.%n%n", name);
      return Stream.of("");
    }
  }
      
	private static Stream<String> filter(String[] args) {
    switch (args[0]) {
			case      "w":
			case     "-w":
			case  "-word":
			case "--word":
        return Grep.onlyLinesContainingAWord(getLinesFromFile(args[1]), false, args[2]);
      case      "wcs":
      case     "-wcs":
      case  "-wordcs":
      case "--wordcs":
				return Grep.onlyLinesContainingAWord(getLinesFromFile(args[1]), true, args[2]);
      case     "wwa":
      case    "-wwa":
      case  "-wwand":
      case "--wwand":
        return Grep.onlyLinesContainingAllWords(getLinesFromFile(args[1]), false, Arrays.copyOfRange(args, 2, args.length));
      case     "wwacs":
      case    "-wwacs":
      case  "-wwandcs":
      case "--wwandcs":
        return Grep.onlyLinesContainingAllWords(getLinesFromFile(args[1]), true, Arrays.copyOfRange(args, 2, args.length));
      case    "wwo":
      case   "-wwo":
      case  "-wwor":
      case "--wwor":
        return Grep.onlyLinesContainingSomeWords(getLinesFromFile(args[1]), false, Arrays.copyOfRange(args, 2, args.length));
      case    "wwocs":
      case   "-wwocs":
      case  "-wworcs":
      case "--wworcs":
        return Grep.onlyLinesContainingSomeWords(getLinesFromFile(args[1]), true, Arrays.copyOfRange(args, 2, args.length));
      case       "r":
      case      "-r":
      case  "-regex":
      case "--regex":
        return Grep.onlyLinesMatchingRegex(getLinesFromFile(args[1]), args[2]);
      default:
        System.out.printf("%n%1$s> Mode not specified or specified incorrectly.%n%n", name);
        return Stream.of("");
    }
  }
  
}