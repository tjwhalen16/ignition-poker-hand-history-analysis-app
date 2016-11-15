package util;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Utility {
	private Utility() {
		
	}
	
	/**
	 * Turns a string into a list.
	 * Breaks the string into list elements by new lines
	 * @param handString - the string with new line seperated lines
	 * @return List<String> - list of lines
	 */
	public static List<String> getHandLinesAsList(String handString) {
		Scanner handScanner = new Scanner(handString);
		List<String> handStrings = new ArrayList<String>();
		String line;
		
		while (handScanner.hasNextLine()) {
			line = handScanner.nextLine();
			// TODO if line is empty?
			
			// If (ignoreLineSet.contains(line)) { TODO
			//	continue;
			//}
			
			handStrings.add(line);
		}
		handScanner.close();
		return handStrings;
	}
}
