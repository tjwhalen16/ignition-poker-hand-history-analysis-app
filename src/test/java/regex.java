import static org.junit.Assert.*;

import org.junit.Test;

import util.Constants;

public class regex {

	@Test
	public void test() {
		assertTrue("*** RIVER *** [7s As 9c 4h] [5s]".matches(Constants.boardPatternString));
		assertFalse("*** SUMMARY ***".matches(Constants.boardPatternString));
		assertFalse("Small Blind : Calls $0.62 ".matches(Constants.boardPatternString));
		
		
		assertTrue("UTG+1 : All-in $1.66 ".matches(Constants.allinPatternString));
		assertTrue("UTG+1 : All-in $1.66".matches(Constants.allinPatternString));
		assertFalse("UTG+1 : All-in $1.66f".matches(Constants.allinPatternString));
		assertFalse("UTG+1 : All-in $1.66 f".matches(Constants.allinPatternString));
		assertFalse("Small Blind : Calls $0.62 ".matches(Constants.allinPatternString));
		
		assertTrue("10s".matches(Constants.cardPatternString));
		assertFalse("ks".matches(Constants.cardPatternString));
		assertFalse("7".matches(Constants.cardPatternString));
		
	}

}
