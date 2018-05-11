package service.model.impl;

import service.model.Hand;

/**
 * One hand in a cash game with an open
 * @author Tyler
 *
 */
public class CashGameHand extends Hand {
	private Open open;
	private Player winner;
	
	public Open getOpen() {
		return open;
	}
	public void setOpen(Open open) {
		this.open = open;
	}

	public Player getWinner() {
		return winner;
	}
	public void setWinner(Player winner) {
		this.winner = winner;
	}	
}
