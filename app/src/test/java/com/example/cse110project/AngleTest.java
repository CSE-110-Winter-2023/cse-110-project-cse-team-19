package com.example.cse110project;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class AngleTest {
    @Test
    public void testAngleNorth(){
        double lat1 = 32.58;
        double long1 = -118.63;
        double lat2 = 48.34;
        var expected = 0;
        var actual = Utilities.findAngle(lat1,long1,lat2,long1);
        assertEquals(expected, actual, 0);
    }

    @Test
    public void testAngleSouth(){
        double lat1 = 32.58;
        double long1 = -118.63;
        double lat2 = 16.34;
        var expected = 180;
        var actual = Utilities.findAngle(lat1,long1,lat2,long1);
        assertEquals(expected, actual, 0);
    }

    @Test
    public void testAngleEast(){
        double lat1 = 32.58;
        double long1 = -118.63;
        double long2 = -100;
        var expected = 90;
        var actual = Utilities.findAngle(lat1,long1,lat1,long2);
        assertEquals(expected, actual, 0);
    }

    @Test
    public void testAngleWest(){
        double lat1 = 32.58;
        double long1 = -118.63;
        double long2 = -140;
        var expected = 270;
        var actual = Utilities.findAngle(lat1,long1,lat1,long2);
        assertEquals(expected, actual, 0);
    }
}
