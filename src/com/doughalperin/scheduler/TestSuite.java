/**
 * 
 */
package com.doughalperin.scheduler;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({TimeOffsetTest.class, BlockFormatTest.class, ConfigTest.class, 
    SessionTest.class,TrackTest.class
    })

/**
 * Overarching JUnit 4 TestSuite calling all test classes.
 *
 */
public class TestSuite {
    //nothing
}
