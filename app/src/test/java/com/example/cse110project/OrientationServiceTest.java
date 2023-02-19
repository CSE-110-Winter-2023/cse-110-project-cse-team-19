package com.example.cse110project;

import static org.junit.Assert.assertEquals;

import android.Manifest;
import android.app.Application;
import android.widget.TextView;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.MutableLiveData;
import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowApplication;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

@RunWith(RobolectricTestRunner.class)
public class OrientationServiceTest {
    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();
    @Test
    public void test_orientation_service(){
        Application application = ApplicationProvider.getApplicationContext();
        ShadowApplication app = Shadows.shadowOf(application);
        app.grantPermissions(android.Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION);

        var testValue = 3.5f;
        var scenario = ActivityScenario.launch(CompassActivity.class);
        scenario.moveToState(Lifecycle.State.STARTED);
        scenario.onActivity(activity -> {
            var orientationService = OrientationService.singleton(activity);

            var mockOrientation = new MutableLiveData<Float>();
            orientationService.setMockOrientationSource(mockOrientation);

            mockOrientation.setValue(testValue);
            TextView textView = activity.findViewById(R.id.orientation);

            var expected = Float.toString(testValue);
            var observed = textView.getText().toString();
            assertEquals(expected, observed);
        });
    }
}
