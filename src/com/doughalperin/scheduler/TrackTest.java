/**
 * 
 */
package com.doughalperin.scheduler;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Tests Track class
 *
 *@see Track
 */
public class TrackTest {
    
    final BlockFormat[][] passing = {
	    {
		new BlockFormat("Morning Sessions", new TimeOffset(2, 9, 0), true, true, 180, 180),
		new BlockFormat("Lunasd asch", new TimeOffset(3, 12, 0), false, true, 60, 60),
		new BlockFormat("Afternoon Sessions", new TimeOffset(4, 13, 0), true, true, 180, 240)
	    },
	    {
		new BlockFormat("Morning Sessions", new TimeOffset(0, 9, 0), true, true,180,180),
		new BlockFormat("Lunch", new TimeOffset(0, 12, 0), false, true, 60, 60),
		new BlockFormat("Afternoon Sessions", new TimeOffset(0, 13, 0), true, true,180, 240),
		new BlockFormat("Networking Event", new TimeOffset(0, 16, 0), false, false, 180, 300)
	    }
    };
    final BlockFormat[][] failing = {
	    {
		new BlockFormat("Morning Sessions", new TimeOffset(0, 9, 0), true, true, 180, 180),
		null
	    },
	    {
		new BlockFormat("Morning Sessions", new TimeOffset(0, 9, 0), true, true,180,180),
		new BlockFormat("Lunch", new TimeOffset(0, 4, 0), false, true, 60, 60)
	    },
	    null
	    
    };
    
    final String[] passingLabels = {"Track", "  ok"};
    final String[] failingLabels = {null, "  "};

    /**
     * Test method for {@link com.doughalperin.scheduler.Track#Track(com.doughalperin.scheduler.BlockFormat[])}.
     */
    @Test
    public void testTrack() {
	for (int i = 0; i < passing.length; i++) {
	    BlockFormat[] c = passing[i];
	    Track ret = new Track(c);
	    assertNotNull("passing case#" + i + " returns null", ret);
	}

	for (int i = 0; i < failing.length; i++) {
	    BlockFormat[] c = failing[i];
	    boolean exceptionOccurred = false;
	    try {
		new Track(c);
	    } catch (IllegalArgumentException e) {
		// what we expect
		exceptionOccurred = true;
	    }
	    assertTrue("Unexpectedly succeeded for case#" + i, exceptionOccurred);
	}
    }

    /**
     * Test method for {@link com.doughalperin.scheduler.Track#getLabel()}.
     */
    @Test
    public void testGetLabel() {
	for (int i = 0; i < passing.length; i++) {
	    BlockFormat[] c = passing[i];
	    Track t = new Track(c);
	    String ret = t.getLabel();
	    assertTrue("passing case#" + i + " not set", ret != null && ret.length() > 0);
	}
    }

    /**
     * Test method for {@link com.doughalperin.scheduler.Track#setLabel(java.lang.String)}.
     */
    @Test
    public void testSetLabel() {
	BlockFormat[] c = passing[0];
	for (int i = 0; i < passingLabels.length; i++) {
	    String value = passingLabels[i];
	    // reset each time
	    Track t = new Track(c);
	    t.setLabel(value);
	    String ret = t.getLabel();
	    assertTrue("case#" + i + " not equal", value.trim().equals(ret));
	}
	//now test failed values
	for (int i = 0; i < failingLabels.length; i++) {
	    String value = failingLabels[i];
	    // reset each time
	    Track t = new Track(c);
	    boolean exceptionOccurred = false;
	    try {
		t.setLabel(value);
	    } catch (IllegalArgumentException e) {
		// what we expect
		exceptionOccurred = true;
	    }
	    assertTrue("Unexpectedly succeeded for case#" + i, exceptionOccurred);
	}
    }

    /**
     * Test method for {@link com.doughalperin.scheduler.Track#getOriginalBlocks()}.
     */
    @Test
    public void testGetOriginalBlocks() {
	BlockFormat[] c = passing[0];
	Track t = new Track(c);
	BlockFormat[] ret = t.getOriginalBlocks();
	assertTrue(c == ret);
    }

    /**
     * Test method for {@link com.doughalperin.scheduler.Track#getWorkingBlocks()}.
     */
    @Test
    public void testGetWorkingBlocks() {
	BlockFormat[] c = passing[0];
	Track t = new Track(c);
	ScheduleBlock[] ret = t.getWorkingBlocks();
	assertNotNull(ret);
    }

    /**
     * Test method for {@link com.doughalperin.scheduler.Track#getScheduleBlocks()}.
     */
    @Test
    public void testGetScheduleBlocks() {
	BlockFormat[] c = passing[0];
	Track t = new Track(c);
	ScheduleBlock[] ret = t.getScheduleBlocks();
	assertNotNull(ret);
    }

    /**
     * Test method for {@link com.doughalperin.scheduler.Track#getBlockUsedMinutes()}.
     */
    @Test
    public void testGetBlockUsedMinutes() {
	BlockFormat[] c = passing[0];
	Track t = new Track(c);
	int[] ret = t.getBlockUsedMinutes();
	assertNotNull(ret);
    }

}
