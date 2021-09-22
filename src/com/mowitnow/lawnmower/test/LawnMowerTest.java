package com.mowitnow.lawnmower.test;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.mowitnow.lawnmower.business.Trips;
import com.mowitnow.lawnmower.businessimpl.TripsImpl;
import com.mowitnow.lawnmower.enums.OrientationEnum;
import com.mowitnow.lawnmower.models.Erea;
import com.mowitnow.lawnmower.models.Mower;
import com.mowitnow.lawnmower.utils.MowerUtils;

class LawnMowerTest {
	
	public static final String PATH = "./src/COMMANDES.txt";
	
	/**
	 * Test Global qui vérifie les objectifs demandés par l'exercice.
	 * @throws IOException
	 */
	@Test
	public void testTheObjectifsOfInterview() throws IOException {
		Trips trip = new TripsImpl();
		List<String> lines = MowerUtils.readCommandesFile(PATH);
		List<Mower> mowers = MowerUtils.convertCommandToMower(lines);
						mowers.forEach(m->{
							trip.doParcourWithMower(m);
						});
						
		assertEquals("1 3 N", mowers.get(0).toString());
		assertEquals("5 1 E", mowers.get(1).toString());
	}
	
	/**
	 * Mower stub for setup data
	 * @param type
	 * @return
	 */
	private Mower getMowerStub(int type) {
		Trips trip= new TripsImpl();
		if(type == 1) {
			List<String> trips = new ArrayList<>() ;
			trips.add("A");
			return new Mower(2, 3, MowerUtils.getInitialOrientation("N"), trips, new Erea(5, 5)) ;
		}else if(type == 2) {
			List<String> trips = new ArrayList<>() ;
			trips.add("A");
			return new Mower(2, 3, MowerUtils.getInitialOrientation("S"), trips, new Erea(5, 5)) ;
		}else if(type == 3) {
			List<String> trips = new ArrayList<>() ;
			trips.add("A");
			return new Mower(2, 3, MowerUtils.getInitialOrientation("W"), trips, new Erea(5, 5)) ;
		}else if(type == 4) {
			List<String> trips = new ArrayList<>() ;
			trips.add("A");
			return new Mower(2, 3, MowerUtils.getInitialOrientation("E"), trips, new Erea(5, 5)) ;
		}else if(type == 5) {
			List<String> trips = new ArrayList<>() ;
			trips.add("G");
			return new Mower(2, 3, MowerUtils.getInitialOrientation("E"), trips, new Erea(5, 5)) ;
		}else if(type == 6) {
			List<String> trips = new ArrayList<>() ;
			trips.add("D");
			return new Mower(2, 3, MowerUtils.getInitialOrientation("E"), trips, new Erea(5, 5)) ;
		}else if(type == 7) {
			List<String> trips = new ArrayList<>() ;
			trips.add("A");
			return new Mower(0, 5, MowerUtils.getInitialOrientation("N"), trips, new Erea(5, 5)) ;
		}
		else if(type == 8) {
			List<String> trips = new ArrayList<>() ;
			trips.add("G");
			trips.add("A");
			return new Mower(2, 3, MowerUtils.getInitialOrientation("W"), trips, new Erea(5, 5)) ;
		}else if(type == 9) {
			List<String> trips = new ArrayList<>() ;
			trips.addAll(MowerUtils.convertStringToTrips("GAGAGAGAA"));
			return new Mower(1, 2, MowerUtils.getInitialOrientation("N"), trips, new Erea(5, 5)) ;
		}
		else {
			return null;
		}
	}

	@Test
	public void getInitialOrientationTest() {
		Trips trip= new TripsImpl();
		Mower mower = new Mower(2, 3, MowerUtils.getInitialOrientation("S"), null, new Erea(5, 5));
		assertEquals(OrientationEnum.S, mower.getOrientation());
	}

	/**
	 * test move mower to North
	 */
	@Test
	public void moveMowerForwardToNorthTest() {
		Trips trip= new TripsImpl();
		Mower mower = getMowerStub(1);
		trip.moveForward(mower);
		assertEquals(4, mower.getY());
	}
	
	/**
	 * test move mower to south
	 */
	@Test
	public void moveMowerForwardToSouthTest() {
		Trips trip= new TripsImpl();
		Mower mower = getMowerStub(2);
		trip.moveForward(mower);
		assertEquals(2, mower.getY());
	}
	
	/**
	 * test move mower to West
	 */
	@Test
	public void moveMowerForwardToWestTest() {
		Trips trip= new TripsImpl();
		Mower mower = getMowerStub(3);
		trip.moveForward(mower);
		assertEquals(1, mower.getX());
	}

	/**
	 * test move mower to East
	 */
	@Test
	public void moveMowerForwardToEastTest() {
		Trips trip= new TripsImpl();
		Mower mower = getMowerStub(4);
		trip.moveForward(mower);
		assertEquals(3, mower.getX());
	}
	
