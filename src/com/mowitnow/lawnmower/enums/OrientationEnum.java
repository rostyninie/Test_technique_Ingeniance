package com.mowitnow.lawnmower.enums;

public enum OrientationEnum {
	N(1),
	E(2),
	S(3),
	W(4);
	
	private int direction;
	
	private OrientationEnum(int direction) {
		this.direction = direction;
	}
	
	public int getDirection() {
		return this.direction;
	}

}
