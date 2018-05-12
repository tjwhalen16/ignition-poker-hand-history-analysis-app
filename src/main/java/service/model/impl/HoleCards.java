package service.model.impl;

/**
 * Class to represent a hand of cards
 * @author Tyler
 *
 */
public class HoleCards {
	private Card card1;
	private Card card2;
	private final String hand;
	
	/**
	 * Constructs a hand using string array
	 * @param cards - array of size 2
	 * i.e. {2c, As}
	 */
	public HoleCards(String[] cards) {
		this.card1 = new Card(cards[0]);
		this.card2 = new Card(cards[1]);		
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
		
		if (card1.getFace().equals(card2.getFace())) {
			// hand is a pocket pair
			hand = card1.getFace().toString() + card2.getFace().toString();
		} else {
			// hand is not paired
			// Put bigger face first
			if (card1.getFace().compareTo(card2.getFace()) > 0) {
				hand = card1.getFace().toString() + card2.getFace().toString();
			} else {
				hand = card2.getFace().toString() + card1.getFace().toString();
			}
			
			// determine if hand is suited
			if (card1.getSuit().equals(card2.getSuit())) { // The cards suits match
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
	
	public Card getCard1() {
		return card1;
	}
	
	public Card getCard2() {
		return card2;
	}
}
