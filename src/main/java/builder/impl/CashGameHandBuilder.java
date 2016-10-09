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
import org.springframework.jdbc.core.metadata.GenericTableMetaDataProvider;

import builder.HandBuilder;
import service.model.Blinds;
import service.model.Hand;
import service.model.impl.Cards;
import service.model.impl.CashGameBlinds;
import service.model.impl.CashGameHand;
import service.model.impl.Open;
import service.model.impl.OpenTypeEnum;
import service.model.impl.Player;
import service.model.impl.PositionEnum;

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
		
		logger.info("Set hands open");
		//pass lines the function setOpen needs to find open
		lineNumber = setOpen(hand, handStrings, lineNumber);
		
		return hand;
	}
	
	@Override
	public int setPlayers(Hand hand, List<String> handStrings, int lineNumber) {

		List<Player> players = new ArrayList<Player>();
		
		//get players positions and stack size
		String line = handStrings.get(lineNumber++);
		while (line.contains("Seat")) {
			//get position
			PositionEnum position = getPosition(line, 8, '(');
			
			//Get stack size
			double stackSize = getLastChipSize(line);
//			int startOfStackSizeIndex = line.indexOf('$') + 1;
//			String stackSizeString = line.substring(startOfStackSizeIndex, line.indexOf('i', startOfStackSizeIndex));
//			double stackSize = Double.parseDouble(stackSizeString);
			
			players.add(new Player(stackSize, position));
			
			line = handStrings.get(lineNumber++);
		}
		
		//skip currently unused line to get to lines with players' cards
//		while (! line.contains("HOLE CARDS")) {
//			line = handStrings.get(lineNumber++);
//		}
		
		//set the blinds
		lineNumber = setBlinds(hand, handStrings, lineNumber);
		
		//get each players' cards
		line = handStrings.get(lineNumber++);
		while (line.contains("dealt")) { //Capturing lines that look like "UTG : Card dealt to a spot [2s 6c]"
			//get position where cards were dealt
			PositionEnum position = getPosition(line, 0, ':');
			
			//get cards as a string array
			int endOfCardsIndex = line.lastIndexOf(']');
			int startOfCardsIndex = endOfCardsIndex - 5;
			String[] cards = line.substring(startOfCardsIndex, endOfCardsIndex).split(" ");
			
			//loop through players to find the player with the correct position
			for (Player player : players) {
				if (position.equals(player.getPosition())) {
					//found the player who should get the current cards
					player.setHand(new Cards(cards));
					break; //break on found
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
	private PositionEnum getPosition(String line, int positionStartIndex, char charToStopAt) {
		PositionEnum position;
		
		if (line.contains("[ME]")) { //this player is me
			//get my position
			position = PositionEnum.fromString(line.substring(positionStartIndex, line.indexOf('[')).trim());
		} else { //This player is not me
			position = PositionEnum.fromString(line.substring(positionStartIndex, line.indexOf(charToStopAt)).trim());
		}
		
		return position;
	}
	
	/**
	 * Finds the last occurence of a chip size from a line
	 * @param line - the line to search for the chip size
	 * @return double - the last occurence of a chip size found in the line
	 */
	private double getLastChipSize(String line) {
		int startOfStackSizeIndex = line.lastIndexOf('$') + 1;
		int endOfStackSizeIndex = line.indexOf(' ', startOfStackSizeIndex);
		String stackSizeString;
		
		//if endOfStackSizeIndex is < 0, then the chip size is the last thing in the line
		//that means there will be no space to stop the substring at
		if (endOfStackSizeIndex > 0) { //So this one stops at the space
			stackSizeString = line.substring(startOfStackSizeIndex, endOfStackSizeIndex);
		} else { //and this one runs to the end of the line
			stackSizeString = line.substring(startOfStackSizeIndex);
		}
		
		double size = Double.parseDouble(stackSizeString);
		
		return size;
	}

	@Override
	public int setOpen(Hand hand, List<String> handStrings, int lineNumber) {
		Open open = null;
		OpenTypeEnum type;
		int numPlayers = hand.getNumberPlayers();
		Timestamp time = hand.getTime();
		
		String line = handStrings.get(lineNumber++);
		while (! line.contains("FLOP")) { //while looking at pre-flop action
			
			if (line.contains("Calls")) {
				//A player opened with a limp
				type = OpenTypeEnum.LIMP;
				PositionEnum position = getPosition(line, 0, ':');
				Player player = hand.getPlayerWithPosition(position);
				double size = hand.getBlinds().getBigBlind();
				
				open = new Open(type, numPlayers, player, size, time);
				break; //break on found
			} else if (line.contains("Raise")) {
				type = OpenTypeEnum.RAISE;
				PositionEnum position = getPosition(line, 0, ':');
				Player player = hand.getPlayerWithPosition(position);
				double size = getLastChipSize(line);
				
				open = new Open(type, numPlayers, player, size, time);
				break; //break on found
			}
			
			
			line = handStrings.get(lineNumber++);
		}
		
		if (open == null) { //no one called or raised
			//Big blind got 'walked'
			type = OpenTypeEnum.WALK;
			Player player = hand.getPlayerWithPosition(PositionEnum.BB);
			double size = 0.0;
			
			open = new Open(type, numPlayers, player, size, time);
		}
		
		hand.setOpen(open);
		
		return lineNumber;
	}

	@Override
	public int setBlinds(Hand hand, List<String> handStrings, int lineNumber) {
		
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
