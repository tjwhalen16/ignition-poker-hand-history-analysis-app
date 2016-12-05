package service.model.impl;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Normalizes all hands to a common/basic input that the handBuilder expects.
 * Removes info from hand that I won't be using, such as players joining, buying more chips, etc.
 * 
 * @author Tyler
 */
public class HandNormalizer {
	private final static Logger logger = LoggerFactory.getLogger(HandNormalizer.class);
	
	/*
	 * Long, nasty, and neccessary function that walks through hand and logs times when the next 
	 * line isn't what it should be.
	 */
	public static String findDeviationsFromMinimalCorrectHand(List<String> lines) {
		int lineNumber = 0;
		StringBuilder deviations = new StringBuilder();
		String line = lines.get(lineNumber++);
		
		assertTrue(line.contains("Ignition Hand #"));
		logger.info("Normalizing hand: {}", line);
		
		int numSeats = 0;
		line = lines.get(lineNumber++); // Move past first line
		while (!line.contains("Set dealer")) {			
			if (!line.contains("Seat ")) {
				append(deviations, "Before set dealer: ", line, "\n");
			} else {
				numSeats++;
			}
			line = lines.get(lineNumber++);
		}
		
		line = lines.get(lineNumber++); // Move past "Set dealer" line
		if (!line.contains("Small Blind")) {
			append(deviations, "Before hole cards: ", line, "\n");
		}
		
		line = lines.get(lineNumber++); // Move past "Small Blind" line
		if (!line.contains("Big Blind")) {
			append(deviations, "Before hole cards: ", line, "\n");
		}
		
		line = lines.get(lineNumber++); // Move past "Big Blind" line
		if (!line.contains("* HOLE CARDS *")) {
			append(deviations, "Before hole cards: ", line, "\n");
		}
		
		int numDealt = 0;
		line = lines.get(lineNumber++); // Move past "* HOLE CARDS *" line
		while (!(line.contains("Folds") || line.contains("Calls") || line.contains("Raises"))) {
			if (!line.contains("Card dealt to a spot")) {
				append(deviations, "Before pre-flop action: ", line, "\n");
			} else {
				numDealt++;
			}
			line = lines.get(lineNumber++);
		}
		
		if (numSeats != numDealt) {
			append(deviations, "Players and cards dealt are not equal");
		}
		
		while (!line.contains("* FLOP *")) {
			if (!(line.contains("Folds") || line.contains("Calls") || line.contains("Raises") || line.contains("Checks") || line.contains("Bets"))) {
				append(deviations, "Before flop: ", line, "\n");
			}
			line = lines.get(lineNumber++);
		}
		
		line = lines.get(lineNumber++); // Move past "* FLOP *" line
		while (!line.contains("* TURN *")) {
			if (!(line.contains("Folds") || line.contains("Calls") || line.contains("Raises") || line.contains("Checks") || line.contains("Bets"))) {
				append(deviations, "Before turn: ", line, "\n");
			}
			line = lines.get(lineNumber++);
		}
		
		line = lines.get(lineNumber++); // Move past "* TURN *" line
		while (!line.contains("* RIVER *")) {
			if (!(line.contains("Folds") || line.contains("Calls") || line.contains("Raises") || line.contains("Checks") || line.contains("Bets"))) {
				append(deviations, "Before river: ", line, "\n");
			}
			line = lines.get(lineNumber++);
		}
		
		line = lines.get(lineNumber++); // Move past "* RIVER *" line
		while (!line.contains("* SUMMARY *")) {
			if (!(line.contains("Folds") || line.contains("Calls") || line.contains("Raises") || line.contains("Checks") || line.contains("Bets")) &&
				!(line.contains("uncalled") || line.contains("Does not show") || line.contains("Hand result"))) {
				append(deviations, "Before summary: ", line, "\n");
			}
			line = lines.get(lineNumber++);
		}
		
		line = lines.get(lineNumber++); // Move past "* SUMMARY *" line
		
		if (!line.contains("Total Pot")) {
			append(deviations, "Before EOF: ", line, "\n");
		}
		line = lines.get(lineNumber++); // Move past "Total Pot" line
		
		if (!line.contains("Board ")) {
			append(deviations, "Before EOF: ", line, "\n");
		}
		line = lines.get(lineNumber++); // Move past "Board " line
		
		for (int i = lineNumber; i < lines.size(); line = lines.get(i++)) {
			if (!line.contains("Seat+")) {
				append(deviations, "Before EOF: ", line, "\n");
			}
		}
		return deviations.toString();
	}
	
	public static List<String> normalize(List<String> lines) {
		return null; // TODO implement
	}
	
	private static void append(StringBuilder builder, String... toAppend) {
		Arrays.stream(toAppend)
			.forEach(str -> builder.append(str));
	}
}
