package service.model;

import java.sql.Timestamp;
import java.util.List;

import service.model.impl.Blinds;
import service.model.impl.Open;
import service.model.impl.Player;

public interface Hand {
	public List<Player> getPlayers();
	public void setPlayers(List<Player> players);
	public Open getOpen();
	public void setOpen(Open open);
	public Blinds getBlinds();
	public void setBlinds(Blinds blinds);
	public Player getWinner();
	public void setWinner(Player winner);
	public Timestamp getTime();
	public void setTime(Timestamp time);
}
