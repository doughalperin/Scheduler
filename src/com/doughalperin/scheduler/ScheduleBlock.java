/**
 * @version 1.0
 */
package com.doughalperin.scheduler;

import java.util.ArrayList;

/**
 * This class is an actual time Block within a Track.  Once scheduling is complete it includes the Sessions that have been slotted
 * into the Block.
 *
 */
/**
 * @author Doug Halperin
 *
 */
public class ScheduleBlock {
    //prototype block
    private BlockFormat block;
    
    private int usedMinutes;
    private ArrayList<Session> sessions;

    private int startMinuteOffset;
    
    
    /**
     * Creates a new ScheduleBlock based on the BlockFormat specified
     * @param bks the time block of this ScheduleBlock
     */
    public ScheduleBlock(BlockFormat bks) {
	block = bks;
	//if not going to be scheduled; minimum duration is used.
	usedMinutes = 0;
	
	//starts same as block
	startMinuteOffset = block.getStartMinuteOffset();
	
	sessions = new ArrayList<Session>();
    }

    /**
     * @return the minutes within the Block actually used by Sessions
     */
    public int getUsedMinutes() {
        return usedMinutes;
    }

    /**
     * @param useMax indicates whether the returned value should use the minimum or maximum duration of the block
     * @return the number of minutes remaining in the block not yet allocated to Sessions
     */
    public int getAvailableMinutes(boolean useMax) {
    	if (useMax)
    	    return block.getMaxDuration() - usedMinutes;
    	return block.getMinDuration() - usedMinutes;
    }

    /**
     * @return the sessions in the Block
     */
    public ArrayList<Session> getSessions() {
        return sessions;
    }
    
    /**
     * Resets the schedule of the block, so that it can be re-used for subsequent scheduling attempts
     */
    public void resetSchedule() {
	usedMinutes = 0;
        sessions.clear();
    }

    /**
     * Indicates whether the Block is actually used for Sessions.
     * 
     * @return true if Sessions can be scheduled in the block
     */
    public boolean isAvailableForScheduling() {
	return block.isAvailableForScheduling();
    }
    
    /**
     * Indicates whether the start of the block is flexible
     * 
     * @return true if the start offset of the block is fixed
     */
    public boolean isFixedStart() {
	return block.isFixedStart();
    }
    
    /**
     * Adds a Session to the block
     * 
     * @param session the session to place
     */
    public void place(Session session) {
	sessions.add(session);
	usedMinutes += session.getMinutes();
    }

    /**
     * @param startMinuteOffset the startMinuteOffset to set
     */
    public void setStartMinuteOffset(int startMinuteOffset) {
        this.startMinuteOffset = startMinuteOffset;
    }
    
    
    
    /**
     * @return the startMinuteOffset
     */
    public int getStartMinuteOffset() {
        return startMinuteOffset;
    }

    /**
     * @return the original start time of the block
     */
    public int getBlockStartTimeOffset() {
	return block.getStartMinuteOffset();
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
	return "ScheduleBlock " + block.getName() + " [used=" + usedMinutes 
		+ " of " + block.getMinDuration() + "-" + block.getMaxDuration()
		+ ", sessionsCount=" + sessions.size() + "]";
    }

    /**
     * @return name of the Block
     */
    public String getBlockName() {
	return block.getName();
    }

    /**
     * @return the minimum length in minutes of the block
     */
    public int getMinDuration() {
	return block.getMinDuration();
    }

    /**
     * @return the maximum length in minutes of the block
     */
    public int getMaxDuration() {
	return block.getMaxDuration();
    }


    
}
