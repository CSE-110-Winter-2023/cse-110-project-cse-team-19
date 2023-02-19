package com.example.cse110project;

import static org.junit.Assert.assertEquals;

import android.Manifest;
import android.app.Application;
import android.util.Pair;
import android.widget.TextView;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.Shadows;
import org.robolectric.shadows.ShadowApplication;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;

@RunWith(RobolectricTestRunner.class)
public class LocationServiceTest {
    @Test
    public void test_location_service(){

        Application application = ApplicationProvider.getApplicationContext();
        ShadowApplication app = Shadows.shadowOf(application);
        app.grantPermissions(android.Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION);

        Pair<Double,Double> testValue = new Pair<>(37.48, -112.3216);
        var scenario = ActivityScenario.launch(CompassActivity.class);
        scenario.moveToState(Lifecycle.State.STARTED);
        scenario.onActivity(activity -> {
            var locationService = LocationService.singleton(activity);

            var mockLocation = new MutableLiveData<Pair<Double, Double>>();
            locationService.setMockLocationSource(mockLocation);
            activity.reobserveLocation();

            mockLocation.setValue(testValue);
            TextView textView = activity.findViewById(R.id.locationText);

            var expected = Utilities.formatLocation(testValue.first,testValue.second);
            var observed = textView.getText().toString();
            assertEquals(expected, observed);
        });

    }
}
