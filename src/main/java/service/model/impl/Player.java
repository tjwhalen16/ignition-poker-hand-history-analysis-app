package service.model.impl;

public class Player {

	private Cards hand;
	private double stack; //in dollars
	private String position; //TODO enum
	
	
	public Player(double stack, String position) {
		super();
		this.stack = stack;
		this.position = position;
	}


	public Cards getHand() {
		return hand;
	}


	public void setHand(Cards hand) {
		this.hand = hand;
	}


	public double getStack() {
		return stack;
	}


	public void setStack(double stack) {
		this.stack = stack;
	}


	public String getPosition() {
		return position;
	}


	public void setPosition(String position) {
		this.position = position;
	}
	
	
	
	
	
}
