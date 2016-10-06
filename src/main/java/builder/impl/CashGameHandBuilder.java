package builder.impl;

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
import service.model.impl.CashGameHand;

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
		//lineNumber = setPlayers(hand, handStrings, lineNumber);
		
		return hand;
	}
	
	@Override
	public int setPlayers(Hand hand, List<String> handStrings, int lineNumber) {

		String line = handStrings.get(lineNumber++);
		while (line.contains("Seat")) {
			
		}

		return lineNumber;
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
