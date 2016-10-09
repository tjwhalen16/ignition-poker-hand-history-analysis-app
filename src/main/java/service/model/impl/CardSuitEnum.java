package service.model.impl;

/**
 * Enum for working with playing card suits
 * @author Tyler
 *
 */
public enum CardSuitEnum {

	CLUB("c"),
	DIAMOND("d"),
	HEART("h"),
	SPADE("s");
	
	private final String suit;
	
	private CardSuitEnum(String suit) {
		this.suit = suit;
	}
	
	@Override
	public String toString() {
		return suit;
	}
	
	/**
	 * Undo the toString, aka get the enum value associated with each enum values face string
	 * @param suit - string turn into enum
	 * @return CardSuitEnum
	 * @throws IllegalArgumentException - if suit isn't an enum choice
	 */
	public static CardSuitEnum fromString(String suit) {
		for (CardSuitEnum cardSuit : values()) {
			if (cardSuit.toString().equalsIgnoreCase(suit)) {
				return cardSuit;
			}
		}
		
		throw new IllegalArgumentException("Invalid CardSuitEnum string");
	}
	
}
