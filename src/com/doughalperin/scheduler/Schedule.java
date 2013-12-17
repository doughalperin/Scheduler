/**
 * @version 1.0
 */
package com.doughalperin.scheduler;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;


/**
 * The primary class in the Track Management system.  The Schedule provides all the methods that leads to successfully
 * slotting Sessions into Tracks.
 * <p>
 * Note this is not thread-safe.
 * </p>
 *
 */
public class Schedule {
    /**
     * Possible charset encodings of input file
     * <p>
     * Currently only UTF-8 supported
     * </p>
     */
    public enum FileEncoding {
	UTF8("UTF-8");

	public static FileEncoding DEFAULT = Config.DEFAULT_FILE_ENCODING;
	private final String name;
	private FileEncoding(String s) {
	    name = s;
	}

	public static FileEncoding getFileEncodingByName(String n) throws IllegalArgumentException {
	    if (n == null)
		return DEFAULT;
	    for (FileEncoding fe : values()) 
		if (n.equals(fe.name))
		    return fe;
	    throw new IllegalArgumentException("File Encoding not found");
	}

	public String getName() {
	    return name;
	}
    }
    
    private ArrayList<Session> sessions;
    private BlockFormat[] blocks;
    private SchedulingStrategy strategy;
    private Track[] tracks;
    
    /**
     * Instantiates a new empty Schedule 
     */
    public Schedule() {
	sessions = new ArrayList<Session>();
	blocks = new BlockFormat[]{};
	strategy = Config.DEFAULT_STRATEGY;
	tracks = null;
    }



    /**
     * Takes specified input file and reads line by line to get the Sessions that need to be scheduled.
     * 
     * @param file complete path to file to read
     * @param encoding character set in use in the file
     * @throws IllegalArgumentException when file not found or lines of the file are not valid Session data
     * @throws IOException when errors occur in reading the file
     * @see Session
     */
    public void setSessionsFromFile(String file, FileEncoding encoding)
    		throws IllegalArgumentException, IOException {
	
	sessions = new ArrayList<Session>();
	InputStream fis;
	BufferedReader br = null;
	try {
	    String line;
	    fis = new FileInputStream(file);
	    br = new BufferedReader(new InputStreamReader(fis, Charset.forName(encoding.getName())));
	    while ((line = br.readLine()) != null) {
	        Session s;
		try {
		    s = Session.getInstanceFromString(line);
		} catch (IllegalArgumentException e) {
		    throw new IllegalArgumentException("Failed input line:" + line + "\n" + e.getMessage(), e);
		}
	        //no value on the line
	        if (s == null)
	            continue;
	        sessions.add(s);
	    }
	} catch (FileNotFoundException e) {
	    throw new IllegalArgumentException("File " + file + " not found.", e);
	} finally {
	    // Done with the file
	    if (br != null)
		try {
		    br.close();
		} catch (IOException e) {
		    e.printStackTrace();
		}
	    else
		br = null;
	    fis = null;
	}

    }

    /**
     * Gets all the tracks in the schedule; null until a schedule has been created
     * @return the schedule Tracks 
     */
    public Track[] getTracks() {
	return tracks;
    }

    /**
     * Applies the SchedulingStrategy to schedule all the sessions
     * 
     * @throws IllegalArgumentException if a schedule cannot be created for some reason.
     */
    public void makeSchedule() throws IllegalArgumentException {
    	if (strategy == null)
    	    throw new IllegalArgumentException("No Strategy is specifed, so no schedule can be created");
    	
	try {
	    tracks = strategy.apply(blocks, sessions.toArray(new Session[]{}));
	} catch (IllegalArgumentException e) {
	    throw new IllegalArgumentException("A viable schedule could not be made:" + e.getMessage(), e);
	}
	
	/*
	 * now immortalize result in tracks 
	 * assign times to each session, etc.
	 */
	//first get maxUsedMinutes of each block
	int[] maxBlockUsedMinutes = null;
	for (int i = 0; i < tracks.length; i++) {
	    Track t = tracks[i];
	    int[] trackUsedMinutes = t.getBlockUsedMinutes();
	    if (i == 0) {
		maxBlockUsedMinutes = trackUsedMinutes;
		continue;
	    }
	    for (int j = 0; j < maxBlockUsedMinutes.length; j++) 
		if (trackUsedMinutes[j] > maxBlockUsedMinutes[j])
		    maxBlockUsedMinutes[j] = trackUsedMinutes[j];
	}
	for (int i = 0; i < tracks.length; i++) {
	    Track t = tracks[i];
	    t.setLabel(Config.TRACK_LABEL_PREFIX + (i + 1));
	    t.setSchedule(maxBlockUsedMinutes);
	}

    }

    /**
     * Sets the blocks of time to be used in all Schedule Tracks
     * 
     * @param bks the time Block formats
     * @throws IllegalArgumentException if the list of Blocks is not time sequenced properly
     */
    public void setBlocks(BlockFormat[] bks) 
    		throws IllegalArgumentException {
	
	//./static check
	BlockFormat.checkBlockOrder(bks);
	blocks = bks;
    }
    
    /**
     * @return the strategy used for scheduling
     * @see SchedulingStrategy
     */
    public SchedulingStrategy getStrategy() {
        return strategy;
    }


    /**
     * Sets the SchedulingStrategy to be used
     * @param strategy the strategy to set
     * @see SchedulingStrategy
     */
    public void setStrategy(SchedulingStrategy strategy) {
        this.strategy = strategy;
    }



}
