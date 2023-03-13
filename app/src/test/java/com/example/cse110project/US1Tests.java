package com.example.cse110project;

import static android.content.Context.MODE_PRIVATE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import android.Manifest;
import android.app.Application;
import android.content.SharedPreferences;
import android.widget.Button;
import android.widget.EditText;

import androidx.lifecycle.Lifecycle;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.core.app.ActivityScenario;
import androidx.test.platform.app.InstrumentationRegistry;

import com.example.cse110project.activity.EnterNameActivity;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.Shadows;
import org.robolectric.shadows.ShadowApplication;

@RunWith(RobolectricTestRunner.class)
public class US1Tests {

    @Test
    public void getUID(){
        String testUID = Utilities.createUID();
        System.out.println(testUID);
        assertNotNull(testUID);
    }

    @Test
    public void checkPreferences(){
        Application application = ApplicationProvider.getApplicationContext();
        ShadowApplication app = Shadows.shadowOf(application);
        app.grantPermissions(android.Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION);

        var scenario = ActivityScenario.launch(EnterNameActivity.class);
        scenario.moveToState(Lifecycle.State.CREATED);
        scenario.onActivity(activity -> {
            SharedPreferences preferences = InstrumentationRegistry.getInstrumentation().getTargetContext().getSharedPreferences("my_preferences", MODE_PRIVATE);
            EditText nameBox = activity.findViewById(R.id.enterNameEditText);
            Button nextBtn = activity.findViewById(R.id.enterNameBtn);

            nameBox.setText("Tyler Culp");
            nextBtn.performClick();

            String preferencesName = preferences.getString(Utilities.LABEL_NAME, "");
            String preferencesUID = preferences.getString(Utilities.USER_PUBLIC_UID, "");

            assertEquals("Tyler Culp", preferencesName);
            assertNotNull(preferencesUID);
            assertNotEquals("", preferencesUID);
        });
    }

}
