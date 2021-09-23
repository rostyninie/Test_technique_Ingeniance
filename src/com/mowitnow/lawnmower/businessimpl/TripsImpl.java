package com.mowitnow.lawnmower.businessimpl;

import java.util.Arrays;
import java.util.Optional;

import com.mowitnow.lawnmower.business.Trips;
import com.mowitnow.lawnmower.enums.OrientationEnum;
import com.mowitnow.lawnmower.models.Mower;

public class TripsImpl implements Trips {
	
	public static final String LEFT = "G";
	public static final String RIGHT = "D";
	public static final String FORWARD = "A";

	/**
	 * move the mower forward
	 */
	@Override
	public void moveForward(Mower mower) {
		
		if(mower.getOrientation().equals(OrientationEnum.N) || mower.getOrientation().equals(OrientationEnum.S)) {
			// move on Y axis
			this.moveToY(mower);
		}else {
			// move on X axis
			this.moveToX(mower);
		}
		
	}
	
	/**
	 * move to WEST or EAST
	 * @param mowerX
	 */
	private void moveToX(Mower mowerX) {
		if(this.canMoveX(mowerX)) {
			if(mowerX.getOrientation().equals(OrientationEnum.E)) {
				// move to the East
				mowerX.setX(mowerX.getX()+1);
			}else {
				//move to the West
				mowerX.setX(mowerX.getX()-1);
			}
		}
	}
	
	/**
	 * move to NORTH or SOUTH
	 * @param mowerY
	 */
	private void moveToY(Mower mowerY) {
		if(this.canMoveY(mowerY)) {
			if(mowerY.getOrientation().equals(OrientationEnum.N)) {
				//move to the North
				mowerY.setY(mowerY.getY()+1);
			}else {
				// move to the South
				mowerY.setY(mowerY.getY()-1);
			}
		}
	}

	/**
	 * check if mower can move
	 * @param mower
	 * @return
	 */
	private boolean canMoveX(Mower mower) {
		if(mower.getOrientation().equals(OrientationEnum.W)) {
			//mower can move to the West only if x is less or equal to 0
			return (mower.getX()-1) >= 0 ;
		}else {
			//mower can move to the East only if x is greater than or equal to Max erea X
			return (mower.getX()+1) <= mower.getErea().getMaxX() ;
		}
		
	}
	
	/**
	 * 
	 * @param mower
	 * @return
	 */
	private boolean canMoveY(Mower mower) {
		if(mower.getOrientation().equals(OrientationEnum.S)) {
			//mower can move to the South only if y is less or equal to 0
			return (mower.getY()-1) >= 0 ;
		}else {
			//mower can move to the North only if y is greater than or equal to Max erea Y
			return (mower.getY()+1) <= mower.getErea().getMaxY() ;
		}
		
	}

	
	/**
	 * change orientation of mower
	 */
	@Override
	public void changeOrientation(Mower mower, String direction) {
		
		int orientationDirection = 0;
		if(direction.equals(LEFT)) {
			if(mower.getOrientation().getDirection()==1) {
				// if actual orientation in North and we want to move to the left, get West orientation
				orientationDirection = 4;
			}else {
				orientationDirection = mower.getOrientation().getDirection() - 1;
			}
			
		}else {
			if(mower.getOrientation().getDirection()==4) {
				// if actual orientation in West and we want to move to the right, get North orientation
				orientationDirection = 1;
			}else {
				orientationDirection = mower.getOrientation().getDirection() + 1;
			}
		}
		
		mower.setOrientation(getMowerNewOrientation(orientationDirection));
	}
	
	/**
	 * get mower new oreintation
	 * @param orientationDirection
	 * @return
	 */
	private OrientationEnum getMowerNewOrientation(int orientationDirection) {
		
		Optional<OrientationEnum> optionalOrientation =  Arrays.asList(OrientationEnum.values()).stream()
				.filter(o->o.getDirection() == orientationDirection)
		        .findFirst();
		
		return optionalOrientation.get();
	}
	
	/**
	 * change orientation of mower or move forward
	 */
	@Override
	public void moveMower(Mower mower, String direction) {
		
		if(direction.equals(LEFT) || direction.equals(RIGHT)) {
			// if direction is left or right, change oreintation of mower
			changeOrientation(mower, direction);
		}else {
			// if direction is forward, get forward with mower
			moveForward(mower);
		}
		System.out.printf("= Mower moved, new position => %s %n", mower);
	}
	
	/**
	 * do mower to parcourt all the trips
	 */
	public void doParcourWithMower(Mower mower) {
		
		System.out.printf("=== Do parcour mower=> position:(%s), trips:%s %n", 
				mower.getX()+" "+mower.getY()+" "+mower.getOrientation(), mower.getTrips());
		
		long start = System.currentTimeMillis();
		
		mower.getTrips().forEach(m->{
			moveMower(mower, m);
		});
		
		System.out.printf("=== End parcour. Taked time : %d ms %n",(System.currentTimeMillis()-start));
	}
}
