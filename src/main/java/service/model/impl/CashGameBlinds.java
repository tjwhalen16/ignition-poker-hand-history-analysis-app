package service.model.impl;

import service.model.Blinds;

/**
 * Represent the blinds in a cash game
 * @author Tyler
 *
 */
public class CashGameBlinds implements Blinds {
	private double smallBlind;
	private double bigBlind;	
	
	public CashGameBlinds(double smallBlind, double bigBlind) {
		super();
		this.smallBlind = smallBlind;
		this.bigBlind = bigBlind;		
	}
	
	public double getSmallBlind() {
		return smallBlind;
	}
	public void setSmallBlind(double smallBlind) {
		this.smallBlind = smallBlind;
	}
	
	public double getBigBlind() {
		return bigBlind;
	}
	public void setBigBlind(double bigBlind) {
		this.bigBlind = bigBlind;
	}
	
}
