package com.example.cse110project;


import static android.content.Context.MODE_PRIVATE;
import static org.junit.Assert.assertEquals;

import android.Manifest;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.Shadows;
import org.robolectric.shadows.ShadowApplication;

@RunWith(RobolectricTestRunner.class)
public class E2EActivityScenario {

    @Test
    public void testRestart(){
        Application application = ApplicationProvider.getApplicationContext();
        ShadowApplication app = Shadows.shadowOf(application);
        app.grantPermissions(android.Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION);
        SharedPreferences preferences = InstrumentationRegistry.getInstrumentation().getTargetContext().getSharedPreferences("my_preferences", MODE_PRIVATE);

        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(Utilities.PERSONAL_HOME_LABEL, "Taylor's House");
        editor.putString(Utilities.PERSONAL_HOME_COORDINATES, "12.82, -05.12");
        editor.putString(Utilities.ORIENTATION_LABEL, "");
        editor.apply();

        assertEquals("", Utilities.ORIENTATION_LABEL);

        //Simulates app restart
        Activity activity = Robolectric.buildActivity(Activity.class).create().start().resume().get();
        activity.finish();
        activity = Robolectric.buildActivity(Activity.class).create().start().resume().get();

        String retrievedCoord = activity.getSharedPreferences(Utilities.PREFERENCES_NAME, Context.MODE_PRIVATE).getString(Utilities.PERSONAL_HOME_COORDINATES, null);
        String retrievedLabel = activity.getSharedPreferences(Utilities.PREFERENCES_NAME, Context.MODE_PRIVATE).getString(Utilities.PERSONAL_HOME_LABEL, null);

        assertEquals("12.82, -05.12", retrievedCoord);
        assertEquals("Taylor's House", retrievedLabel);
    }
}
