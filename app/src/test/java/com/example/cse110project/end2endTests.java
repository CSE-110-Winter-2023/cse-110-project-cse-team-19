package com.example.cse110project;

import static android.content.Context.MODE_PRIVATE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import static java.security.AccessController.getContext;

import android.Manifest;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Pair;
import android.widget.Button;
import android.widget.EditText;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.core.app.ActivityScenario;
import androidx.test.platform.app.InstrumentationRegistry;

import com.example.cse110project.activity.CompassActivity;
import com.example.cse110project.activity.EnterFriendActivity;
import com.example.cse110project.activity.EnterNameActivity;
import com.example.cse110project.model.API;
import com.example.cse110project.model.EnterFriendViewModel;
import com.example.cse110project.model.User;
import com.example.cse110project.model.UserAPI;
import com.example.cse110project.model.UserDatabase;
import com.example.cse110project.service.ConstrainUserService;
import com.example.cse110project.service.LocationService;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.Shadows;
import org.robolectric.shadows.ShadowApplication;

import java.security.AccessControlContext;
import java.util.Hashtable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@RunWith(RobolectricTestRunner.class)
public class end2endTests {
    API api = new UserAPI();
    @Test
    public void US1end2end() {
        try(ActivityScenario<EnterNameActivity> scenario = ActivityScenario.launch(EnterNameActivity.class)){
            scenario.onActivity(activity -> {
                var context = ApplicationProvider.getApplicationContext();
                var db = UserDatabase.provide(context);
                var dao = db.getDao();

                EditText name = activity.findViewById(R.id.enterNameEditText);
                name.setText("Tyler");
                Button nextBtn = activity.findViewById(R.id.enterNameBtn);
                nextBtn.performClick();

                User ourUser = Utilities.personalUser;

                assertEquals("Tyler", ourUser.label);
                assertNotNull(ourUser.public_code);
                assertNotNull(ourUser.private_code);
                assertEquals(0.0, ourUser.longitude, .0001);
                assertEquals(0.0, ourUser.latitude, .0001);
            });
        }
    }

    @Test
    public void US2end2end(){
        try(ActivityScenario<EnterFriendActivity> scenario = ActivityScenario.launch(EnterFriendActivity.class)){
            scenario.onActivity(activity -> {
                var context = ApplicationProvider.getApplicationContext();
                var db = UserDatabase.provide(context);
                var dao = db.getDao();

                User user = new User("UCSD", "UCSD", "UCSD", 0, 0);
                dao.upsert(user);

                EnterFriendViewModel viewModel = new ViewModelProvider(activity).get(EnterFriendViewModel.class);

                try {
                    viewModel.getOrCreateUser("UCSD", context);
                } catch (ExecutionException e) {
                    throw new RuntimeException(e);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } catch (TimeoutException e) {
                    throw new RuntimeException(e);
                }
                EditText enterUIDs = activity.findViewById(R.id.enterUIDEditText);
                enterUIDs.setText("UCSD");

                assertEquals(true, dao.exists("UCSD"));


                Button nextBtn = activity.findViewById(R.id.nextBtn);
                nextBtn.performClick();
            });
        }
    }

    // Trying to test that a User upserted to the North will be displayed
    @Test
    public void US3end2end() {
        Context context = ApplicationProvider.getApplicationContext();
        var db = UserDatabase.provide(context);
        var dao = db.getDao();
        User north = new User ("some_user", "some_user", "North", 90, 0);
        dao.upsert(north);
        Utilities.personalUser = new User("me", "me", "Tyler", 0,0);

        try(ActivityScenario<CompassActivity> scenario = ActivityScenario.launch(CompassActivity.class)){
            scenario.onActivity(activity -> {

                assertEquals(true, dao.exists("some_user"));

                activity.mockLocation();
                LocationService location = activity.getLocationService();
                LiveData<Pair<Double, Double>> loc = location.getLocation();
                loc.observe(activity, coords -> {
                    assertEquals(0.0, coords.first, .000);
                    assertEquals(0.0, coords.second, .000);
                });

                Hashtable<String, ConstrainUserService> table = activity.getTextViews();
                assertNotNull(table.get("some_user"));

                ConstrainUserService northUser = table.get("some_user");
                assertEquals("90.0, 0.0", northUser.toString());
            });
        }
    }

//    @Test
//    public void US4 end2end() {
//
//    }
}
