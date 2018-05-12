package service.model.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Board {
	private List<Card> cards;
	
	public Board() {
		this.cards = new ArrayList<>();
	}
	
	public Board(String[] cards) {
		this.cards = new ArrayList<>();
		for (String card : cards) {
			this.cards.add(new Card(card));
		}
	}
	
	public void addCardString(String card) {
		cards.add(new Card(card));
	}
	
	@Override
	public String toString() {		
		return cards.stream()
					.map(Card::toString)
				    .collect(Collectors.joining(" "));
	}
}
