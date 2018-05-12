package service.model.impl;

import service.model.Hand;

public class AllinHand extends Hand {
	private Allin allin;
	private boolean allinHappened = false;
	
	public void setAllin(Allin allin) {
		this.allin = allin;
		allinHappened = true;
	}
	
	public Allin getAllin() {
		return allin;
	}
	
	public boolean didAllinHappen() {
		return allinHappened;
	}
}
