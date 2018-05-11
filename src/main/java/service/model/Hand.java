package service.model;

import java.sql.Timestamp;
import java.util.List;

import service.model.Blinds;
import service.model.impl.Open;
import service.model.impl.Player;
import service.model.impl.PositionEnum;

public abstract class Hand {
	private List<Player> players;
	private Blinds blinds;
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
		
		throw new IllegalArgumentException("No player with position: " + position);
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
	public Blinds getBlinds() {
		return blinds;
	}
	public void setBlinds(Blinds blinds) {
		this.blinds = blinds;
	}
	public Timestamp getTime() {
		return time;
	}
	public void setTime(Timestamp time) {
		this.time = time;
	}		
}
