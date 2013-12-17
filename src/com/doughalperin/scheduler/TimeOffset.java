/**
 * @version 1.0
 */
package com.doughalperin.scheduler;

/**
 * Small utility class to use relative time of a dayNumber, hour, and minute
 * <p>
 * This class provides for time offset from some arbitrary time time zero which is at dayNumber = 0, hour = 0, minute = 0
 * </p>
 *
 */
public class TimeOffset {
    private int dayNumber;
    private int hour;
    private int minute;
    private boolean includeDay;
    
    /**
     * @param dayNumber time starts at Day 0, this is number of days from then
     * @param hour 0-23
     * @param minute 0-59
     * @throws IllegalArgumentException when values are less than zero or out of range
     */
    public TimeOffset(int dayNumber, int hour, int minute) throws IllegalArgumentException {
	if (dayNumber < 0) 
	    throw new IllegalArgumentException("Day must be zero or greater");
	if (hour < 0 || hour > 23)
	    throw new IllegalArgumentException("Hour must be betwen 0 and 23 inclusively");
	if (minute < 0 || minute > 59)
	    throw new IllegalArgumentException("Minute must be betwen 0 and 59 inclusively");
	this.dayNumber = dayNumber;
	this.hour = hour;
	this.minute = minute;
	this.includeDay = Config.INCLUDE_DAYNUMBER;
    }
    
    /**
     * Starts with an offset and instantiates the time based on that.
     * @param offset minutes from time zero
     * @throws IllegalArgumentException if offset is less than zero
     */
    public TimeOffset(int offset) throws IllegalArgumentException {
	if (offset < 0)
	    throw new IllegalArgumentException("Offset must be zero or greater");
	
	minute = offset % 60;
	offset = (offset - minute)/60;
	hour = offset % 24;
	dayNumber = (offset - hour)/24;
	includeDay = Config.INCLUDE_DAYNUMBER;
    }
    
    /**
     * @return the time represented as minutes since time zero
     */
    public int getOffset() {
	return (dayNumber * 24 + hour) * 60 + minute;
    }
    
    public String toString() {
	String s =  (includeDay ? dayNumber + " " : "")
		+ (hour >= 12
			? String.format("%02d:%02d", hour - (hour == 12 ? 0 : 12), minute) + "PM"
			: String.format("%02d:%02d", hour, minute) + "AM"
			);
	return s;
    }

    /**
     * @param includeDay indicating whether dayNumber should be included in any String representation of the time offset
     */
    public void setIncludeDay(boolean includeDay) {
        this.includeDay = includeDay;
    }
    

}
