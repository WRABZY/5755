// default package to simplify manual testing during development

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;

/**
  * Класс {@code Grep} предоставляет возможность 
  * построчно фильтровать поток текстовой информации, подаваемой на вход,
  *	по заданным слову, нескольким словам или регулярному выражению.
  * Класс {@code Grep} инкапсулирует только статические методы.
  * @author WRABZY
  * @version 1.0
*/
class Grep {
	
	public static void main(String[] args){
		if (args.length > 0) {
			switch (args[0]){
				
				default:
					if (args.length > 1) {
						File    file;
						Scanner scan = null;
						try {
							try {
								file = new File(args[0]);
								scan = new Scanner(file);
								String thisString = "";
								ArrayList<String> words = new ArrayList<>(30);
								while (scan.hasNextLine()) {
									thisString = scan.nextLine();
									for (String s: thisString.split(" ")) words.add(s);
									for (int i = 1; i < args.length; i++) {
										if (words.contains(args[i])) System.out.println(thisString);
									}
									words.clear();
								}
							}
							finally {
								if (scan != null) scan.close();
							}
						}
						catch (FileNotFoundException fnfe) {
							System.out.println("File not found!");
						}
					}
					else {
						System.out.println("Nothing to look for!");
					}
			}
		}
		else {
			System.out.println("print \"java wrbjgrep -help\" for help");
		}
			
	}
}