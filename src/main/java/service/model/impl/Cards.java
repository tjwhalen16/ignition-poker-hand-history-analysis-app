package service.model.impl;

/**
 * Class to represent a hand of cards
 * @author Tyler
 *
 */
public class Cards {
	private CardFaceEnum face1, face2;
	private CardSuitEnum suit1, suit2;
	private final String hand;
	
	/**
	 * Constructs a hand using string array
	 * @param cards - array of size 2
	 * i.e. {2c, As}
	 */
	public Cards(String[] cards) {
		
		// Store card 1
		String card1 = cards[0];
		face1 = CardFaceEnum.fromString(card1.substring(0, 1));
		suit1 = CardSuitEnum.fromString(card1.substring(1));
		
		// Store card 2
		String card2 = cards[1];
		face2 = CardFaceEnum.fromString(card2.substring(0, 1));
		suit2 = CardSuitEnum.fromString(card2.substring(1));
		
		hand = setHandStringUsingFields();
		
	}
		
	/**
	 * Sets hand string to the following:
	 * Pocket pair: AA, JJ, TT, 33, etc
	 * Suited cards: AJs, 92s, KQs, etc
	 * Off-Suited card: AJo, 92o, KQo, etc
	 * 
	 * Larger face will always be first
	 * 
	 * @return hand - string that represents common poker hand notation
	 */
	private String setHandStringUsingFields() {
		String hand;
		
		if (face1.equals(face2)) {
			// hand is a pocket pair
			hand = face1.toString() + face2.toString();
		} else {
			// hand is not paired
			// Put bigger face first
			if (face1.compareTo(face2) > 0) {
				hand = face1.toString() + face2.toString();
			} else {
				hand = face2.toString() + face1.toString();
			}
			
			// determine if hand is suited
			if (suit1.equals(suit2)) { // The cards suits match
				hand += "s";
			} else { // The cards suits don't match
				hand += "o";
			}
		}
		
		return hand;
	}
	
	@Override
	public String toString() {
		return hand;
	}
}
