package service.model.impl;

import java.sql.Timestamp;

public class Open {
	
	private OpenTypeEnum type;
	private int numPlayers;
	private PositionEnum position; // TODO change to enum?
	private Cards hand;
	private double stackSize; // In BBs
	private double openSize; // In BBs
	
	// fields i'm unsure about
	private Timestamp time;
	
	public Open(OpenTypeEnum type, int numPlayers, Player player, double size, Timestamp time) {
		
		this.type = type;
		this.numPlayers = numPlayers;
		this.position = player.getPosition();
		this.hand = player.getHand();
		this.stackSize = player.getStack();
		this.openSize = size;
		this.time = time;		
	}	
	
	@Override
	public String toString() {
		return "Open [type=" + type + ", numPlayers=" + numPlayers + ", position=" + position + ", hand=" + hand
				+ ", stackSize=" + stackSize + ", openSize=" + openSize + ", time=" + time + "]";
	}

	public OpenTypeEnum getType() {
		return type;
	}

	public int getNumPlayers() {
		return numPlayers;
	}

	public PositionEnum getPosition() {
		return position;
	}

	public Cards getHand() {
		return hand;
	}

	public double getStackSize() {
		return stackSize;
	}

	public double getOpenSize() {
		return openSize;
	}

	public Timestamp getTime() {
		return time;
	}
	
	
	
}
