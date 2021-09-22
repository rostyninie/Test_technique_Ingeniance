package com.mowitnow.lawnmower;

import java.io.IOException;
import java.util.List;

import com.mowitnow.lawnmower.business.Trips;
import com.mowitnow.lawnmower.businessimpl.TripsImpl;
import com.mowitnow.lawnmower.models.Mower;
import com.mowitnow.lawnmower.utils.MowerUtils;

public class App {
	
	public static final String PATH = "./src/COMMANDES.txt";

	public static void main(String[] args) {
		
		Trips trip = new TripsImpl();
		
		try {
			// read commands from file
			List<String> lines = MowerUtils.readCommandesFile(PATH);
			
			if(!lines.isEmpty()) {
			
				// convert commands to mower
				List<Mower> mowers = MowerUtils.convertCommandToMower(lines);
				
				// make mower parcour erea
				
				mowers.forEach(m->{
					trip.doParcourWithMower(m);
					System.out.println(m);
				});
			
			}
		} catch (IOException e) {
			System.out.println("ERROR: when getting command file : "+e);
		}
	}
}
