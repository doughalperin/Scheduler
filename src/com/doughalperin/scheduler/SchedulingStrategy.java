/**
 * @version 1.0
 */
package com.doughalperin.scheduler;

import java.util.ArrayList;
import java.util.TreeMap;

/**
 * This abstract class defines the basic functionality required for a scheduling strategy 
 * that places a series of Sessions into TrackBlocks
 * 
 *
 *
 */
public abstract class SchedulingStrategy {
    
    /**
     * List of results from attempt to place a Session into a Track
     * <ul>
     * <li>PLACED - if successfully placed in the Track</li>
     * <li>COULD_PLACE_AT_MAX - if a Block of flexible duration was extended to accomodate the Session</li>
     * <li>FAILED - the Session does not fit in the Track</li>
     * </ul>
     *
     */
    enum PlacementResult {PLACED, COULD_PLACE_AT_MAX, FAILED}
    
    /**
     * @param blocks time blocks in which Tracks are divided
     * @param sessions all the Sessions to schedule
     * @return the Tracks with Sessions scheduled
     * @throws IllegalArgumentException if a Session cannot be scheduled for example if the duration of a Session 
     * is longer than the total time in a Track
     */
    public abstract Track[] apply(BlockFormat[] blocks, Session[] sessions) throws IllegalArgumentException;
    
    /**
     * Group sessions into like length groups and counts.  This is an important optimization step available to all SchedulingStrategy
     * sub-classes that treats all equal-length Sessions interchangeably
     * 
     * @param sessions the Sessions to group
     */
    protected  TreeMap<Integer, ArrayList<Session>> groupSessions(Session[] sessions) {
	TreeMap<Integer, ArrayList<Session>> groups = new TreeMap<Integer, ArrayList<Session>>();
	for (Session s : sessions) {
	    int m = s.getMinutes();
	    ArrayList<Session> group = groups.get(m);
	    if (group == null) {
		group = new ArrayList<Session>();
		groups.put(m,  group);
	    }
	    group.add(s);
	}
	return groups;
	
    }

}
