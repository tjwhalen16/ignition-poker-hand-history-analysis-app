package builder;

import java.sql.Timestamp;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import service.model.Blinds;
import service.model.Hand;
import service.model.impl.Cards;
import service.model.impl.CashGameBlinds;
import service.model.impl.Player;
import service.model.impl.PositionEnum;

public interface HandBuilder {
	Hand build(String handString);
	
	default int setBlinds(Hand hand, List<String> handStrings, int lineNumber) {
		double smallBlind = 0.0;
		double bigBlind = 0.0;
		
		String line = handStrings.get(lineNumber++);
		while (! line.contains("HOLE CARDS")) {
			
			if (line.contains("Small Blind")) {
				smallBlind = getLastChipSize(line);
			}
			
			if (line.contains("Big Blind")) {
				bigBlind = getLastChipSize(line);
			}
			
			line = handStrings.get(lineNumber++);
		}
		
		Blinds blinds = new CashGameBlinds(smallBlind, bigBlind);
		hand.setBlinds(blinds);
		
		return lineNumber;		
	}
	
	default int setTime(Hand hand, List<String> handStrings, int lineNumber) {
		String line = handStrings.get(lineNumber++);
		int dateStartPosition = line.indexOf('-') + 2;
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Timestamp time = new Timestamp(dateFormat.parse(line, new ParsePosition(dateStartPosition)).getTime());
		hand.setTime(time);
		
		return lineNumber;		
	}
	
	default int setPlayers(Hand hand, List<String> handStrings, int lineNumber) {
		List<Player> players = new ArrayList<Player>();
		
		// get players positions and stack size
		String line = handStrings.get(lineNumber++);
		while (line.contains("Seat")) {
			PositionEnum position = getPosition(line, 8, '(');
			double stackSize = getLastChipSize(line);
			
			players.add(new Player(stackSize, position));			
			line = handStrings.get(lineNumber++);
		}	
		lineNumber = setBlinds(hand, handStrings, lineNumber);
		// get each players' cards
		line = handStrings.get(lineNumber);
		while (line.contains("dealt")) { // capturing lines that look like "UTG : Card dealt to a spot [2s 6c]"
			PositionEnum position = getPosition(line, 0, ':');			
			int endOfCardsIndex = line.lastIndexOf(']');
			int startOfCardsIndex = endOfCardsIndex - 5;
			String[] cards = line.substring(startOfCardsIndex, endOfCardsIndex).split(" ");
			
			// Loop through players to find the player with the correct position
			for (Player player : players) {
				if (position.equals(player.getPosition())) {
					player.setHand(new Cards(cards));
					break; // break on found
				}
			}			
			line = handStrings.get(++lineNumber);
		}
		hand.setPlayers(players);		
		return lineNumber; // While loop above goes 1 line too far		
	}

	/**
	 * Gets player position from a line string
	 * @param line - string to get position from
	 * @param positionStartIndex - index where the position starts
	 * @param charToStopAt - next non-whitespace character after the position string ends
	 * @return position - A string that represents the position
	 */
	default PositionEnum getPosition(String line, int positionStartIndex, char charToStopAt) {
		PositionEnum position;
		
		if (line.contains("[ME]")) { // This player is me
			// get my position
			position = PositionEnum.fromString(line.substring(positionStartIndex, line.indexOf('[')).trim());
		} else { // This player is not me
			position = PositionEnum.fromString(line.substring(positionStartIndex, line.indexOf(charToStopAt)).trim());
		}		
		return position;
	}
	
	/**
	 * Finds the last occurence of a chip size from a line
	 * @param line - the line to search for the chip size
	 * @return double - the last occurence of a chip size found in the line
	 */
	default double getLastChipSize(String line) {
		int startOfStackSizeIndex = line.lastIndexOf('$') + 1;
		int endOfStackSizeIndex = line.indexOf(' ', startOfStackSizeIndex);
		String stackSizeString;
		
		// If endOfStackSizeIndex is < 0, then the chip size is the last thing in the line
		// That means there will be no space to stop the substring at
		if (endOfStackSizeIndex > 0) { // So this one stops at the space
			stackSizeString = line.substring(startOfStackSizeIndex, endOfStackSizeIndex);
		} else { // and this one runs to the end of the line
			stackSizeString = line.substring(startOfStackSizeIndex);
		}	
		double size = Double.parseDouble(stackSizeString);		
		return size;
	}	
}
