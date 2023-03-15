package com.example.cse110project;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.LiveData;
import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;

import com.example.cse110project.activity.CompassActivity;
import com.example.cse110project.activity.EnterFriendActivity;
import com.example.cse110project.model.User;
import com.example.cse110project.model.UserAPI;
import com.example.cse110project.model.UserDao;
import com.example.cse110project.model.UserDatabase;
import com.example.cse110project.model.UserRepository;
import com.example.cse110project.service.RotateCompass;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.time.Instant;
import java.util.Hashtable;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import java.util.Enumeration;

@RunWith(RobolectricTestRunner.class)
public class US3Tests {
    UserAPI api = new UserAPI();

    @Test
    public void getTimeTest() {
        String currTime = Instant.now().toString();
        assertNotNull(currTime);
        System.out.println("\n" + currTime + "\n");
    }

    @Test
    public void getRemoteUserLocationTest() throws ExecutionException, InterruptedException, TimeoutException {
        Future<User> userFuture = api.getUserLocationAsync("some private code");
        User userInfo = userFuture.get(1, TimeUnit.SECONDS);
        assertNotNull(userInfo);
        System.out.println("\n"+ userInfo.toJSON() + "\n");
    }

    @Test
    public void checkDaoInsert() throws ExecutionException, InterruptedException, TimeoutException {
        UserAPI api = UserAPI.provide();
        Future<User> futureUser = api.getUserLocationAsync("19");
        User user = futureUser.get(1, TimeUnit.SECONDS);

        Context context = ApplicationProvider.getApplicationContext();
        UserDatabase db = UserDatabase.provide(context);
        UserDao dao = db.getDao();
        dao.upsert(user);

        boolean isInserted = dao.exists("19");
        assertEquals(true, isInserted);
    }

    @Test
    public void checkDaoGet() throws ExecutionException, InterruptedException, TimeoutException {
        UserAPI api = new UserAPI();
        Future<User> futureUser = api.getUserLocationAsync("19");
        User user = futureUser.get(1, TimeUnit.SECONDS);

        Context context = ApplicationProvider.getApplicationContext();
        UserDatabase db = UserDatabase.provide(context);
        UserDao dao = db.getDao();
        dao.upsert(user);

        assertEquals(null, user.private_code);

        //LiveData<User> liveUser = dao.get("19");
    }

    @Test
    public void testJsonForPut() throws ExecutionException, InterruptedException, TimeoutException {
        User putUser = new User("l7har", "0000", "A place",35, 25);
        var executor = Executors.newSingleThreadExecutor();
        executor.submit(() -> {
            api.putUserLocation(putUser);
        });
        Future<User> future = api.getUserLocationAsync(putUser.public_code);
        User userFromCloud = future.get(1, TimeUnit.SECONDS);

        assertEquals(userFromCloud.public_code, putUser.public_code);
        assertEquals(userFromCloud.label, putUser.label);
        assertEquals(userFromCloud.latitude, putUser.latitude, .0001);
        assertEquals(userFromCloud.longitude, putUser.longitude, .0001);

    }

    @Test
    public void getThenPut() throws ExecutionException, InterruptedException, TimeoutException {
        Future<User> future = api.getUserLocationAsync("spirit");
        User user = future.get(1, TimeUnit.SECONDS);
        System.out.println("\n"+ user.toJSON() + "\n");
        user.private_code = "1234";
        user.latitude = 100;
        user.longitude = -50;
        //System.out.println("\n"+ user.toJSON() + "\n");
        var putFuture = api.putUserAsync(user);
        String put = putFuture.get(1,TimeUnit.SECONDS);
        future = api.getUserLocationAsync("spirit");
        user = future.get(1, TimeUnit.SECONDS);
        System.out.println("\n"+ user.toJSON() + "\n");

        assertEquals(100, user.latitude, .0001);
        assertEquals(-50, user.longitude, .0001);
    }

    @Test
    public void testRecreateUser() {
        try (ActivityScenario<EnterFriendActivity> scenario = ActivityScenario.launch(EnterFriendActivity.class)){
            scenario.onActivity(activity -> {
                SharedPreferences prefs = activity.getSharedPreferences(Utilities.PREFERENCES_NAME, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor =prefs.edit();
                editor.putString(Utilities.USER_PUBLIC_UID, "abcdefg");
                editor.putString(Utilities.USER_PRIVATE_UID, "12345");
                editor.putString(Utilities.LABEL_NAME, "Tyler");
                editor.putFloat(Utilities.USER_LATITUDE, 20);
                editor.putFloat(Utilities.USER_LONGITUDE, 50);
                editor.putString(Utilities.CREATED_AT, Instant.EPOCH.toString());
                editor.putString(Utilities.UPDATED_AT, Instant.now().toString());

                editor.apply();

                activity.recreateUser(prefs);
                System.out.println(Utilities.personalUser.toJSON());

                assertNotNull(Utilities.personalUser);
            });
        }
    }


    @Test
    public void testCreateTextView() {
        Utilities.personalUser = new User("test","test","test",0,0);
        try (ActivityScenario<CompassActivity> scenario = ActivityScenario.launch(CompassActivity.class)){
            scenario.onActivity(activity -> {
                ConstraintLayout layout = activity.findViewById(R.id.compassLayout);
                Hashtable<String, TextView> table = activity.getTextViews();
                Enumeration<String> e = table.keys();

                int i = 0;
                while (e.hasMoreElements()) {
                    String key = e.nextElement();
                    TextView textView = table.get(key);
                    textView.setId(i);
                    View newTextView = layout.findViewById(i);
                    i++;
                    assertNotNull(newTextView);
                }
            });
        }
    }

    @Test
    public void testConstrainView() {
        Utilities.personalUser = new User("test","test","test",0,0);
        try (ActivityScenario<CompassActivity> scenario = ActivityScenario.launch(CompassActivity.class)){
            scenario.onActivity(activity -> {
                ConstraintLayout layout = activity.findViewById(R.id.compassLayout);
                Hashtable<String, TextView> table = activity.getTextViews();
                Enumeration<String> e = table.keys();

                int i = 0;
                while (e.hasMoreElements()) {
                    String key = e.nextElement();
                    TextView textView = table.get(key);
                    //TODO Change to allow zoomLevel param
                    //RotateCompass.constrainUser(textView, 32, 100, 32, -100);
                    var angle = (float) Utilities.findAngle(32, 100, 32, -100);
                    ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) textView.getLayoutParams();
                    assertEquals(layoutParams.circleAngle, angle);
                }
            });
        }
    }
}
