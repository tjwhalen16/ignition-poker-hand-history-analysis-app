package builder.impl;

import static org.mockito.Matchers.anyDouble;

import java.sql.Timestamp;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import builder.HandBuilder;
import service.model.Hand;
import service.model.impl.Cards;
import service.model.impl.CashGameHand;
import service.model.impl.Player;

public class CashGameHandBuilder implements HandBuilder {
	
	private static final Logger logger = LoggerFactory.getLogger(CashGameHandBuilder.class);
	
	public CashGameHandBuilder() {
		
	}
	
	
	private List<String> getHandLinesAsList(String handString) {
		Scanner handScanner = new Scanner(handString);
		List<String> handStrings = new ArrayList<String>();
		String line;
		
		while (handScanner.hasNextLine()) {
			line = handScanner.nextLine();
			//TODO if line is empty?
			
			//if (ignoreLineSet.contains(line)) { TODO
			//	continue;
			//}
			
			handStrings.add(line);
		}
		handScanner.close();
		return handStrings;
	}
	
	
	@Override
	public Hand build(String handString) {
		CashGameHand hand = new CashGameHand();
		
		logger.info("getHandLineAsList");
		List<String> handStrings = getHandLinesAsList(handString);
		int lineNumber = 0;
		
		logger.info("Set timestamp of hand");
		//pass hand line
		lineNumber = setTime(hand, handStrings, lineNumber);
		
		logger.info("Set players of hand");
		//pass lines the function setPlayers needs
		lineNumber = setPlayers(hand, handStrings, lineNumber);
		
		return hand;
	}
	
	@Override
	public int setPlayers(Hand hand, List<String> handStrings, int lineNumber) {

		List<Player> players = new ArrayList<Player>();
		
		//get players positions and stack size
		String line = handStrings.get(lineNumber++);
		while (line.contains("Seat")) {
			//get position
			String position = getPosition(line, 8, '(');
			
			//Get stack size
			int startOfStackSizeIndex = line.indexOf('$') + 1;
			String stackSizeString = line.substring(startOfStackSizeIndex, line.indexOf('i', startOfStackSizeIndex));
			double stackSize = Double.parseDouble(stackSizeString);
			
			players.add(new Player(stackSize, position));
			
			line = handStrings.get(lineNumber++);
		}
		
		//skip currently unused line to get to lines with players' cards
		while (! line.contains("HOLE CARDS")) {
			line = handStrings.get(lineNumber++);
		}
		
		//get each players' cards
		line = handStrings.get(lineNumber++);
		while (line.contains("dealt")) { //Capturing lines that look like "UTG : Card dealt to a spot [2s 6c]"
			//get position where cards were dealt
			String position = getPosition(line, 0, ':');
			
			//get cards as a string array
			int endOfCardsIndex = line.lastIndexOf(']');
			int startOfCardsIndex = endOfCardsIndex - 5;
			String[] cards = line.substring(startOfCardsIndex, endOfCardsIndex).split(" ");
			
			//loop through players to find the player with the correct position
			for (Player player : players) {
				if (position.equalsIgnoreCase(player.getPosition())) {
					//found the player who should get the current cards
					player.setHand(new Cards(cards));
					break;
				}
			}
			
			line = handStrings.get(lineNumber++);
		}
		
		hand.setPlayers(players);
		
		return lineNumber;
	}
	
	/**
	 * Gets player position from a line string
	 * @param line - string to get position from
	 * @param positionStartIndex - index where the position starts
	 * @param charToStopAt - next non-whitespace character after the position string ends
	 * @return position - A string that represents the position
	 */
	private String getPosition(String line, int positionStartIndex, char charToStopAt) {
		String position;
		
		if (line.contains("[ME]")) { //this player is me
			//get my position
			position = line.substring(positionStartIndex, line.indexOf('[')).trim();
		} else { //This player is not me
			position = line.substring(positionStartIndex, line.indexOf(charToStopAt)).trim();
		}
		
		return position;
	}

	@Override
	public void setOpen() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setBlinds() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setWinner() {
		// TODO Auto-generated method stub

	}

	@Override
	public int setTime(Hand hand, List<String> handStrings, int lineNumber) {
		String line = handStrings.get(lineNumber++);
		int dateStartPosition = line.indexOf('-') + 2;
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Timestamp time = new Timestamp(dateFormat.parse(line, new ParsePosition(dateStartPosition)).getTime());
		hand.setTime(time);
		
		return lineNumber;
	}

}
