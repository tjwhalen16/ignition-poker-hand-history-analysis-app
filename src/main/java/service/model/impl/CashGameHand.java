package service.model.impl;

import java.sql.Timestamp;
import java.util.List;

import service.model.Hand;

public class CashGameHand implements Hand {

	private List<Player> players;
	private Open open;
	private Blinds blinds;
	private Player winner;
	private Timestamp time;
	
	
	public List<Player> getPlayers() {
		return players;
	}
	public void setPlayers(List<Player> players) {
		this.players = players;
	}
	public Open getOpen() {
		return open;
	}
	public void setOpen(Open open) {
		this.open = open;
	}
	public Blinds getBlinds() {
		return blinds;
	}
	public void setBlinds(Blinds blinds) {
		this.blinds = blinds;
	}
	public Player getWinner() {
		return winner;
	}
	public void setWinner(Player winner) {
		this.winner = winner;
	}
	public Timestamp getTime() {
		return time;
	}
	public void setTime(Timestamp time) {
		this.time = time;
	}
	
	
}
