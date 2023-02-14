package com.example.cse110project;

import static org.junit.Assert.assertEquals;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ImageView;
import androidx.lifecycle.Lifecycle;

import androidx.test.core.app.ActivityScenario;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
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
            assertEquals(View.VISIBLE, redIcon.getVisibility());
        });
        //ImageView compass = findViewById(R.id.compass_image);
    }

    @Test
    public void test_MyHomeDisplay(){

        CoordinateActivity activity = Robolectric.buildActivity(CoordinateActivity.class).create().start().resume().get();
        LabelActivity labelActivity = Robolectric.buildActivity(LabelActivity.class).create().start().resume().get();
        CompassActivity compassActivity = Robolectric.buildActivity(CompassActivity.class).create().start().resume().get();

        EditText myLabel = activity.findViewById(R.id.personalHomeCoords);
        myLabel.setText("12.82 -05.12");

        Button nextButton = activity.findViewById(R.id.next_btn);
        nextButton.performClick();

        Button submitButton = labelActivity.findViewById(R.id.submit_btn);
        submitButton.performClick();

        ImageView homeIcon = compassActivity.findViewById(R.id.red_icon);

        assertEquals(View.VISIBLE, homeIcon.getVisibility());

    }
}
