/**
 * @version 1.0
 */
package com.doughalperin.scheduler;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

/**
 * 
 * BruteForce Strategy iterates through sessions from the longest duration sessions to the shortest ones.  The strategy is 
 * to place in first available Track Block.  If no track block fits the session it creates a new Track.
 * <p>
 * This strategy will be within 2x of the optimal strategy since every Block will be at least half-full.
 * </p>
 * 
 *
 */
public class BruteForceStrategy extends SchedulingStrategy {
    /* (non-Javadoc)
     * @see com.doughalperin.scheduler.SchedulingStrategy#schedule(com.doughalperin.scheduler.Block[], com.doughalperin.scheduler.Session[])
     */
    @Override
    public Track[] apply(BlockFormat[] blocks, Session[] sessions) throws IllegalArgumentException {
	ArrayList<Track> tracks = new ArrayList<Track>();
	
	//start with 1 track 
	tracks.add(new Track(blocks));
	
	if (sessions.length == 0)
	    return tracks.toArray(new Track[]{});
	
	TreeMap<Integer, ArrayList<Session>> groups = groupSessions(sessions);
	
	//place groups into tracks
	execute(groups, tracks);

	//return working tracks that were created 
	return tracks.toArray(new Track[]{});
	
    }
    
    /**
     * Recursively complete the steps to place Sessions into Tracks. After a Session is placed, this method is called again to
     * place the remaining Sessions in Tracks.
     * 
     * @param groups the remaining groups of Sessions (grouped by duration) to Schedule
     * @param tracks the current set of Tracks that have been created
     * @throws IllegalArgumentException when a schedule cannot be created because a Session is too long to fit in a Track
     */
    public void execute(TreeMap<Integer, ArrayList<Session>> groups, ArrayList<Track> tracks) 
    		throws IllegalArgumentException {

	if (groups.isEmpty())
	    //we did it!
	    return;
	
	//work on biggest item in list
	Map.Entry<Integer, ArrayList<Session>> largest = (Map.Entry<Integer, ArrayList<Session>>)groups.lastEntry();
	int minutes = largest.getKey();
	ArrayList<Session> sessions = largest.getValue();
	Session session = sessions.remove(0);
	if (sessions.isEmpty())
	    groups.remove(minutes);
	
	PlacementResult placed = PlacementResult.FAILED;
	//first try against minDuration
	Track maxAltTrack = null;
	for (Track t : tracks) {
	    placed = place(t, session, false);
	    if (placed == PlacementResult.PLACED)
		break;
	    if (placed == PlacementResult.COULD_PLACE_AT_MAX && maxAltTrack == null)
		maxAltTrack = t;
	}
	if (placed != PlacementResult.PLACED && maxAltTrack != null) {
	    placed = place(maxAltTrack, session, true);
	    assert(placed == PlacementResult.PLACED);
	}
	
	if (placed != PlacementResult.PLACED) {
	    //add a new track and see if it will fit...otherwise we fail
	    Track t = new Track(tracks.get(0).getOriginalBlocks());
	
	    //at this point assume max space available and it will be used if needed
	    placed = place(t, session, true);
	    if (placed == PlacementResult.FAILED)
		throw new IllegalArgumentException("Session [" + session.getName() + "] of " + minutes 
			+ " minutes does not fit in any track.");
	    tracks.add(t);
	}

	//place remaining groups into tracks
	execute(groups, tracks);
	
    }

    /**
     * Attempts to place Session in the specified Track
     * 
     * @param t the Track in which to place the Session
     * @param session to place
     * @param placeInMax whether or not the operation will extend a Block (if it can be) in order to accommodate the Session
     * @return PlacementResult indicating the success of the placement attempt
     */
    private PlacementResult place(Track t, Session session, boolean placeInMax) {
	//finds first working block that will fit the session
	int minutes = session.getMinutes();
	boolean canPlaceInMax = false;
	for (ScheduleBlock wb : t.getWorkingBlocks()) {
	    if (wb.getAvailableMinutes(placeInMax) >= minutes) {
		wb.place(session);
		return PlacementResult.PLACED;
	    }
	    if (!placeInMax && wb.getAvailableMinutes(true) >= minutes)
		canPlaceInMax = true;
	}
	if (canPlaceInMax)
	    return PlacementResult.COULD_PLACE_AT_MAX;
	return PlacementResult.FAILED;
    }

}
