package builder.impl;

import java.sql.Timestamp;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
import util.Utility;

// TODO rename to OpenHandBuilder, because the purpose of this class is to record opens
public class CashGameHandBuilder implements HandBuilder {	
	private static final Logger logger = LoggerFactory.getLogger(CashGameHandBuilder.class);			
	
	@Override
	public CashGameHand build(String handString) {
		CashGameHand hand = new CashGameHand();		
		List<String> handStrings = Utility.getHandLinesAsList(handString);
		int lineNumber = 0;		
		// Pass hand line
		lineNumber = setTime(hand, handStrings, lineNumber);		
		// Pass lines the function setPlayers needs
		lineNumber = setPlayers(hand, handStrings, lineNumber);	
		// Pass lines the function setOpen needs to find open
		lineNumber = setOpen(hand, handStrings, lineNumber);
		setWinner(hand, handStrings, lineNumber);		
		return hand;
	}	

	public int setOpen(CashGameHand hand, List<String> handStrings, int lineNumber) {
		Open open = null;
		OpenTypeEnum type;
		int numPlayers = hand.getNumberPlayers();
		Timestamp time = hand.getTime();
		
		String line = handStrings.get(lineNumber++);
		while (! line.contains("FLOP")) { // While looking at pre-flop action			
			if (line.contains("Calls")) { 
				// a player opened with a limp
				type = OpenTypeEnum.LIMP;
				PositionEnum position = getPosition(line, 0, ':');
				Player player = hand.getPlayerWithPosition(position);
				double size = hand.getBlinds().getBigBlind();				
				open = new Open(type, numPlayers, player, size, time);
				break; // break on found
			} else if (line.contains("Raise")) {
				// A player opened with a raise
				type = OpenTypeEnum.RAISE;
				PositionEnum position = getPosition(line, 0, ':');
				Player player = hand.getPlayerWithPosition(position);
				double size = getLastChipSize(line);				
				open = new Open(type, numPlayers, player, size, time);
				break; // break on found
			}					
			line = handStrings.get(lineNumber++);
		}
		
		if (open == null) { // No one called or raised
			// big blind got 'walked'
			type = OpenTypeEnum.WALK;
			Player player = hand.getPlayerWithPosition(PositionEnum.BB);
			double size = 0.0;
			
			open = new Open(type, numPlayers, player, size, time);
		}		
		hand.setOpen(open);		
		return lineNumber;
	}

	public void setWinner(CashGameHand hand, List<String> handStrings, int lineNumber) {
		String line = handStrings.get(lineNumber++);
		while (!line.contains("Hand result")) {
			// Loop until line with winner is found
			line = handStrings.get(lineNumber++);
		}
		PositionEnum position = getPosition(line, 0, ':');
		hand.setWinner(hand.getPlayerWithPosition(position));
	}
}
