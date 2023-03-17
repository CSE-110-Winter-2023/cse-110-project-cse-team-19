package com.example.cse110project;

import static android.content.Context.MODE_PRIVATE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import android.Manifest;
import android.app.Application;
import android.content.SharedPreferences;
import android.util.Pair;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.MutableLiveData;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.core.app.ActivityScenario;
import androidx.test.platform.app.InstrumentationRegistry;

import com.example.cse110project.activity.CompassActivity;
import com.example.cse110project.activity.EnterNameActivity;
import com.example.cse110project.model.User;
import com.example.cse110project.service.TimeService;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.Shadows;
import org.robolectric.shadows.ShadowApplication;

@RunWith(RobolectricTestRunner.class)
public class US1Tests {
    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

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

    @Test
    public void US5_test_GPS_Inactive() {
        Utilities.personalUser = new User("test","test","test",0,0);

        var scenario = ActivityScenario.launch(CompassActivity.class);
        scenario.moveToState(Lifecycle.State.STARTED);

        scenario.onActivity(activity -> {
            var timeService = TimeService.singleton();
            var mockTime = new MutableLiveData<Long>();
            timeService.setMockTimeSource(mockTime);

            Pair<Double, Double> testLocation = new Pair<Double, Double>(1.0, 1.0);
            activity.onLocationChanged(testLocation);

            Long currentTime = System.currentTimeMillis();
            Long testTime = currentTime + 600000;
            mockTime.setValue(testTime);

            TextView gpsStatus = activity.findViewById(R.id.gpsStatus);
            String expectedStatus = "GPS Inactive for: " + Utilities.formatTime(testTime - currentTime) + " Minutes";
            assertEquals(expectedStatus, gpsStatus.getText());

            ImageView gpsActive = activity.findViewById(R.id.gpsActive);
            assertEquals(4, gpsActive.getVisibility());

            ImageView gpsInactive = activity.findViewById(R.id.gpsInactive);
            assertEquals(0, gpsInactive.getVisibility());
        });

    }
}
