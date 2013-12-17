/**
 * @version 1.0
 */
package com.doughalperin.scheduler;

/**
 * Class containing all the defaults and configuration options used by the 
 * Track Management system.
 *
 */
public class Config {

    /**
     * Default file Encoding of the input file
     * 
     * @see Scheduler
     */
    public static Schedule.FileEncoding DEFAULT_FILE_ENCODING = Schedule.FileEncoding.UTF8;
    
    /** 
     * Minutes in a lightning session
     * 
     *  @see Session
     * */
    public static final int LIGHTNING_MINUTES = 5;
    
    /** 
     * Periods of time in each Track available for scheduling 
     * 
     * @see BlockFormat
     **/
    public static final BlockFormat[] BLOCKS = {
	new BlockFormat("Morning Sessions", new TimeOffset(0, 9, 0), true, true, 180, 180),
	new BlockFormat("Lunch", new TimeOffset(0, 12, 0), false, true, 60, 60),
	new BlockFormat("Afternoon Sessions", new TimeOffset(0, 13, 0), true, true, 180, 240),
	new BlockFormat("Networking Event", new TimeOffset(0, 16, 0), false, false, 180, 300)
    };
    
    /**
     *  Default as to whether to include \DayNumber in TimeOffset toString
     *  
     *   @see TimeOffset
     **/
    public static final boolean INCLUDE_DAYNUMBER = false;
    
    /**
     * Default Strategy to use for scheduling
     * 
     *  @see SchedulingStrategy
     *  @see BruteForceStrategy
     **/
    public static final SchedulingStrategy DEFAULT_STRATEGY = new BruteForceStrategy();
    
    /** 
     * Prefix on track labels ... before (1-based) numeric index of track
     * 
     *  @see Track
     **/
    public static final String TRACK_LABEL_PREFIX = "Track ";

}
