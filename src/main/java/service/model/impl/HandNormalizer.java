package service.model.impl;

import static org.junit.Assert.assertTrue;

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
	public static void logDifferences(List<String> lines) {
		int lineNumber = 0;
		String line = lines.get(lineNumber++);
		
		assertTrue(line.contains("Ignition Hand #"));
		logger.info("Normalizing hand: {}", line);
		
		int numSeats = 0;
		line = lines.get(lineNumber++); // Move past first line
		while (!line.contains("Set dealer")) {			
			if (!line.contains("Seat ")) {
				logger.warn("Before set dealer: {}", line);
			} else {
				numSeats++;
			}
			line = lines.get(lineNumber++);
		}
		
		line = lines.get(lineNumber++); // Move past "Set dealer" line
		if (!line.contains("Small Blind")) {
			logger.warn("Before hole cards: {}", line);
		}
		
		line = lines.get(lineNumber++); // Move past "Small Blind" line
		if (!line.contains("Big Blind")) {
			logger.warn("Before hole cards: {}", line);
		}
		
		line = lines.get(lineNumber++); // Move past "Big Blind" line
		if (!line.contains("* HOLE CARDS *")) {
			logger.warn("Before hole cards: {}", line);
		}
		
		int numDealt = 0;
		line = lines.get(lineNumber++); // Move past "* HOLE CARDS *" line
		while (!(line.contains("Folds") || line.contains("Calls") || line.contains("Raises"))) {
			if (!line.contains("Card dealt to a spot")) {
				logger.warn("Before pre-flop action: {}", line);
			} else {
				numDealt++;
			}
			line = lines.get(lineNumber++);
		}
		
		if (numSeats != numDealt) {
			logger.warn("Players and cards dealt are not equal");
		}
		
		while (!line.contains("* FLOP *")) {
			if (!(line.contains("Folds") || line.contains("Calls") || line.contains("Raises") || line.contains("Checks") || line.contains("Bets"))) {
				logger.warn("Before flop: {}", line);
			}
			line = lines.get(lineNumber++);
		}
		
		line = lines.get(lineNumber++); // Move past "* FLOP *" line
		while (!line.contains("* TURN *")) {
			if (!(line.contains("Folds") || line.contains("Calls") || line.contains("Raises") || line.contains("Checks") || line.contains("Bets"))) {
				logger.warn("Before turn: {}", line);
			}
			line = lines.get(lineNumber++);
		}
		
		line = lines.get(lineNumber++); // Move past "* TURN *" line
		while (!line.contains("* RIVER *")) {
			if (!(line.contains("Folds") || line.contains("Calls") || line.contains("Raises") || line.contains("Checks") || line.contains("Bets"))) {
				logger.warn("Before river: {}", line);
			}
			line = lines.get(lineNumber++);
		}
		
		line = lines.get(lineNumber++); // Move past "* RIVER *" line
		while (!line.contains("* SUMMARY *")) {
			if (!(line.contains("Folds") || line.contains("Calls") || line.contains("Raises") || line.contains("Checks") || line.contains("Bets"))) {
				logger.warn("Before summary: {}", line);
			}
			line = lines.get(lineNumber++);
		}
		
		line = lines.get(lineNumber++); // Move past "* SUMMARY *" line
		
		if (!line.contains("Total Pot")) {
			logger.warn("Before EOF: {}", line);
		}
		line = lines.get(lineNumber++); // Move past "Total Pot" line
		
		if (!line.contains("Board ")) {
			logger.warn("Before EOF: {}", line);
		}
		line = lines.get(lineNumber++); // Move past "Board " line
		
		for (int i = lineNumber; i < lines.size(); line = lines.get(i++)) {
			if (!line.contains("Seat+")) {
				logger.warn("Before EOF: {}", line);
			}
		}
	}
	
	public static List<String> normalize(List<String> lines) {
		return null; // TODO implement
	}
}
