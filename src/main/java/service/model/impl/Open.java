package service.model.impl;

import java.sql.Timestamp;

public class Open {
	private Player player;
	private OpenTypeEnum type;
	private int numPlayers;
	private double openSize; // In BBs
	
	// fields i'm unsure about
	private Timestamp time;
	
	public Open(OpenTypeEnum type, int numPlayers, Player player, double size, Timestamp time) {
		
		this.type = type;
		this.numPlayers = numPlayers;
		this.player = player;
		this.openSize = size;
		this.time = time;		
	}	
	
	@Override
	public String toString() {
		return "Open [type=" + type + ", numPlayers=" + numPlayers + ", position=" + player.getPosition() + ", hand=" + player.getHand()
				+ ", stackSize=" + player.getStack() + ", openSize=" + openSize + ", time=" + time + "]";
	}

	public OpenTypeEnum getType() {
		return type;
	}

	public int getNumPlayers() {
		return numPlayers;
	}

	public PositionEnum getPosition() {
		return player.getPosition();
	}

	public Cards getHand() {
		return player.getHand();
	}

	public double getStackSize() {
		return player.getStack();
	}

	public double getOpenSize() {
		return openSize;
	}

	public Timestamp getTime() {
		return time;
	}	
}
