package com.example.cse110project;
import static org.junit.Assert.assertEquals;

import android.util.Pair;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.MutableLiveData;
import androidx.test.core.app.ActivityScenario;

import com.example.cse110project.activity.CompassActivity;
import com.example.cse110project.model.User;
import com.example.cse110project.service.ConstrainUserService;
import com.example.cse110project.service.LocationService;
import com.example.cse110project.service.TimeService;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.concurrent.TimeUnit;

@RunWith(RobolectricTestRunner.class)
public class US5Tests {
    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Test
    public void test_GPS_Inactive_Startup() {
        Utilities.personalUser = new User("test","test","test",0,0);

        try (ActivityScenario<CompassActivity> scenario = ActivityScenario.launch(CompassActivity.class)){
            scenario.onActivity(activity -> {
                var timeService = TimeService.singleton();

                var mockTime = new MutableLiveData<Long>();
                timeService.setMockTimeSource(mockTime);
                // No need to worry about telling the activity to refresh where it gets data.

                Long currentTime = System.currentTimeMillis();
                Long testTime = currentTime + 600000;
                mockTime.setValue(testTime);

                TextView gpsStatus = activity.findViewById(R.id.gpsStatus);
                String expectedStatus = "No GPS Signal Since Startup";
                assertEquals(expectedStatus, gpsStatus.getText());

                ImageView gpsActive = activity.findViewById(R.id.gpsActive);
                assertEquals(4, gpsActive.getVisibility());

                ImageView gpsInactive = activity.findViewById(R.id.gpsInactive);
                assertEquals(0, gpsInactive.getVisibility());
            });
        }
    }

    @Test
    public void test_GPS_Inactive() {
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

    @Test
    public void test_GPS_Active() {
        Utilities.personalUser = new User("test", "test", "test", 0, 0);

        var scenario = ActivityScenario.launch(CompassActivity.class);
        scenario.moveToState(Lifecycle.State.STARTED);

        scenario.onActivity(activity -> {
            var timeService = TimeService.singleton();
            var mockTime = new MutableLiveData<Long>();
            timeService.setMockTimeSource(mockTime);

            Pair<Double, Double> testLocation = new Pair<Double, Double>(1.0, 1.0);
            activity.onLocationChanged(testLocation);

            Long currentTime = System.currentTimeMillis();
            Long testTime = currentTime;
            mockTime.setValue(testTime);

            TextView gpsStatus = activity.findViewById(R.id.gpsStatus);
            assertEquals(4, gpsStatus.getVisibility());

            ImageView gpsActive = activity.findViewById(R.id.gpsActive);
            assertEquals(0, gpsActive.getVisibility());

            ImageView gpsInactive = activity.findViewById(R.id.gpsInactive);
            assertEquals(4, gpsInactive.getVisibility());
        });
    }
}
