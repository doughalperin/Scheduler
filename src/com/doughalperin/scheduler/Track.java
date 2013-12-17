/**
 * @version 1.0
 */
package com.doughalperin.scheduler;

/**
 * 
 * Track instances are created as needed to develop a viable instance.
 * <p>
 * Initially a Track holds the BlockFormat information about time periods during a Track. 
 * After a schedule has been successfully made, the track completed, Sessions are assigned to specific times
 * within ScheduleBlocks in the Track. 
 * </p>
 * <p>
 * Sessions are contained within ScheduleBlocks that are in the Track.
 * </p>
 *
 */
public class Track {

    
    private String label;
    
    //original blocks
    private BlockFormat[] originalBlocks;
    
    //schedule blocks
    private ScheduleBlock[] scheduleBlocks;
    
    //subset of scheduleBlocks that can contain sessions
    private ScheduleBlock[] workingBlocks;
    
    /**
     * Constructs a new Track with specified time blocks
     * 
     * @param blocks the time block formats for the Track; blocks must be in sequential time order
     * @throws IllegalArgumentException if the blocks are invalid, for example if they are not sequential in time.
     */
    public Track(BlockFormat[] blocks) throws IllegalArgumentException {
	
	BlockFormat.checkBlockOrder(blocks);
	originalBlocks = blocks;
	
	scheduleBlocks = new ScheduleBlock[blocks.length];
	int countSchedulable = 0;
	//create working blocks that will be filled as we go along
	for (int i = 0; i < blocks.length; i++) {
	    scheduleBlocks[i] = new ScheduleBlock(blocks[i]);
	    if (blocks[i].isAvailableForScheduling()) 
		countSchedulable++;
	}
	//as a shortcut to those used for placement to avoid continued lookup
	workingBlocks = new ScheduleBlock[countSchedulable];
	int i = 0;
	for (ScheduleBlock sb : scheduleBlocks) {
	    if (!sb.isAvailableForScheduling())
		continue;
	    workingBlocks[i] = sb;
	    i++;
	}	
	
	//as a placeholder
	label = getClass().getSimpleName();
	
    }
	    	
    /**
     * @return Track label
     */
    public String getLabel() {
	return label;
    }

    /**
     * @param label the label to set
     */
    public void setLabel(String label) throws IllegalArgumentException {
	if (label == null || label.trim().length() == 0)
	    throw new IllegalArgumentException("Label cannot be null or just whitespace");
        this.label = label.trim();
    }

    /**
     * @return the originalBlocks that specify the time periods in the Track
     */
    public BlockFormat[] getOriginalBlocks() {
        return originalBlocks;
    }

    /**
     * @return blocks being used during scheduling
     */
    public ScheduleBlock[] getWorkingBlocks() {
	return workingBlocks;
    }
    
    /**
     * @return blocks containing Session and time information
     */
    public ScheduleBlock[] getScheduleBlocks() {
	return scheduleBlocks;
    }

    /**
     * Move workingBlocks into a real schedule of times
     * 
     */
    public void setSchedule(int[] maxBlockUsedMinutes) {
	for (int i = 0; i < scheduleBlocks.length; i++) {
	    ScheduleBlock bs = scheduleBlocks[i];
	    
	    //set startTimeOffset based on start and whether fixedStart
	    if (i == 0 || bs.isFixedStart())
		bs.setStartMinuteOffset(bs.getBlockStartTimeOffset());
	    else { 
		//may move up based on maxBlockUsedMinutes 
		int endTimeOffset = scheduleBlocks[i - 1].getBlockStartTimeOffset() + maxBlockUsedMinutes[i- 1];
		if (endTimeOffset < bs.getBlockStartTimeOffset())
		    bs.setStartMinuteOffset(bs.getBlockStartTimeOffset());
		else
		    bs.setStartMinuteOffset(endTimeOffset);
	    }
	    
	    //set Session schedules
	    if (bs.isAvailableForScheduling()) {
		int offset = bs.getStartMinuteOffset();
		for (Session s : bs.getSessions()) {
		  s.setStartOffset(offset);
		  offset += s.getMinutes();
		}
	    } else {
		//establish a session matching the block
		Session s = new Session(bs.getBlockName(), bs.getMinDuration());
		bs.place(s);
		s.setStartOffset(bs.getStartMinuteOffset());
		s.setBlockLevelSession(true);
	    }
	}
    }

    /**
     * @return minutes used for each ScheduleBlock in the Track
     */
    public int[] getBlockUsedMinutes() {
	int[] blockUsedMinutes = new int[scheduleBlocks.length];
	for (int i = 0; i < scheduleBlocks.length; i++) {
	    ScheduleBlock bs = scheduleBlocks[i];
	    blockUsedMinutes[i] = bs.isAvailableForScheduling() 
		    ? bs.getUsedMinutes()
			    : bs.getMinDuration();
	}
	return blockUsedMinutes; 
    }

}
