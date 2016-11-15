package builder;

import java.util.List;

import service.model.Hand;

public interface HandBuilder {
	Hand build(String handString);
	public int setPlayers(Hand hand, List<String> handStrings, int lineNumber);
	public int setOpen(Hand hand, List<String> handStrings, int lineNumber);
	public int setBlinds(Hand hand, List<String> handStrings, int lineNumber);
	public void setWinner(Hand hand, List<String> handStrings, int lineNumber);
	public int setTime(Hand hand, List<String> handStrings, int lineNumber);
}
