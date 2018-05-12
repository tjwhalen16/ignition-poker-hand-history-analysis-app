package builder.impl;

import java.util.List;

import builder.HandBuilder;
import service.model.Hand;
import service.model.impl.AllinHand;
import util.Utility;

public class AllinHandBuilder implements HandBuilder {

	@Override
	public AllinHand build(String handString) {
		AllinHand hand = new AllinHand();
		
		return hand;
		
//		CashGameHand hand = new CashGameHand();		
//		List<String> handStrings = Utility.getHandLinesAsList(handString);
//		int lineNumber = 0;		
//		// Pass hand line
//		lineNumber = setTime(hand, handStrings, lineNumber);		
//		// Pass lines the function setPlayers needs
//		lineNumber = setPlayers(hand, handStrings, lineNumber);	
//		// Pass lines the function setOpen needs to find open
//		lineNumber = setOpen(hand, handStrings, lineNumber);
//		setWinner(hand, handStrings, lineNumber);		
//		return hand;
	}

}
