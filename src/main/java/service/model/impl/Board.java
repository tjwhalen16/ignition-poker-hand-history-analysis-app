package service.model.impl;

import java.util.ArrayList;
import java.util.List;

public class Board {
	private List<Card> cards;
	
	public Board(String[] cards) {
		this.cards = new ArrayList<>();
		for (String card : cards) {
			this.cards.add(new Card(card));
		}
	}
	
	@Override
	public String toString() {
		String board = "[";
		for (Card card : cards) {
			board += " " + card;
		}
		return board + "]";
	}
}
