package com.edwin.loopviewpager;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void Index() throws Exception {
        int getCurrentItem = 0;
        for (getCurrentItem = 0; getCurrentItem < 100; getCurrentItem++) {
            int i = (getCurrentItem + 1) % 4;
            System.out.println("getCurrentItem = " + getCurrentItem + " i = " + i + "  50%4 =" + 50%4);
        }

    }
}