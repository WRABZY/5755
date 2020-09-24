// default package to simplify manual testing during development

/**
  * Public class {@code wrbjgrep} is entry point in Grep-program. 
  * Classname, that starts with lowercase letter imitates command "grep".
  * @author WRABZY
  * @version 1.0
*/
public class wrbjgrep {
	
	private static String name = "wrbjgrep";
	
	public static void main(String[] args) {
		switch (args.length) {
			case 0:
				System.out.printf("%n%1$s> print \"java %1$s -help\" for help%n", name);
				break;
			case 1:
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
										  + "%1$s> For example #2: D/My Documents/text.txt%n"
										  + "%1$s>%n"
										  + "%1$s> Keys - what to filter by. At least one key is required. Multiple keys can be used.%n"
										  + "%1$s> If multiple keys are used with mode --word, then only first one will be used for filtering.%n"
										  + "%1$s>%n"
										  + "%1$s> Target is path to the file where the result will be placed.%n" 
										  + "%1$s> If target not specified or it is not possible to find / create a file, then the result will be displayed on the terminal.%n%n", name);
						break;
					default:
						System.out.printf("%n%1$s> Unknown command or wrong arguments.%n%n", name);
				}
				break;
			default:
				switch (args[0]) {
					case      "w":
					case     "-w":
					case  "-word":
					case "--word":
						System.out.println("TODO");
						break;
					case      "wcs":
					case     "-wcs":
					case  "-wordcs":
					case "--wordcs":
						System.out.println("TODO");
						break;
					case     "wwa":
					case    "-wwa":
					case  "-wwand":
					case "--wwand":
						System.out.println("TODO");
						break;
					case     "wwacs":
					case    "-wwacs":
					case  "-wwandcs":
					case "--wwandcs":
						System.out.println("TODO");
						break;
					case    "wwo":
					case   "-wwo":
					case  "-wwor":
					case "--wwor":
						System.out.println("TODO");
						break;
					case    "wwocs":
					case   "-wwocs":
					case  "-wworcs":
					case "--wworcs":
						System.out.println("TODO");
						break;
					case       "r":
					case      "-r":
					case  "-regex":
					case "--regex":
						System.out.println("TODO");
						break;
					default:
						System.out.printf("%n%1$s> Mode not specified or specified incorrectly.%n%n", name);
				}
		}
	}
}