/**
 * @version 1.0
 */
package com.doughalperin.scheduler;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Tests Session Class
 *
 * @see Session
 */
public class SessionTest {
    final Object[][] passing = {
	    {"Session1", 1},
	    {"a####saa  as ads", 15},
	    {"Session1", 222}
    };
    
    final Object[][] failing = {
	    {null, 1},
	    {"Session2", -15},
	    {"", 222},
	    {"       ",14}
    };
    
    //line, totalMinutes, minutes to set, name
    final Object[][] passingLines = {
		{"Common Ruby Errors 2hrs45min", 165, 10, "Common Ruby Errors"},
		{"Rails for Python Developers lightning", Config.LIGHTNING_MINUTES, 42, "Rails for Python Developers"},
		{"Communicating Over Distance 60 min", 60, 88, "Communicating Over Distance"},
		{"Accounting-Driven Development 4  HOURS  5 minS", 245, 911, "Accounting-Driven Development"},
		{"    Clojure Ate Scala (on my project) 45min", 45, 888, "Clojure Ate Scala (on my project)"},
		{"Testy 3HRS",180, 2, "Testy"},
		{"a#### goo 002HRS",120, 4, "a#### goo"},
		{"beeee 1  mIN", 1, 5, "beeee"}
    };

    final String[] failingLines = {
	    "Overdoing it in P2ython 45min",
	    "Lua for the M4asses 30min",
	    "Ruby Errors from Mismatched Gem Versions 45minh",
	    "Ruby on Rails: Why We Should Move On 60miin",
	    "a#### goo 002HRS-6MINS",
	    "    30MIN"
    };
    
    final int[] failMinutes = {-12, 0, -999};
    
    final Integer[] passingStartOffsets = {null, 0, 12};
    
    final Integer[] failingStartOffsets = {-12, -999};
    
    /**
     * Test method for {@link com.doughalperin.scheduler.Session#getInstanceFromString(java.lang.String)}.
     */
    @Test
    public void testGetInstanceFromString() {
	for (int i = 0; i < passingLines.length; i++) {
	    Object[] c = passingLines[i];
	    Session ret = Session.getInstanceFromString((String)c[0]);
	    assertNotNull("passing case#" + i + " returns null", ret);
	}

	for (int i = 0; i < failingLines.length; i++) {
	    String c = failingLines[i];
	    boolean exceptionOccurred = false;
	    try {
		Session.getInstanceFromString(c);
	    } catch (IllegalArgumentException e) {
		// what we expect
		exceptionOccurred = true;
	    }
	    assertTrue("Unexpectedly succeeded for case#" + i, exceptionOccurred);
	}
    }

    /**
     * Test method for {@link com.doughalperin.scheduler.Session#Session(java.lang.String, int)}.
     */
    @Test
    public void testSessionStringInt() {
	for (int i = 0; i < passing.length; i++) {
	    Object[] c = passing[i];
	    Session ret = new Session((String)c[0], (Integer)c[1]);
	    assertNotNull("passing case#" + i + " returns null", ret);
	}

	for (int i = 0; i < failing.length; i++) {
	    Object[] c = failing[i];
	    boolean exceptionOccurred = false;
	    try {
		new Session((String)c[0], (Integer)c[1]);
	    } catch (IllegalArgumentException e) {
		// what we expect
		exceptionOccurred = true;
	    }
	    assertTrue("Unexpectedly succeeded for case#" + i, exceptionOccurred);
	}
    }

    /**
     * Test method for {@link com.doughalperin.scheduler.Session#getMinutes()}.
     */
    @Test
    public void testGetMinutes() {
	for (int i = 0; i < passingLines.length; i++) {
	    Object[] c = passingLines[i];
	    Session s = Session.getInstanceFromString((String)c[0]);
	    int ret = s.getMinutes();
	    int expect = (Integer)c[1];
	    assertTrue("case#" + i + " not equal", ret == expect);
	}
    }

