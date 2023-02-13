package com.example.cse110project;

import static org.junit.Assert.assertEquals;

import android.view.View;
import android.widget.TextView;
import android.widget.ImageView;
import androidx.lifecycle.Lifecycle;

import androidx.test.core.app.ActivityScenario;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

@RunWith(RobolectricTestRunner.class)
public class CompassTest {
    @Test
    public void test_UI_exists(){
        var scenario = ActivityScenario.launch(CompassActivity.class);
        scenario.onActivity(activity -> {
            ImageView compass = (ImageView) activity.findViewById(R.id.compass_image);
            ImageView redIcon = (ImageView) activity.findViewById(R.id.red_icon);
            //assertEquals(0,0);

            assertEquals(View.VISIBLE, compass.getVisibility());
            assertEquals(View.VISIBLE, compass.getVisibility());
        });
        //ImageView compass = findViewById(R.id.compass_image);
    }
}
