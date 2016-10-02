package service.model.impl;

import java.util.Date;

public class Open {
	
	//TODO move somewhere else?
	public enum Type {
		RAISE, LIMP, WALK;
	}
	
	private Type type;
	private int numPlayers;
	private Player player;
	private String position; //TODO change to enum?
	private Cards hand;
	private double stack; //in BBs
	private double size; //in BBs
	
	//fields i'm unsure about
	private Date date;
}
