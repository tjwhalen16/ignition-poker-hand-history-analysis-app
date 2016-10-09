package service.model.impl;

import java.sql.Timestamp;
import java.util.List;

import service.model.Blinds;
import service.model.Hand;

/**
 * One hand in a cash game
 * @author Tyler
 *
 */
public class CashGameHand implements Hand {

	private List<Player> players;
	private Open open;
	private Blinds blinds;
	private Player winner;
	private Timestamp time;
	
	
	/**
	 * Returns the player in this hand with the position
	 * @param position - position that desired player has
	 * @return Player with PositionEnum position
	 */
	public Player getPlayerWithPosition(PositionEnum position) {
		for (Player player : players) {
			if (player.getPosition().equals(position)) {
				return player;
			}
		}
		
		throw new IllegalArgumentException("No player with position" + position);
	}
	
	/**
	 * Gets the number of players dealt cards in the hand
	 * @return int - the number of players in the hand
	 */
	public int getNumberPlayers() {
		return players.size();
	}
	
	
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
