package com.example.cse110project;

import static android.content.Context.MODE_PRIVATE;
import static org.junit.Assert.assertEquals;

import android.Manifest;
import android.app.Activity;
import android.app.Application;
import android.content.SharedPreferences;

import android.view.View;

import android.widget.TextView;
import android.widget.ImageView;

import androidx.lifecycle.Lifecycle;
import androidx.test.core.app.ApplicationProvider;
import android.content.Context;
import androidx.test.core.app.ActivityScenario;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.robolectric.RobolectricTestRunner;
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowApplication;
import org.mockito.Mockito;
import android.content.pm.PackageManager;


@RunWith(RobolectricTestRunner.class)
public class CompassTest {
    
    @Test
    public void test_UI_exists(){
        /*
        var scenario = ActivityScenario.launch(CompassActivity.class);
        scenario.onActivity(activity -> {
            ImageView compass = (ImageView) activity.findViewById(R.id.compass_image);
            ImageView redIcon = (ImageView) activity.findViewById(R.id.red_icon);
            //assertEquals(0,0);

            assertEquals(View.VISIBLE, compass.getVisibility());
            assertEquals(View.VISIBLE, redIcon.getVisibility());
        });
        */
        //ImageView compass = findViewById(R.id.compass_image);
    }

    @Test
    public void test_MyHomeDisplay(){

        SharedPreferences preferences = InstrumentationRegistry.getInstrumentation().getTargetContext().getSharedPreferences("my_preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("mine", "12.82 -05.12");
        editor.apply();

        Application application = ApplicationProvider.getApplicationContext();

        ShadowApplication app = Shadows.shadowOf(application);
        app.grantPermissions(android.Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION);

        var scenario = ActivityScenario.launch(MainActivity.class);
        scenario.moveToState(Lifecycle.State.CREATED);
        scenario.onActivity(activity -> {
            ImageView homeIcon = activity.findViewById(R.id.red_icon);
            TextView homeText = activity.findViewById(R.id.homeLabelDisplay);
            assertEquals(View.VISIBLE, homeIcon.getVisibility());
            assertEquals(View.VISIBLE, homeText.getVisibility());
        });

    }
    @Test public void testDegrees(){
        double gliderPortDegrees = Utilities.findAngle(32.88129, -117.23758, 32.89075438019187, -117.25108298507078);
        double sanDiegoCountyDegrees = Utilities.findAngle(32.88129, -117.23758, 32.778364, -118.116286);
        System.out.println(gliderPortDegrees);
        System.out.println(sanDiegoCountyDegrees);
    }
}
