package com.example.cse110project;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
public class US4Tests {

    @Test
    public void testDistance() {
        double distance = Utilities.findDistance(35, 100, 35, 120);
        assertEquals(distance, 1129.03, 5);
    }
}
