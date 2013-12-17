/**
 * 
 */
package com.doughalperin.scheduler;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Tests TimeOffset Class
 *
 * @see TimeOffset
 */
public class TimeOffsetTest {
    final int[][] passing3Ints = {
	    {0, 23, 44},
	    {11, 5, 2},
	    {4, 1, 0}
    };

    final int[] passingOffsets = {201, 999, 47, 1456, 0};

    final int[][] failing = {
	    {-1, 23, 44},
	    {0, 24, 2},
	    {1, 77, 11},
	    {0, -11, 2},
	    {1, 3, -9},
	    {4, 15, 62}
    };

    final int[] failingTimeOffsets = {-17, -156};

    /**
     * Test method for {@link com.doughalperin.scheduler.TimeOffset#TimeOffset(int, int, int)}.
     */
    @Test
    public void testTimeOffsetIntIntInt() {

	for (int[] c : passing3Ints) {
	    TimeOffset to = new TimeOffset(c[0], c[1], c[2]);
	    assertNotNull("passing case " + c + " returns null", to);
	}

	for (int[] c : failing) {
	    boolean exceptionOccurred = false;
	    try {
		new TimeOffset(c[0], c[1], c[2]);
	    } catch (IllegalArgumentException e) {
		// what we expect
		exceptionOccurred = true;
	    }
	    assertTrue("Creation unexpectedly succeded for " + c, exceptionOccurred);
	}
    }

    /**
     * Test method for {@link com.doughalperin.scheduler.TimeOffset#TimeOffset(int)}.
     */
    @Test
    public void testTimeOffsetInt() {
	
	for (int c : passingOffsets) {
	    TimeOffset to = new TimeOffset(c);
	    assertNotNull("passing Case " + c + " returns null", to);
	}
	for (int c : failingTimeOffsets) {
	    boolean exceptionOccurred = false;
	    try {
		new TimeOffset(c);
	    } catch (IllegalArgumentException e) {
		// what we expect
		exceptionOccurred = true;
	    }
	    assertTrue("Creation unexpectedly succeded for " + c, exceptionOccurred);
	}
    }

    /**
     * Test method for {@link com.doughalperin.scheduler.TimeOffset#getOffset()}.
     */
    @Test
    public void testGetOffset() {
	for (int[] c : passing3Ints) {
	    TimeOffset to = new TimeOffset(c[0], c[1], c[2]);
	    assertEquals("Offset wrong for " + c, (c[0] * 24 + c[1]) * 60 + c[2], to.getOffset());
	}
	for (int c : passingOffsets) {
	    TimeOffset to = new TimeOffset(c);
	    assertEquals("Offset wrong for " + c, c, to.getOffset());
	}
    }

    /**
     * Test method for {@link com.doughalperin.scheduler.TimeOffset#toString()}.
     */
    @Test
    public void testToStringAndSetIncludeDay() {
	for (boolean b : new boolean[]{true, false}) {
	    for (int[] c : passing3Ints) {
		TimeOffset to = new TimeOffset(c[0], c[1], c[2]);
		to.setIncludeDay(b);
		assertNotNull("toString failed for " + c, to.toString());
	    }
	    for (int c : passingOffsets) {
		TimeOffset to = new TimeOffset(c);
		to.setIncludeDay(b);
		assertNotNull("toString failed for " + c, to.toString());
	    }
	}
    }
}
