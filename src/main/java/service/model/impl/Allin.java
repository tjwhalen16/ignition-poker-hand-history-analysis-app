package service.model.impl;

public class Allin {
	private Player player;
	private Board board;
	private double size;
	
	public Allin(Player player, Board board, double size) {
		this.player = player;
		this.board = board;
		this.size = size;
	}
	
	@Override
	public String toString() {
		return "Player at position " + player.getPosition() + " went all in with " + size + ". He was holding " + player.getHand().getCard1() + ", " + player.getHand().getCard2() + " on a board of: " + board;
	}
	
	public HoleCards getHand() {
		return player.getHand();
	}
	
	public Board getBoard() {
		return board;
	}
	
	public double getSize() {
		return size;
	}
}
