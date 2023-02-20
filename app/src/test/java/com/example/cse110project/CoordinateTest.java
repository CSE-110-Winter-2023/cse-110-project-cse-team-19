package com.example.cse110project;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

import android.content.SharedPreferences;

import android.content.SharedPreferences;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import org.junit.Test;
import org.junit.runner.RunWith;
import static android.content.Context.MODE_PRIVATE;
import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

@RunWith(AndroidJUnit4.class)
public class CoordinateTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void parseStringTest() {
        String s1 = "Not, a, coordinate";
        String s2 = "12.82, -05.12";

        String[] s1List = Utilities.parseCoords(s1);
        String[] s2List = Utilities.parseCoords(s2);

        assertEquals("Not", s1List[0]);
        assertEquals("a", s1List[1]);
        assertEquals("coordinate", s1List[2]);

        assertEquals("12.82", s2List[0]);
        assertEquals("-05.12", s2List[1]);
    }

    @Test
    public void checkLatitudeTest() {
        String invalidStrValue = "words";
        String validNegativeValue = "-15.99";
        String validPositiveValue = "89.99";
        String zeroValue = "0.00";
        String invalidUpperLimit = "208.9498";
        String invalidLowerLimit = "-100.13";
        String validLowerLimit = "-90";
        String validUpperLimit = "90";
        String invalidMarginalLowerLimit = "-91";
        String invalidMarginalUpperLimit = "91";

        assertEquals(false, Utilities.isValidLatitude(invalidStrValue));
        assertEquals(true, Utilities.isValidLatitude(validNegativeValue));
        assertEquals(true, Utilities.isValidLatitude(validPositiveValue));
        assertEquals(true, Utilities.isValidLatitude(zeroValue));
        assertEquals(false, Utilities.isValidLatitude(invalidUpperLimit));
        assertEquals(false, Utilities.isValidLatitude(invalidLowerLimit));
        assertEquals(true, Utilities.isValidLatitude(validLowerLimit));
        assertEquals(true, Utilities.isValidLatitude(validUpperLimit));
        assertEquals(false, Utilities.isValidLatitude(invalidMarginalLowerLimit));
        assertEquals(false, Utilities.isValidLatitude(invalidMarginalUpperLimit));
    }

    @Test
    public void checkLongitudeTest() {
        String invalidStrValue = "words";
        String validNegativeValue = "-15.99";
        String validNegativeValue2 = "-121.99";
        String validPositiveValue = "89.99";
        String validPositiveValue2 = "160.67";
        String zeroValue = "0.00";
        String invalidUpperLimit = "208.9498";
        String invalidLowerLimit = "-200.13";
        String validLowerLimit = "-180";
        String validUpperLimit = "180";
        String invalidMarginalLowerLimit = "-181";
        String invalidMarginalUpperLimit = "181";

        assertEquals(false, Utilities.isValidLongitude(invalidStrValue));
        assertEquals(true, Utilities.isValidLongitude(validNegativeValue));
        assertEquals(true, Utilities.isValidLongitude(validNegativeValue2));
        assertEquals(true, Utilities.isValidLongitude(validPositiveValue));
        assertEquals(true, Utilities.isValidLongitude(validPositiveValue2));
        assertEquals(true, Utilities.isValidLongitude(zeroValue));
        assertEquals(false, Utilities.isValidLongitude(invalidUpperLimit));
        assertEquals(false, Utilities.isValidLongitude(invalidLowerLimit));
        assertEquals(true, Utilities.isValidLongitude(validLowerLimit));
        assertEquals(true, Utilities.isValidLongitude(validUpperLimit));
        assertEquals(false, Utilities.isValidLongitude(invalidMarginalLowerLimit));
        assertEquals(false, Utilities.isValidLongitude(invalidMarginalUpperLimit));
    }

    @Test
    public void savePreferenceTest(){
        SharedPreferences preferences = InstrumentationRegistry.getInstrumentation().getTargetContext().getSharedPreferences("my_preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("mine", "12.82 -05.12");
        editor.apply();

        String value = preferences.getString("mine", null);

        assertEquals("12.82 -05.12", value);

    }
}