	/**
	 * test change orientation of mower to LEFT
	 */
	@Test
	public void changeOrientationOfMowerToLeftTest() {
		Trips trip= new TripsImpl();
		Mower mower = getMowerStub(5);
		trip.changeOrientation(mower, mower.getTrips().get(0));
		assertEquals(OrientationEnum.N, mower.getOrientation());
	}
	
	/**
	 * test change orientation of mower to RIGHT
	 */
	@Test
	public void changeOrientationOfMowerToRightTest() {
		Trips trip= new TripsImpl();
		Mower mower = getMowerStub(6);
		trip.changeOrientation(mower, mower.getTrips().get(0));
		assertEquals(OrientationEnum.S, mower.getOrientation());
	}
	
	/**
	 * test if move is not possible when moving get out of erea
	 */
	@Test
	public void mowerNotMoveIfXOrYisOutOfEreaTest() {
		Trips trip= new TripsImpl();
		Mower mower = getMowerStub(7);
		trip.moveForward(mower);
		assertEquals(5, mower.getY());
	}
	
	/**
	 * test move mower 
	 */
	@Test
	public void moveMowerTest() {
		Trips trip= new TripsImpl();
		Mower mower = getMowerStub(8);
		trip.moveMower(mower, mower.getTrips().get(0));
		trip.moveMower(mower, mower.getTrips().get(1));
		assertEquals(OrientationEnum.S, mower.getOrientation());
		assertEquals(2, mower.getY());
	}
	
	/**
	 * test mower parcour all Trips
	 */
	@Test
	public void mowerParcourAllTripsTest() {
		Trips trip= new TripsImpl();
		Mower mower = getMowerStub(9);
		trip.doParcourWithMower(mower);
		assertEquals(OrientationEnum.N, mower.getOrientation());
		assertEquals(3, mower.getY());
		assertEquals(1, mower.getX());
	}
	
	//=================== Test Utils part =====================
	
	/**
	 * test validate trips with white space
	 */
	@Test
	public void validateTripsWithInValidWhiteSpaceTripsTest() {
		String trips = "GAGAG AGAA ";
		boolean valid = MowerUtils.validateTrips(trips);
		assertEquals(false, valid);
	}
	
	/**
	 * test validate Trips With InValid Direction
	 */
	@Test
	public void validateTripsWithInValidDirectionTripsTest() {
		String trips = "GAGAGEAGAA";
		boolean valid = MowerUtils.validateTrips(trips);
		assertEquals(false, valid);
	}
	
	/**
	 * test vvalidate Trips With valid Directions
	 */
	@Test
	public void validateTripsWithValidTripsTest() {
		String trips = "GAGAGAGAA";
		boolean valid = MowerUtils.validateTrips(trips);
		assertEquals(true, valid);
	}
	
	/**
	 * invalide test for erea
	 */
	@Test
	public void validateInvalidEreaTest() {
		String erea1 = "4  5";
		String erea2 = "4 A";
		
		boolean validErea1 = MowerUtils.validateErea(erea1);
		boolean validErea2 = MowerUtils.validateErea(erea2);
		assertEquals(false, validErea1);
		assertEquals(false, validErea2);
	}
	
	/**
	 * valide test for erea
	 */
	@Test
	public void validateValidEreaTest() {
		String erea1 = "4 5";
		
		boolean validErea1 = MowerUtils.validateErea(erea1);
		assertEquals(true, validErea1);
	}
	
	/**
	 * Invalide test for position and oreintation
	 */
	@Test
	public void validatePositionAndOrientationWithBadPostionAndOreintationTest() {
		String postition1 = "4  5 N";
		String postition2 = "4 A";
		String postition3 = "4 4 O";
		
		boolean validPostition1 = MowerUtils.validateInitialPositionAndOrientation(postition1);
		boolean validPostition2 = MowerUtils.validateInitialPositionAndOrientation(postition2);
		boolean validPostition3 = MowerUtils.validateInitialPositionAndOrientation(postition3);
		assertEquals(false, validPostition1);
		assertEquals(false, validPostition2);
		assertEquals(false, validPostition3);
	}
	
	/**
	 * Valid test for position and orientation
	 */
	@Test
	public void validatePositionAndOrientationWithValidPostionAndOreintationTest() {
		String postition1 = "4 5 N";
		
		boolean validPostition1 = MowerUtils.validateInitialPositionAndOrientation(postition1);
		assertEquals(true, validPostition1);
	}
	
	/**
	 * load commands from file test
	 * @throws IOException
	 */
	@Test
	public void readCommandFileTest() throws IOException {
		List<String> lines = MowerUtils.readCommandesFile("./src/COMMANDES.txt");
		assertEquals(5, lines.size());
	}
	
	/**
	 * Test convert command line file to mower
	 * @throws IOException
	 */
	@Test
	public void getMowerfromCommandLineFileTest() throws IOException {
		List<String> lines = MowerUtils.readCommandesFile(PATH);
		List<Mower> mowers = MowerUtils.convertCommandToMower(lines);
		assertEquals(2, mowers.size());
	}
	

}
