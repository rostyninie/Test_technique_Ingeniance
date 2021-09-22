package com.mowitnow.lawnmower.models;

import java.util.List;

import com.mowitnow.lawnmower.enums.OrientationEnum;

public class Mower {

	private int x;
	private int y;
	private OrientationEnum orientation;
	private List<String> trips;
	private Erea erea;
	
	public Mower(int x, int y, OrientationEnum orientation, List<String> trips, Erea erea) {
		this.x = x;
		this.y = y;
		this.orientation = orientation;
		this.trips = trips;
		this.erea = erea;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public OrientationEnum getOrientation() {
		return orientation;
	}

	public void setOrientation(OrientationEnum orientation) {
		this.orientation = orientation;
	}

	public List<String> getTrips() {
		return trips;
	}

	public void setTrips(List<String> trips) {
		this.trips = trips;
	}

	public Erea getErea() {
		return erea;
	}

	public void setErea(Erea erea) {
		this.erea = erea;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((orientation == null) ? 0 : orientation.hashCode());
		result = prime * result + ((trips == null) ? 0 : trips.hashCode());
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Mower other = (Mower) obj;
		if (orientation != other.orientation)
			return false;
		if (trips == null) {
			if (other.trips != null)
				return false;
		} else if (!trips.equals(other.trips))
			return false;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return this.x+" "+this.y+" "+this.orientation.name();
	}
	
	
	
}
