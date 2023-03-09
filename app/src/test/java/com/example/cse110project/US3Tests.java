package com.example.cse110project;

import static android.content.Context.MODE_PRIVATE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import android.Manifest;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.ContentInfo;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ImageView;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.core.app.ActivityScenario;
import androidx.test.platform.app.InstrumentationRegistry;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.Shadows;
import org.robolectric.shadows.ShadowApplication;
import java.time.Instant;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

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
        Future<String> userFuture = api.getUserLocationAsync("some private code");
        String userInfo = userFuture.get(1, TimeUnit.SECONDS);
        assertNotNull(userInfo);
        System.out.println("\n"+ userInfo + "\n");
    }

    @Test
    public void checkDaoInsert() throws ExecutionException, InterruptedException, TimeoutException {
        UserAPI api = new UserAPI();
        Future<String> futureUser = api.getUserLocationAsync("19");
        String userJSON = futureUser.get(1, TimeUnit.SECONDS);
        User user = User.fromJSON(userJSON);

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
        Future<String> futureUser = api.getUserLocationAsync("19");
        String userJSON = futureUser.get(1, TimeUnit.SECONDS);
        User user = User.fromJSON(userJSON);

        Context context = ApplicationProvider.getApplicationContext();
        UserDatabase db = UserDatabase.provide(context);
        UserDao dao = db.getDao();
        dao.upsert(user);

        //LiveData<User> liveUser = dao.get("19");


    }
}
