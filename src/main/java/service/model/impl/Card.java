package service.model.impl;

public class Card {
	private CardFaceEnum face;
	private CardSuitEnum suit;
	
	public Card(String cardString) {
		face = CardFaceEnum.fromString(cardString.substring(0, 1));
		suit = CardSuitEnum.fromString(cardString.substring(1));		
	}
	
	public CardFaceEnum getFace() {
		return face;
	}
	
	public CardSuitEnum getSuit() {
		return suit;
	}
}
