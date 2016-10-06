package builder.impl;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.List;

import org.junit.Test;

import service.model.Hand;
import service.model.impl.Player;

public class CashGameHandBuilderIT {

	@Test
	public void testBuildWithNoEdgeCases() {
		
		//Make Hand String to build off of
		String handString = //This is what a files look like, newLines explicityly added
				"Ignition Hand #3364065998 TBL#11040389 HOLDEM No Limit - 2016-09-26 20:44:14\n" +
				"Seat 1: UTG ($6.38 in chips)\n" +
				"Seat 2: UTG+1 ($4.80 in chips)\n" +
				"Seat 3: UTG+2 ($4.02 in chips)\n" +
				"Seat 4: UTG+3 ($2.28 in chips)\n" +
				"Seat 5: UTG+4 ($1.74 in chips)\n" +
				"Seat 7: Dealer ($4.78 in chips)\n" +
				"Seat 8: Small Blind ($1.45 in chips)\n" +
				"Seat 9: Big Blind [ME] ($5 in chips)\n" +
				"Dealer : Set dealer [7]\n" +
				"Small Blind : Small Blind $0.02\n" +
				"Big Blind  [ME] : Big blind $0.05\n" +
				"*** HOLE CARDS ***\n" +
				"UTG : Card dealt to a spot [2s 6c]\n" +
				"UTG+1 : Card dealt to a spot [Ad 9d]\n" + 
				"UTG+2 : Card dealt to a spot [Kd 8d]\n" + 
				"UTG+3 : Card dealt to a spot [6s 3d]\n" + 
				"UTG+4 : Card dealt to a spot [Jc 8s]\n" + 
				"Dealer : Card dealt to a spot [9s 7s]\n" + 
				"Small Blind : Card dealt to a spot [Qc 4h]\n" + 
				"Big Blind  [ME] : Card dealt to a spot [As 5h]\n" + 
				"UTG : Folds\n" + 
				"UTG+1 : Calls $0.05\n" +  
				"UTG+2 : Calls $0.05\n" +  
				"UTG+3 : Folds\n" + 
				"UTG+4 : Checks\n" + 
				"Dealer : Calls $0.05\n" +  
				"Small Blind : Folds\n" + 
				"Big Blind  [ME] : Checks\n" + 
				"*** FLOP *** [Ts 2h Ah]\n" + 
				"Big Blind  [ME] : Checks\n" + 
				"UTG+1 : Checks\n" + 
				"UTG+2 : Checks\n" + 
				"UTG+4 : Checks\n" + 
				"Dealer : Checks\n" + 
				"*** TURN *** [Ts 2h Ah] [Kh]\n" + 
				"Big Blind  [ME] : Bets $0.13\n" +  
				"UTG+1 : Calls $0.13\n" +  
				"UTG+2 : Raises $0.26 to $0.26\n" + 
				"UTG+4 : Folds\n" + 
				"Dealer : Folds\n" + 
				"Big Blind  [ME] : Calls $0.13\n" +  
				"UTG+1 : Folds\n" + 
				"*** RIVER *** [Ts 2h Ah Kh] [6d]\n" + 
				"Big Blind  [ME] : Bets $0.46\n" +  
				"UTG+2 : Folds\n" + 
				"Big Blind  [ME] : Return uncalled portion of bet $0.46\n" +  
				"Big Blind  [ME] : Does not show [As 5h] (One pair)\n" + 
				"Big Blind  [ME] : Hand result $0.88\n" +  
				"*** SUMMARY ***\n" + 
				"Total Pot($0.92)\n" + 
				"Board [Ts 2h Ah Kh 6d]\n" + 
				"Seat+1: UTG Folded before the FLOP\n" + 
				"Seat+2: UTG+1 Folded on the TURN\n" + 
				"Seat+3: UTG+2 Folded on the RIVER\n" + 
				"Seat+4: UTG+3 Folded before the FLOP\n" + 
				"Seat+5: UTG+4 Folded on the TURN\n" + 
				"Seat+7: Dealer Folded on the TURN\n" + 
				"Seat+8: Small Blind Folded before the FLOP\n" + 
				"Seat+9: Big Blind $0.88 [Does not show]\n";
			
		CashGameHandBuilder builder = new CashGameHandBuilder();
		Hand hand = builder.build(handString);
		
		//Check timestampe
		Calendar cal = Calendar.getInstance();
		cal.set(2016, Calendar.SEPTEMBER, 26, 20, 44, 14);
		cal.set(Calendar.MILLISECOND, 0);
		assertEquals("Timestamp is correct", cal.getTimeInMillis(), hand.getTime().getTime());
		
		
		//Not implemented yet
		assertNull(hand.getPlayers());
		assertNull(hand.getBlinds());
		assertNull(hand.getOpen());
		assertNull(hand.getWinner());
		
		
		//The Start of Check players
//		List<Player> players = hand.getPlayers();
//		assertEquals("Player count is correct", 8, players.size());
	}

}
