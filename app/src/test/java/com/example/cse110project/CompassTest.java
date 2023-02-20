package com.example.cse110project;

import static android.content.Context.MODE_PRIVATE;
import static org.junit.Assert.assertEquals;

import android.Manifest;
import android.app.Application;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.TextView;
import android.widget.ImageView;
import androidx.lifecycle.Lifecycle;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.core.app.ActivityScenario;
import androidx.test.platform.app.InstrumentationRegistry;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.Shadows;
import org.robolectric.shadows.ShadowApplication;



@RunWith(RobolectricTestRunner.class)
public class CompassTest {

    @Test
    public void test_MyHomeDisplay(){

        SharedPreferences preferences = InstrumentationRegistry.getInstrumentation().getTargetContext().getSharedPreferences("my_preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("mine", "12.82, -05.12");
        editor.apply();

        Application application = ApplicationProvider.getApplicationContext();

        ShadowApplication app = Shadows.shadowOf(application);
        app.grantPermissions(android.Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION);

        var scenario = ActivityScenario.launch(CompassActivity.class);
        scenario.moveToState(Lifecycle.State.CREATED);
        scenario.onActivity(activity -> {
            ImageView homeIcon = activity.findViewById(R.id.red_icon);
            TextView homeText = activity.findViewById(R.id.homeLabelDisplay);
            ImageView familyIcon = activity.findViewById(R.id.blue_icon);
            TextView familyText = activity.findViewById(R.id.familyLabelDisplay);
            ImageView friendIcon = activity.findViewById(R.id.purple_icon);
            TextView friendText = activity.findViewById(R.id.friendLabelDisplay);

            assertEquals(View.VISIBLE, homeIcon.getVisibility());
            assertEquals(View.VISIBLE, homeText.getVisibility());
            assertEquals(View.INVISIBLE, familyIcon.getVisibility());
            assertEquals(View.INVISIBLE, familyText.getVisibility());
            assertEquals(View.INVISIBLE, friendIcon.getVisibility());
            assertEquals(View.INVISIBLE, friendText.getVisibility());
        });

    }

    @Test
    public void mock_orientation_test (){
        SharedPreferences preferences = InstrumentationRegistry.getInstrumentation().getTargetContext().getSharedPreferences("my_preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("orientationLabel", "100");
        editor.apply();
        var value = Float.parseFloat(preferences.getString("orientationLabel", null));

        var scenario2 = ActivityScenario.launch(CompassActivity.class);
        scenario2.moveToState(Lifecycle.State.STARTED);
        scenario2.onActivity(activity -> {
            TextView textView = activity.findViewById(R.id.orientation);
            var observed = Float.parseFloat(textView.getText().toString());
            assertEquals(value, observed, 0);
        });
    }
}
