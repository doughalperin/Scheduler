/**
 * @version 1.0
 */
package com.doughalperin.scheduler;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 *Tests Config Class - assuring that it creates no runtime errors on instantiation
 *
 *@see Config
 */
public class ConfigTest {

    /**
     * Just make sure no exceptions on Class instantiation
     */
    @Test
    public void test() {
	BlockFormat[] blocks = Config.BLOCKS;
	assertTrue(blocks.length > 0);
    }

}
