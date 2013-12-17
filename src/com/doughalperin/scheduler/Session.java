/**
 * @version 1.0
 */
package com.doughalperin.scheduler;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * Class containing all information about Sessions that will be scheduled.
 *
 */
public class Session {
    
    static private final String hourPatternString = "(\\d+)\\s*(hour|hr)s?";
    static private final String minutePatternString = "(\\d+)\\s*(minute|min)s?";
    static private final String lightningPatternString = "lightning";
    static public final String LINE_PATTERN = "([^0-9]+?)\\s+"
	    + "("  + lightningPatternString + "|" + hourPatternString + "|" + minutePatternString + "|"
	    + hourPatternString + "\\s*" + minutePatternString + ")";
    
    private String name;
    private int minutes;
    private Integer startOffset;
    private boolean isBlockLevelSession;
    
    /*
     * Decode a line containing Session name and time length
     * <p>
     * Note that blank lines are ignored; leading and trailing whitespace is trimmed
     * lines beginning with # are taken as comments and ignored.
     * </p>
     * 
     * @params line format is [^#0-9][^0-9]*\s+(<lightning>|\d+<hour>|\d+<minute>|\d+<hour>\d+<minute>)(\[\w+\])?$
     * <blockquote>
     * <lightning> :== lightning
     * <hour> ::= (hr|hour)s?
     * <minute> :== (min|minute)s?
     * </blockquote>
     * Lightning, hour, and minute values are case-insensitive!
     * 
     * Note that blank lines are ignored; leading and trailing whitespace is trimmed
     * lines beginning with # are taken as comments and ignored.
     * 
     */
    public static Session getInstanceFromString(String line) 
    		throws IllegalArgumentException {
	
	final Pattern pattern = Pattern.compile(LINE_PATTERN, Pattern.CASE_INSENSITIVE);
		
	if (line == null)
	    return null;
	line = line.trim();
	if (line.length() == 0 || line.startsWith("#"))
	    return null;
	Matcher matcher = pattern.matcher(line);
	if (!matcher.matches()) 
	    throw new IllegalArgumentException("Invalid Session line [" + line + "]");
	
	String name = matcher.group(1);
	String timeString = matcher.group(2);
	
	//now decode the time
	int mins = 0;
	if (timeString.matches(lightningPatternString)) 
	    mins = Config.LIGHTNING_MINUTES;
	else {
	    matcher = Pattern.compile(hourPatternString, Pattern.CASE_INSENSITIVE).matcher(timeString);
	    if (matcher.find()) {
		assert (matcher.group(1).matches("^\\d+$"));  //we already tested for it being numeric
		mins += Integer.parseInt(matcher.group(1)) * 60;
	    }
	    matcher = Pattern.compile(minutePatternString, Pattern.CASE_INSENSITIVE).matcher(timeString);
	    if (matcher.find()) {
		assert (matcher.group(1).matches("^\\d+$"));  //we already tested for it being numeric
		mins += Integer.parseInt(matcher.group(1));
	    }
	}

	return new Session(name, mins);
    }
    
    /**
     * @param name what the session is called
     * @param minutes how long the session is in minutes
     * @throws IllegalArgumentException if Name is not specified or minutes are les than or equal to zero
     */
    public Session(String name, int minutes) throws IllegalArgumentException {
	if (name == null || name.trim().length() == 0)
	    throw new IllegalArgumentException("Name must be specified and not be whitespace.");
	this.name = name.trim();
	setMinutes(minutes);
	
	startOffset = null;  //not in track yet
	
	isBlockLevelSession = false;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override 
    public String toString() {
	return "Session:" + name + " [" + minutes + " minutes]"; 
    }
    
    /**
     * @return the time
     */
    public int getMinutes() {
        return minutes;
    }

    /**
     * @param m the time to set in minutes
     */
    public void setMinutes(int m) throws IllegalArgumentException {
	if (m <= 0) 
	    throw new IllegalArgumentException("Minutes must be greater than zero.");
        minutes = m;
    }

    /**
     * @return the name of the Session
     */
    public String getName() {
	return name;
    }


    /**
     * @return the offset in minutes from time zero of the beginning of the session
     * @throws IllegalStateException if the Session has not yet been scheduled into a time slot
     */
    public int getStartOffset() throws IllegalStateException {
	if (startOffset == null)
	    throw new IllegalStateException("Start Offset is not yet set.");
	return startOffset;
    }

    /**
     * Puts the Session on a Track schedule
     * @param startOffset the startOffset to set
     */
    public void setStartOffset(Integer startOffset) throws IllegalArgumentException {
	
	if (startOffset == null) {
	    this.startOffset = null;
	    return;
	}
	
	if (startOffset < 0) 
	    throw new IllegalArgumentException("Start Offset must be equal to or greater than zero.");
        this.startOffset = startOffset;
    }

    /**
     * @return whether this Session is simply a placeholder for Blocks like lunch that contain no real sessions.
     */
    public boolean isBlockLevelSession() {
        return isBlockLevelSession;
    }

    /**
     * @param isBlockLevelSession sets whether or not this is a Block Level placeholder Session
     */
    public void setBlockLevelSession(boolean isBlockLevelSession) {
        this.isBlockLevelSession = isBlockLevelSession;
    }
    

}
