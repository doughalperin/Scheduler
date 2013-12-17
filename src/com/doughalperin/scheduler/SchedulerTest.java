/**
 * @version 1.0
 */
package com.doughalperin.scheduler;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Tests Scheduler main, allowing runs from a sample file
 * 
 * @see Scheduler
 */
public class SchedulerTest {

    /**
     * Test method for Bad input file for {@link com.doughalperin.scheduler.Scheduler#main(java.lang.String[])}.
     */
    @Test
    public void testMainFailedInput() {
	System.setProperty("file", "sample/FailedInput.txt");
	Scheduler.main(null);
	//successfully ran
	assertTrue(true);
    }
    
    /**
     * Test method for {@link com.doughalperin.scheduler.Scheduler#main(java.lang.String[])}.
     */
    @Test
    public void testMain() {
	System.setProperty("file", "sample/SampleInput.txt");
	Scheduler.main(null);
	//successfully ran
	assertTrue(true);
    }

}
