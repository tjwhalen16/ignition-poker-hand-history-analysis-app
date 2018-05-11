package service.model.impl;

public class Player {
	private HoleCards hand;
	private double stack; // In dollars
	private PositionEnum position; // TODO enum
	
	public Player(double stack, PositionEnum position) {
		super();
		this.stack = stack;
		this.position = position;
	}

	public HoleCards getHand() {
		return hand;
	}

	public void setHand(HoleCards hand) {
		this.hand = hand;
	}

	public double getStack() {
		return stack;
	}

	public void setStack(double stack) {
		this.stack = stack;
	}

	public PositionEnum getPosition() {
		return position;
	}

	public void setPosition(PositionEnum position) {
		this.position = position;
	}	
}
