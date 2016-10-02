package builder;

import service.model.Hand;

public interface HandBuilder {

	Hand build(String handString);
	void setPlayers();
	void setOpen();
	void setBlinds();
	void setWinner();
	void setTime();
}
