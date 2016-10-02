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
}
