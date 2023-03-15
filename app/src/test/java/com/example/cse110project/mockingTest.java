package com.example.cse110project;
import static android.content.Context.MODE_PRIVATE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.EditText;

import androidx.room.Room;
import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.example.cse110project.activity.CompassActivity;
import com.example.cse110project.activity.EnterFriendActivity;
import com.example.cse110project.activity.mockUrlActivity;
import com.example.cse110project.model.API;
import com.example.cse110project.model.User;
import com.example.cse110project.model.UserDao;
import com.example.cse110project.model.UserDatabase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;

@RunWith(AndroidJUnit4.class)
public class mockingTest {

    @Test
    public void testMock() {
        SharedPreferences preferences = InstrumentationRegistry.getInstrumentation().getTargetContext().getSharedPreferences("my_preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(Utilities.MOCK_URL, "https://socialcompass.goto.ucsd.edu/location/");
        editor.apply();
        try (ActivityScenario<CompassActivity> scenario = ActivityScenario.launch(CompassActivity.class)) {
            scenario.onActivity(activity -> {

                SharedPreferences prefs = activity.getSharedPreferences(Utilities.PREFERENCES_NAME, MODE_PRIVATE);

                assertEquals("https://socialcompass.goto.ucsd.edu/location/", prefs.getString(Utilities.MOCK_URL,""));

                API activityAPI = activity.getApi();

                assertEquals("I am the mocking API!", activityAPI.toString());

            });
        }
    }

}
