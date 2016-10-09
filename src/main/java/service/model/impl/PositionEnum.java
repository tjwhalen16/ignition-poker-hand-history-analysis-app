package service.model.impl;

/**
 * Enum to represent player's  positions at the table
 * @author Tyler
 *
 */
public enum PositionEnum {

	UTG("UTG"),
	UTG1("UTG+1"),
	UTG2("UTG+2"),
	UTG3("UTG+3"),
	UTG4("UTG+4"),
	UTG5("UTG+5"),
	BTN("Dealer"),
	SB("Small Blind"),
	BB("Big Blind");
	
	private final String position;
	
	private PositionEnum(String position) {
		this.position = position;
	}
	
	@Override
	public String toString() {
		return position;
	}
	
	/**
	 * Undo the toString, aka get the enum value associated with each enum values position string
	 * @param position - string turn into enum
	 * @return PositionEnum
	 * @throws IllegalArgumentException - if position isn't an enum choice
	 */
	public static PositionEnum fromString(String position) {
		for(PositionEnum positionEnum : PositionEnum.values()) {
			if (positionEnum.toString().equalsIgnoreCase(position)) {
				return positionEnum;
			}
		}
		
		throw new IllegalArgumentException("Invalid PositionEnum string");
	}
	
	
}
