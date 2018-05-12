package builder.impl;

import static java.util.stream.Collectors.toList;
import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.junit.Test;
import org.springframework.util.ResourceUtils;

import service.impl.HandExtractorServiceImpl;
import service.model.impl.Allin;
import service.model.impl.AllinHand;

public class AllinHandBuilderIT {
	@Test
	public void testGettingAllinsFromFile() {
		File file;
		try {
			file = ResourceUtils.getFile(this.getClass().getResource("/HH20160930-224100 - 4754389 - RING - $0.02-$0.05 - HOLDEM - NL - TBL No.11049214.txt"));
			HandExtractorServiceImpl handExtractor = new HandExtractorServiceImpl();
			List<String> handStrings = handExtractor.getAllHandStringsFromFile(file);
			AllinHandBuilder builder = new AllinHandBuilder();
			List<Allin> allins = handStrings.stream()
					   .map(builder::build)
					   .filter(AllinHand::didAllinHappen)
					   .map(AllinHand::getAllin)
					   .collect(toList());
			assertEquals("There should be 5 allins, I counted", 5, allins.size());			
			allins.forEach(System.out::println);
		} catch (IOException e) {
			System.out.println("Can't find file in resource folder\n");
		}
	}
	
	@Test
	public void testBuildWithNoEdgeCasesAndNoAllin() {
		// Make Hand String to build off of. It doesn't contain an allin
		String handString = // This is what a hand file look like, newLines explicityly added
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
		
		AllinHandBuilder builder = new AllinHandBuilder();
		AllinHand hand = builder.build(handString);
		
		assertFalse(hand.didAllinHappen());
	}
	
	@Test
	public void testBuildWithNoEdgeCases() {
		// Make Hand String to build off of. It does contain an allin
		String handString = // This is what a hand file look like, newLines explicityly added
				"Ignition Hand #3366038667 TBL#11049214 HOLDEM No Limit - 2016-09-30 22:41:06\n" +
				"Seat 1: UTG+4 ($4.73 in chips)\n" +
				"Seat 2: UTG+5 ($5.24 in chips)\n" +
				"Seat 3: Dealer ($3.73 in chips)\n" +
				"Seat 4: Small Blind ($3.65 in chips)\n" +
				"Seat 5: Big Blind [ME] ($5 in chips)\n" +
				"Seat 6: UTG ($2.34 in chips)\n" +
				"Seat 7: UTG+1 ($2.66 in chips)\n" +
				"Seat 8: UTG+2 ($6.22 in chips)\n" +
				"Seat 9: UTG+3 ($5.14 in chips)\n" +
				"Dealer : Set dealer [3] \n" +
				"Small Blind : Small Blind $0.02 \n" +
				"Big Blind  [ME] : Big blind $0.05 \n" +
				"*** HOLE CARDS ***\n" +
				"UTG+4 : Card dealt to a spot [Kd 8c] \n" +
				"UTG+5 : Card dealt to a spot [3d 9d] \n" +
				"Dealer : Card dealt to a spot [2s Qh] \n" +
				"Small Blind : Card dealt to a spot [Ad Jd] \n" +
				"Big Blind  [ME] : Card dealt to a spot [6s 5h] \n" +
				"UTG : Card dealt to a spot [Jc 4d] \n" +
				"UTG+1 : Card dealt to a spot [Ac Jh] \n" +
				"UTG+2 : Card dealt to a spot [Ah 5c] \n" +
				"UTG+3 : Card dealt to a spot [Ks 8h] \n" +
				"UTG : Folds\n" +
				"UTG+1 : Raises $0.15 to $0.15\n" +
				"UTG+2 : Folds\n" +
				"Table enter user\n" +
				"Table leave user\n" +
				"UTG+3 : Calls $0.15 \n" +
				"UTG+4 : Folds\n" +
				"UTG+5 : Folds\n" +
				"Dealer : Folds\n" +
				"Small Blind : Calls $0.13 \n" +
				"Big Blind  [ME] : Calls $0.10 \n" +
				"*** FLOP *** [7s As 9c]\n" +
				"Small Blind : Checks\n" +
				"Big Blind  [ME] : Checks\n" +
				"UTG+1 : Bets $0.23 \n" +
				"UTG+3 : Folds\n" +
				"Small Blind : Calls $0.23 \n" +
				"Big Blind  [ME] : Folds\n" +
				"*** TURN *** [7s As 9c] [4h]\n" +
				"Small Blind : Checks\n" +
				"UTG+1 : Bets $0.62 \n" +
				"Small Blind : Calls $0.62 \n" +
				"*** RIVER *** [7s As 9c 4h] [5s]\n" +
				"Small Blind : Checks\n" +
				"UTG+1 : All-in $1.66 \n" +
				"Small Blind : Folds\n" +
				"UTG+1 : Return uncalled portion of bet $1.66 \n" +
				"UTG+1 : Does not show [Ac Jh] (One pair)\n" +
				"UTG+1 : Hand result $2.19 \n" +
				"*** SUMMARY ***\n" +
				"Total Pot($2.30)\n" +
				"Board [7s As 9c 4h 5s]\n" +
				"Seat+1: UTG+4 Folded before the FLOP\n" +
				"Seat+2: UTG+5 Folded before the FLOP\n" +
				"Seat+3: Dealer Folded before the FLOP\n" +
				"Seat+4: Small Blind Folded on the RIVER\n" +
				"Seat+5: Big Blind Folded on the FLOP\n" +
				"Seat+6: UTG Folded before the FLOP\n" +
				"Seat+7: UTG+1 $2.19 [Does not show]  \n" +
				"Seat+8: UTG+2 Folded before the FLOP\n" +
				"Seat+9: UTG+3 Folded on the FLOP\n";	
		
		AllinHandBuilder builder = new AllinHandBuilder();
		AllinHand hand = builder.build(handString);
		
		assertTrue(hand.didAllinHappen());
		Allin allin = hand.getAllin();
		
		// Check hand
		assertEquals("Ac", allin.getHand().getCard1().toString());
		assertEquals("Jh", allin.getHand().getCard2().toString());
		// Check size
		assertEquals(1.66, allin.getSize(), 0.001);
		// Check board
		assertEquals("7s As 9c 4h 5s", allin.getBoard().toString());
	}	

}
