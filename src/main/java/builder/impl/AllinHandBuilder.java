package builder.impl;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import builder.HandBuilder;
import service.model.impl.Allin;
import service.model.impl.AllinHand;
import service.model.impl.Board;
import service.model.impl.Player;
import service.model.impl.PositionEnum;
import util.Constants;
import util.Utility;

public class AllinHandBuilder implements HandBuilder {
	private final static Logger logger = LoggerFactory.getLogger(AllinHandBuilder.class);
	
	@Override
	public AllinHand build(String handString) {
		AllinHand hand = new AllinHand();
		List<String> handStrings = Utility.getHandLinesAsList(handString);
		int lineNumber = 0;	
		lineNumber = setTime(hand, handStrings, lineNumber);
		lineNumber = setPlayers(hand, handStrings, lineNumber);			
		setAllin(hand, handStrings, lineNumber);
		return hand;
	}
	
	private void setAllin(AllinHand hand, List<String> handStrings, int lineNumber) {
		String lastSeenBoard = "";
		for ( ; lineNumber < handStrings.size(); lineNumber++) {
			String line = handStrings.get(lineNumber);
			if (line.matches(Constants.boardPatternString)) {
				lastSeenBoard = line;
			} else if (line.matches(Constants.allinPatternString)) {
				logger.info("allin found");
				Allin allin = new Allin();
				createAllin(allin, hand, lastSeenBoard, line); // Current line has "all-in" in it
				hand.setAllin(allin);
				break;
			}
		}
	}

	private void createAllin(Allin allin, AllinHand hand, String boardLine, String allinLine) {
		Pattern allinPattern = Pattern.compile(Constants.allinPatternString);
		Matcher m = allinPattern.matcher(allinLine);		
		if (! m.matches()) {
			logger.error("Allin was found in line but regex matcher groups aren't there: {}", allinLine);
		}		
		PositionEnum position = PositionEnum.fromString(m.group(1));
		allin.setPlayer(hand.getPlayerWithPosition(position));
		allin.setSize(Double.parseDouble(m.group(2)));
				
		Pattern cardPattern = Pattern.compile(Constants.cardPatternString);
		m = cardPattern.matcher(boardLine);
		Board board = new Board();
		while (m.find()) {
			board.addCardString(m.group(1));
		}
		allin.setBoard(board);
	}
}