    /**
     * Test method for {@link com.doughalperin.scheduler.Session#setMinutes(int)}.
     */
    @Test
    public void testSetMinutes() {
	for (int i = 0; i < passingLines.length; i++) {
	    Object[] c = passingLines[i];
	    Session s = Session.getInstanceFromString((String)c[0]);
	    int expect = (Integer)c[2];
	    s.setMinutes(expect);
	    int ret = s.getMinutes();
	    assertTrue("case#" + i + " not equal", ret == expect);
	}
	//now test failed values
	Object[] c = passingLines[0];
	Session s = Session.getInstanceFromString((String)c[0]);
	for (int i = 0; i < failMinutes.length; i++) {
	    int value = failMinutes[i];
	    boolean exceptionOccurred = false;
	    try {
		s.setMinutes(value);
	    } catch (IllegalArgumentException e) {
		// what we expect
		exceptionOccurred = true;
	    }
	    assertTrue("Unexpectedly succeeded for case#" + i, exceptionOccurred);
	}
    }

    /**
     * Test method for {@link com.doughalperin.scheduler.Session#getName()}.
     */
    @Test
    public void testGetName() {
	for (int i = 0; i < passingLines.length; i++) {
	    Object[] c = passingLines[i];
	    Session s = Session.getInstanceFromString((String)c[0]);
	    String ret = s.getName();
	    String expect = (String)c[3];
	    assertTrue("passingLines case#" + i + " not equal", expect.equals(ret));
	}
	for (int i = 0; i < passing.length; i++) {
	    Object[] c = passing[i];
	    Session s = new Session((String)c[0], (Integer)c[1]);
	    String ret = s.getName();
	    String expect = (String)c[0];
	    assertTrue("passing case#" + i + " not equal", expect.equals(ret));
	}
    }

    /**
     * Test method for {@link com.doughalperin.scheduler.Session#getStartOffset()}.
     * Test method for {@link com.doughalperin.scheduler.Session#setStartOffset(java.lang.Integer)}.
     */
    @Test
    public void testGetStartOffsetAndSetStartOffset() {
	Object[] c = passingLines[0];
	Session s = Session.getInstanceFromString((String)c[0]);
	//initially expect null causes exception
	boolean exceptionOccurred = false;
	try {
	    s.getStartOffset();
	} catch (IllegalStateException e) {
	    // what we expect
	    exceptionOccurred = true;
	}
	assertTrue("Unexpectedly succeeded for null case", exceptionOccurred);

	for (int i = 0; i < passingStartOffsets.length; i++) {
	    Integer expect = passingStartOffsets[i];
	    // reset each time
	    s = Session.getInstanceFromString((String)c[0]);
	    s.setStartOffset(expect);
	    if (expect == null) {
		exceptionOccurred = false;
		try {
		    s.getStartOffset();
		} catch (IllegalStateException e) {
		    // what we expect
		    exceptionOccurred = true;
		}
		assertTrue("Unexpectedly succeeded for null case", exceptionOccurred);
		continue;
	    }
		
	    Integer ret = s.getStartOffset();
	    assertTrue("case#" + i + " not equal", expect.equals(ret));
	}
	//now test failed values
	for (int i = 0; i < failingStartOffsets.length; i++) {
	    Integer value = failingStartOffsets[i];
	    // reset each time
	    s = Session.getInstanceFromString((String)c[0]);
	    try {
		s.setStartOffset(value);
	    } catch (IllegalArgumentException e) {
		// what we expect
		exceptionOccurred = true;
	    }
	    assertTrue("Creation unexpectedly succeeded for case#" + i, exceptionOccurred);
	}
    }
    
    /**
     * Test method for {@link com.doughalperin.scheduler.Session#isBlockLevelSession()}
     * and {@link com.doughalperin.scheduler.Session#setBlockLevelSession(boolean)}.
     */
    @Test
    public void testSetBlockLevelSessionAndIsBlockLevelSession() {
	boolean[] boolTests = {true, false};
	Object[] c = passingLines[0];

	for (int i = 0; i < boolTests.length; i++) {
	    boolean b = boolTests[i];
	    Session s = Session.getInstanceFromString((String)c[0]);
	    s.setBlockLevelSession(b);
	    boolean ret = s.isBlockLevelSession();
	    assertTrue("case#" + i + " not equal", ret == b);
	}
    }

    
}