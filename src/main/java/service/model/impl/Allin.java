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
