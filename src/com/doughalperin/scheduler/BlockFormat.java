/**
 * @version 1.0
 */
package com.doughalperin.scheduler;


/**
 * Class defines format of Track Blocks
 * 
 * All members of the class are immutable
 * 
 *
 */
public class BlockFormat {
    
    private final String name;
    private final int startMinuteOffset;  //minutes from start of day 0
    private final int earliestMinuteOffset;
    private final int minDuration;
    private final int maxDuration;
    private final boolean availableForScheduling;
    private final boolean fixedStart;
    

    /**
     * Instantiates a BlockFormat based on parameters
     * 
     * @param n name of the block, for example, "Morning Sessions" or "Lunch"
     * @param timeOffset the time from time zero at which the Block begins
     * @param afs indicates whether this Block is available for scheduling Sessions
     * @param fs indicates whether the block has a fixed start time or it is dependent on completion time of prior block
     * @param min minimum minute length of block
     * @param max maximum minute length of block
     * @throws IllegalArgumentException if input is invalid, for example timeOffset or name is null 
     * or durations are less than or equal to zero
     * @see TimeOffset
     */
    public BlockFormat(String n, TimeOffset timeOffset, boolean afs, boolean fs, int min, int max)
    		throws IllegalArgumentException {

	if (n == null || n.length() == 0)
	    throw new IllegalArgumentException("Name cannot be null or blank.");
	name = n;
	
	if (timeOffset == null)
	    throw new IllegalArgumentException("TimeOffset cannot be null.");
	startMinuteOffset = timeOffset.getOffset();
	
	fixedStart = fs;
	//set value for case when fixedStart is false
	earliestMinuteOffset = startMinuteOffset;
	
	if (min <= 0 || max <= 0)
	    throw new IllegalArgumentException("Durations must be greater than 0.");
	if (min > max)
	    throw new IllegalArgumentException("Minimum Duration must be less than or equal to Maximum Duration.");
	minDuration = min;
	maxDuration = max;
	availableForScheduling = afs;
    }
    
    /**
     * Confirms that a sequence of blocks are in time order
     * @param bks the array of blocks to check
     * @throws IllegalArgumentException if the order is not valid
     */
    static void checkBlockOrder(BlockFormat[] bks) 
	    throws IllegalArgumentException {
	
	if (bks == null)
	    throw new IllegalArgumentException("Blocks must be specified");
	
	//insure no overlap with each other; must be time-ordered
	int maxStartOffset = 0;
	for (int i = 0; i < bks.length; i++) {
	    BlockFormat b = bks[i];
	    if (b == null)
		throw new IllegalArgumentException("A block cannot be null.");
	    if (i == 0) {
		//if first !isFixedStart, effectively ends up with fixedStart
		maxStartOffset = b.getStartMinuteOffset() + b.getMaxDuration();
		continue;
	    }
	    if (!b.isFixedStart()) {
		maxStartOffset += b.getMaxDuration();
		continue;
	    }

	    //start must be after prior block
	    if (b.getStartMinuteOffset() < maxStartOffset)
		throw new IllegalArgumentException("Block #" + i+ " overlaps with prior Block");

	    //move past current block
	    maxStartOffset = b.getStartMinuteOffset() + b.getMaxDuration();
	}
    }

    /**
     * 
     * @return the time in minutes after time zero that the block begins
     */
    public int getStartMinuteOffset() {
        return startMinuteOffset;
    }

    /**
     * @return the minimum duration in minutes of the Block
     */
    public int getMinDuration() {
        return minDuration;
    }

    /**
     * @return the maximum duration in minutes of the Block
     */
    public int getMaxDuration() {
        return maxDuration;
    }


    /**
     * @return whether this Block can be used for scheduling
     */
    public boolean isAvailableForScheduling() {
	return availableForScheduling;
    }

    /**
     * @return the name of the block
     */
    public String getName() {
	return name;
    }
    
    /**
     * @return whether the block has a fixed start time or one dependent on the prior block's completion time
     */
    public boolean isFixedStart() {
        return fixedStart;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
	return "Block [name=" + name + ", startMinuteOffset="
		+ startMinuteOffset + ", earliestMinuteOffset="
		+ earliestMinuteOffset + ", minDuration=" + minDuration
		+ ", maxDuration=" + maxDuration + ", availableForScheduling="
		+ availableForScheduling + ", fixedStart=" + fixedStart + "]";
    }

 
}
