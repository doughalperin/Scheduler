/**
 * @version 1.0
 */
package com.doughalperin.scheduler;

import java.io.IOException;

import com.doughalperin.scheduler.Schedule.FileEncoding;

/**
 * 
 * Starting point for Track Management 
 * 
 *
 */
public class Scheduler {

    /**
     * 
     * The starting point for Track Management - instantiating the Schedule instance that does the work and then outputting the results.
     * <p>
     * Command line properties that are read:
     * <ul>
     * <li>file - the name of the input file containing Session information.  This is required.</li>
     * <li>encoding - the type of encoding of the input file.  This is optional.  The only format currently supported is <i>UTF8</i></li>
     * </ul>
     * </p>
     * 
     * @param args unused - use -D options on command line
     * 
     */
    public static void main(String[] args) {
	System.out.println("Welcome to the Conference Track Manager!");
	System.out.flush();
	
	String file = System.getProperty( "file");
	if (file == null) {
	    System.err.println("File listing Conference Tracks not specified.");
	    return;
	}

	//Confirm support
	FileEncoding encoding;
	try {
	    encoding = FileEncoding.getFileEncodingByName(System.getProperty( "encoding"));
	} catch (IllegalArgumentException e1) {
	    System.err.println("Unsupported File Encoding specified.");
	    return;
	}

	System.out.println("\nInput file is " + file + " with encoding " + encoding.getName());
	System.out.println("Scheduling Strategy is " + Config.DEFAULT_STRATEGY.getClass().getSimpleName() + "\n");
	//assure we see welcome first
	System.out.flush();
	
	Schedule schedule = new Schedule();
	schedule.setStrategy(Config.DEFAULT_STRATEGY);
	try {
	    schedule.setSessionsFromFile(file, encoding);
	    schedule.setBlocks(Config.BLOCKS);
	    schedule.makeSchedule();
	} catch (IllegalArgumentException e) {
	    System.err.println(e.getMessage());
	    return;
	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	    return;
	}
	
	Track tracks[] = schedule.getTracks();
	for (Track track : tracks) {
	    System.out.println(track.getLabel());
	    for (ScheduleBlock sb : track.getScheduleBlocks()) {
		for (Session s : sb.getSessions())
		    System.out.println((new TimeOffset(s.getStartOffset())) + " " + s.getName() 
			    + (s.isBlockLevelSession() ? "" : " [" + s.getMinutes() + "min]"));
	    }
	    System.out.println();
	}
    }

}
