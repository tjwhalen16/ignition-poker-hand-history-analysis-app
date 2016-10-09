package service.model.impl;

/**
 * Enum for working with playing card faces
 * @author Tyler
 *
 */
public enum CardFaceEnum {

	TWO("2"),
	THREE("3"),
	FOUR("4"),
	FIVE("5"),
	SIX("6"),
	SEVEN("7"),
	EIGHT("8"),
	NINE("9"),
	TEN("T"),
	JACK("J"),
	QUEEN("Q"),
	KING("K"),
	ACE("A");
	
	private final String face;
	
	private CardFaceEnum(String face) {
		this.face = face;
	}
	
	@Override
	public String toString() {
		return face;
	}
	
	/**
	 * Undo the toString, aka get the enum value associated with each enum values face string
	 * @param face - string turn into enum
	 * @return CardFaceEnum
	 * @throws IllegalArgumentException - if face isn't an enum choice
	 */
	public static CardFaceEnum fromString(String face) {
		for (CardFaceEnum cardFace : values()) {
			if (cardFace.toString().equalsIgnoreCase(face)) {
				return cardFace;
			}
		}
		
		throw new IllegalArgumentException("Invalid CardFaceEnum string");
	}
}
