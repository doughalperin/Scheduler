/**
 * @version 1.0
 */
package com.doughalperin.scheduler;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Tests BlockFormat Class
 *
 * @see BlockFormat
 *
 */
public class BlockFormatTest {
    Object[][] passing = {
	    {"Morning Sessions", new TimeOffset(0, 9, 0), true, true, 180, 180},
	    {"Lunch", new TimeOffset(0, 12, 0), false, true, 60, 60},
	    {"Afternoon Sessions", new TimeOffset(0, 13, 0), true, true, 180, 240},
	    {"Networking Event", new TimeOffset(0, 16, 0), false, false, 180, 300}
    };
    Object[][] failing = {
	    {null, new TimeOffset(0, 9, 0), true, true, 180, 180},
	    {"Lunch", null, false, true, -60, 60},
	    {"Afternoon Sessions", new TimeOffset(0, 13, 0), true, true, 300, 240},
	    {"Networking Event", new TimeOffset(0, 16, 0), false, false, 0, 0},
	    {"", new TimeOffset(0, 16, 0), false, false, 0, 0}
    };
    
    /**
     * Test method for {@link com.doughalperin.scheduler.BlockFormat#BlockFormat(java.lang.String, com.doughalperin.scheduler.TimeOffset, boolean, boolean, int, int)}.
     */
    @Test
    public void testBlockFormat() {
	for (int i = 0; i < passing.length; i++) {
	    Object[] c = passing[i];
	    BlockFormat bf = new BlockFormat((String)c[0], (TimeOffset)c[1], 
			(Boolean)c[2], (Boolean)c[3], (Integer)c[4], (Integer)c[5]);
	    assertNotNull("passing case#" + i + " returns null", bf);
	}

	for (int i = 0; i < failing.length; i++) {
	    Object[] c = failing[i];
	    boolean exceptionOccurred = false;
	    try {
		new BlockFormat((String)c[0], (TimeOffset)c[1], 
			(Boolean)c[2], (Boolean)c[3], (Integer)c[4], (Integer)c[5]);
	    } catch (IllegalArgumentException e) {
		// what we expect
		exceptionOccurred = true;
	    }
	    assertTrue("Unexpectedly succeeded for case#" + i, exceptionOccurred);
	}
    }

    /**
     * Test method for {@link com.doughalperin.scheduler.BlockFormat#getStartMinuteOffset()}.
     */
    @Test
    public void testGetStartMinuteOffset() {
	for (int i = 0; i < passing.length; i++) {
	    Object[] c = passing[i];
	    BlockFormat bf = new BlockFormat((String)c[0], (TimeOffset)c[1], 
			(Boolean)c[2], (Boolean)c[3], (Integer)c[4], (Integer)c[5]);
	    int ret = bf.getStartMinuteOffset();
	    int expect = ((TimeOffset)c[1]).getOffset();
	    assertTrue("case#" + i + " not equal", ret == expect);
	}
    }

    /**
     * Test method for {@link com.doughalperin.scheduler.BlockFormat#getMinDuration()}.
     */
    @Test
    public void testGetMinDuration() {
	for (int i = 0; i < passing.length; i++) {
	    Object[] c = passing[i];
	    BlockFormat bf = new BlockFormat((String)c[0], (TimeOffset)c[1], 
			(Boolean)c[2], (Boolean)c[3], (Integer)c[4], (Integer)c[5]);
	    int ret = bf.getMinDuration();
	    int expect = (Integer)c[4];
	    assertTrue("case#" + i + " not equal", ret == expect);
	}
    }

    /**
     * Test method for {@link com.doughalperin.scheduler.BlockFormat#getMaxDuration()}.
     */
    @Test
    public void testGetMaxDuration() {
	for (int i = 0; i < passing.length; i++) {
	    Object[] c = passing[i];
	    BlockFormat bf = new BlockFormat((String)c[0], (TimeOffset)c[1], 
			(Boolean)c[2], (Boolean)c[3], (Integer)c[4], (Integer)c[5]);
	    int ret = bf.getMaxDuration();
	    int expect = (Integer)c[5];
	    assertTrue("case#" + i + " not equal", ret == expect);
	}
    }

    /**
     * Test method for {@link com.doughalperin.scheduler.BlockFormat#isAvailableForScheduling()}.
     */
    @Test
    public void testIsAvailableForScheduling() {
	for (int i = 0; i < passing.length; i++) {
	    Object[] c = passing[i];
	    BlockFormat bf = new BlockFormat((String)c[0], (TimeOffset)c[1], 
			(Boolean)c[2], (Boolean)c[3], (Integer)c[4], (Integer)c[5]);
	    boolean ret = bf.isAvailableForScheduling();
	    boolean expect = (Boolean)c[2];
	    assertTrue("case#" + i + " not equal", ret == expect);
	}
    }

    /**
     * Test method for {@link com.doughalperin.scheduler.BlockFormat#getName()}.
     */
    @Test
    public void testGetName() {
	for (int i = 0; i < passing.length; i++) {
	    Object[] c = passing[i];
	    BlockFormat bf = new BlockFormat((String)c[0], (TimeOffset)c[1], 
			(Boolean)c[2], (Boolean)c[3], (Integer)c[4], (Integer)c[5]);
	    String ret = bf.getName();
	    String expect = (String)c[0];
	    assertTrue("case#" + i + " not equal", expect.equals(ret));
	}
    }

    /**
     * Test method for {@link com.doughalperin.scheduler.BlockFormat#isFixedStart()}.
     */
    @Test
    public void testIsFixedStart() {
	for (int i = 0; i < passing.length; i++) {
	    Object[] c = passing[i];
	    BlockFormat bf = new BlockFormat((String)c[0], (TimeOffset)c[1], 
			(Boolean)c[2], (Boolean)c[3], (Integer)c[4], (Integer)c[5]);
	    boolean ret = bf.isFixedStart();
	    boolean expect = (Boolean)c[3];
	    assertTrue("case#" + i + " not equal", ret == expect);
	}
    }

}
