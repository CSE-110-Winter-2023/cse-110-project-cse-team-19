package com.example.cse110project;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import android.content.Context;

import androidx.test.core.app.ApplicationProvider;

import com.example.cse110project.model.User;
import com.example.cse110project.model.UserAPI;
import com.example.cse110project.model.UserDao;
import com.example.cse110project.model.UserDatabase;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

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

        //LiveData<User> liveUser = dao.get("19");
    }
}
