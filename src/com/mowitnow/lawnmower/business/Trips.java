package com.mowitnow.lawnmower.business;

import com.mowitnow.lawnmower.models.Mower;

public interface Trips {
	
	void moveForward(Mower mower);
	
    void changeOrientation(Mower mower, String direction);
	
	void moveMower(Mower mower, String direction);
	
	void doParcourWithMower(Mower mower);
}
