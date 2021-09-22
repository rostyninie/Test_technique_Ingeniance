package com.mowitnow.lawnmower.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.mowitnow.lawnmower.enums.OrientationEnum;
import com.mowitnow.lawnmower.models.Erea;
import com.mowitnow.lawnmower.models.Mower;

public class MowerUtils {
	
	public static final String MOVE_TYPE ="ADG";
public static final String ORIENTATION_TYPE ="NWSE";
	
	/**
	 * convert commands trips string to list of command
	 * @param stTrips
	 * @return
	 */
	public static List<String> convertStringToTrips(String stTrips){
		
		if(stTrips==null || stTrips.equals(""))
		    return Collections.emptyList();
			
		return convertStringToListOfString(stTrips.trim());
	}
	
	/**
	 * Validate command to trips
	 * @param stTrips
	 * @return
	 */
	public static boolean validateTrips(String stTrips) {
		
		if(stTrips==null || stTrips.equals(""))
			return false;
		
		stTrips= stTrips.trim();
		
		return !convertStringToListOfString(stTrips).stream().anyMatch(s-> MOVE_TYPE.indexOf(s)==-1);
	}
	
	/**
	 * read commands to the file commands
	 * @param path
	 * @return
	 * @throws IOException
	 */
	public static List<String> readCommandesFile(String path) throws IOException{
		
		System.out.printf("=== reading commands file path : %s ... %n", path);
		
		long start = System.currentTimeMillis();
		
		Path commandPathFile = Paths.get(path);
		List<String> listComand = new ArrayList<>();
		
		try(Stream<String> listComandStr = Files.lines(commandPathFile)){
			listComand = listComandStr.collect(Collectors.toList());
		}
		
		System.out.printf("=== End read commands files. Taked time : %d ms %n", 
				(System.currentTimeMillis()-start));
		
		return listComand;
		
	}
	
	/**
	 * validate erea
	 * @param erea
	 * @return
	 */
	public static boolean validateErea(String erea) {
		
		if(erea==null || erea.equals(""))
			return false;
		
		erea = erea.trim();
		
		if(!erea.contains(" ") || convertStringToListOfString(erea).stream().filter(e-> e.equals(" ")).count()!=1)
			return false;
		
		String[] ereaTb = erea.split(" ");
		
		if(!isNumeric(ereaTb[0]) || !isNumeric(ereaTb[1]))
			return false;
		
		return true;
		
	}
	
/**
 * Validate initial position and orientation
 * @param position
 * @return
 */
public static boolean validateInitialPositionAndOrientation(String position) {
		
		if(position==null || position.equals(""))
			return false;
		
		
		position = position.trim();
		long nbOccurentWhiteSpace = convertStringToListOfString(position).stream().filter(e-> e.equals(" ")).count();
		
		
		if(!position.contains(" ") || nbOccurentWhiteSpace != 2)
			return false;
		
		String[] positionTb = position.split(" ");
		
		if(!isNumeric(positionTb[0]) || !isNumeric(positionTb[1]) || !ORIENTATION_TYPE.contains(positionTb[2]))
			return false;
		
		return true;
		
	}
	
/**
 * Convert command to mower
 * @param listCommand
 * @return
 */
	public static List<Mower> convertCommandToMower(List<String> listCommand) {
		
		List<Mower> mowers = new ArrayList<>();
		
		System.out.println("=== Convertion of commands to mower ...");
		
		long start = System.currentTimeMillis();
		
		if(!listCommand.isEmpty()) {
			String ereaStr = listCommand.get(0).trim();
			if(validateErea(ereaStr)) {
				String[] ereaTb = ereaStr.split(" ");
				Erea erea = new Erea(Integer.parseInt(ereaTb[0]), Integer.parseInt(ereaTb[1]));
				List<String> commandPositionAndTrips = listCommand.subList(1, listCommand.size());
				
				if(commandPositionAndTrips.size()%2==0) {
					for(int i=0; i<commandPositionAndTrips.size(); i+=2) {
						
						String lineInitialPostion = commandPositionAndTrips.get(i).trim();
						String lineTrips = commandPositionAndTrips.get(i+1).trim();
						
						if(validateTrips(lineTrips) && validateInitialPositionAndOrientation(lineInitialPostion)) {
							System.out.printf("= Convert command => position:(%s), trips:%s %n",lineInitialPostion, lineTrips);
							String[] positionTb = lineInitialPostion.split(" ");
							int x = Integer.parseInt(positionTb[0]);
							int y = Integer.parseInt(positionTb[1]);
							String orientation = positionTb[2];
							List<String> trips = convertStringToTrips(lineTrips);
							
							Mower mower = new Mower(x, y, getInitialOrientation(orientation), trips, erea);
							mowers.add(mower);
							
						}else {
							System.out.printf("Invalid position %s or trip %s for this mower %n", lineInitialPostion, lineTrips);
						}
						
					}
					
					System.out.printf("=== End Convertion of commands to mower. Taked time : %d ms %n", 
							(System.currentTimeMillis()-start));
				}else {
					System.out.println("One or more posistion or trips is absent for one or more mower!");
				}
				
			}else {
				System.out.println("Erea specification in commands file is not valid!!!");
			}
		}
		
		
		return mowers;
		
	}
	
	/**
	 * check if string is numeric
	 * @param value
	 * @return
	 */
	public static boolean isNumeric(String value) {
				
		    if(value == null || value.equals("")) {
		        return false;
		    }
		    
		    try {
		        Integer.parseInt(value);
		        return true;
		    } catch (NumberFormatException e) {
		        System.out.println("value is not a int");
		    }
		    return false;
	}
	
	
	/**
	 * get Initial orientation
	 */
	public static OrientationEnum getInitialOrientation(String initialOrientation) {
		return OrientationEnum.valueOf(initialOrientation);
		
	}
	
	public static List<String> convertStringToListOfString(String str) {
		char[] charTb = str.toCharArray();
		List<String> strList = new ArrayList<>();
		for(char c: charTb) {
			strList.add(String.valueOf(c));
		}
		return strList;
	}
	
	

}
