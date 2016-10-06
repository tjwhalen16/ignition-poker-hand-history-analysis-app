package builder;

import java.util.List;

import service.model.Hand;

public interface HandBuilder {

	Hand build(String handString);
	public int setPlayers(Hand hand, List<String> handStrings, int lineNumber);
	void setOpen();
	void setBlinds();
	void setWinner();
	int setTime(Hand hand, List<String> handStrings, int lineNumber);
}
