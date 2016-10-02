package builder.impl;

import builder.HandBuilder;
import service.model.Hand;
import service.model.impl.CashGameHand;

public class CashGameHandBuilder implements HandBuilder {
	
	public CashGameHandBuilder() {
	}
	
	@Override
	public Hand build(String handString) {
		CashGameHand hand = new CashGameHand();
		
		return hand;
	}
	
	@Override
	public void setPlayers() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setOpen() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setBlinds() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setWinner() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setTime() {
		// TODO Auto-generated method stub

	}

}
