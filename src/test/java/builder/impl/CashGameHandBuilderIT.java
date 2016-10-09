package builder.impl;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import service.model.Blinds;
import service.model.Hand;
import service.model.impl.Open;
import service.model.impl.OpenTypeEnum;
import service.model.impl.Player;
import service.model.impl.PositionEnum;

/**
 * Tests hand builder
 * @author Tyler
 *
 */
public class CashGameHandBuilderIT {
	//TODO run tests off of a test file instead of strings?
	
	@Test
	public void testBuildWithNoEdgeCases() {
		
		//Make Hand String to build off of
		String handString = //This is what a hand file look like, newLines explicityly added
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
		
		//Check timestamp
		Calendar cal = Calendar.getInstance();
		cal.set(2016, Calendar.SEPTEMBER, 26, 20, 44, 14);
		cal.set(Calendar.MILLISECOND, 0);
		assertEquals("Timestamp is correct", cal.getTimeInMillis(), hand.getTime().getTime());
		
		//Check players
		List<Player> players = hand.getPlayers();
		assertEquals("Player count is correct", 8, players.size());		
		//Turn list into map for easier testing
		Map<PositionEnum, Player> positionPlayerMap = new HashMap<PositionEnum, Player>(); 
		for (Player player : players) {
			positionPlayerMap.put(player.getPosition(), player);
		}
		
		//Check UTG
		assertTrue(positionPlayerMap.containsKey(PositionEnum.UTG));
		Player utg = positionPlayerMap.get(PositionEnum.UTG);
		assertEquals("UTG stacksize is correct", 6.38, utg.getStack(), 0.001);
		assertEquals("UTG hand is correct", "62o", utg.getHand().toString());
		//Check UTG+1
		assertTrue(positionPlayerMap.containsKey(PositionEnum.UTG1));
		Player utg1 = positionPlayerMap.get(PositionEnum.UTG1);
		assertEquals("UTG+1 stacksize is correct", 4.8, utg1.getStack(), 0.001);
		assertEquals("UTG+1 hand is correct", "A9s", utg1.getHand().toString());
		//Check UTG+2
		assertTrue(positionPlayerMap.containsKey(PositionEnum.UTG2));
		Player utg2 = positionPlayerMap.get(PositionEnum.UTG2);
		assertEquals("UTG+2 stacksize is correct", 4.02, utg2.getStack(), 0.001);
		assertEquals("UTG+2 hand is correct", "K8s", utg2.getHand().toString());
		//Check UTG+3
		assertTrue(positionPlayerMap.containsKey(PositionEnum.UTG3));
		Player utg3 = positionPlayerMap.get(PositionEnum.UTG3);
		assertEquals("UTG+3 stacksize is correct", 2.28, utg3.getStack(), 0.001);
		assertEquals("UTG+3 hand is correct", "63o", utg3.getHand().toString());
		//Check UTG+4
		assertTrue(positionPlayerMap.containsKey(PositionEnum.UTG4));
		Player utg4 = positionPlayerMap.get(PositionEnum.UTG4);
		assertEquals("UTG+4 stacksize is correct", 1.74, utg4.getStack(), 0.001);
		assertEquals("UTG+4 hand is correct", "J8o", utg4.getHand().toString());
		//Check Dealer
		assertTrue(positionPlayerMap.containsKey(PositionEnum.BTN));
		Player dealer = positionPlayerMap.get(PositionEnum.BTN);
		assertEquals("Dealer stacksize is correct", 4.78, dealer.getStack(), 0.001);
		assertEquals("Dealer hand is correct", "97s", dealer.getHand().toString());		
		//Check Small Blind
		assertTrue(positionPlayerMap.containsKey(PositionEnum.SB));
		Player smallBlind = positionPlayerMap.get(PositionEnum.SB);
		assertEquals("Small Blind stacksize is correct", 1.45, smallBlind.getStack(), 0.001);
		assertEquals("Small Blind hand is correct", "Q4o", smallBlind.getHand().toString());
		//Check Big Blind
		assertTrue(positionPlayerMap.containsKey(PositionEnum.BB));
		Player bigBlind = positionPlayerMap.get(PositionEnum.BB);
		assertEquals("Big Blind stacksize is correct", 5, bigBlind.getStack(), 0.001);
		assertEquals("Big Blind hand is correct", "A5o", bigBlind.getHand().toString());
		
		//Check open
		Open open = hand.getOpen();
		assertNotNull(open);
		assertEquals("Open has correct type", OpenTypeEnum.LIMP, open.getType());
		assertEquals("Open has correct number of players", 8, open.getNumPlayers());
		assertEquals("Open has correct position", PositionEnum.UTG1, open.getPosition());
		assertEquals("Open has correct cards", "A9s", open.getHand().toString());
		assertEquals("Open has correct stackSize", 4.80, open.getStackSize(), 0.001);
		assertEquals("Open has coorect open size", 0.05, open.getOpenSize(), 0.001);
		
		//Check blins
		Blinds blinds = hand.getBlinds();
		assertNotNull(blinds);
		assertEquals("Small Blind is correct", 0.02, blinds.getSmallBlind(), 0.001);
		assertEquals("Big Blind is correct", 0.05, blinds.getBigBlind(), 0.001);
		
		
		//Not implemented yet
		assertNull(hand.getWinner());
		
		

	}

}
