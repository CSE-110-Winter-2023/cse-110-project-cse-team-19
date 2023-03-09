package com.example.cse110project;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import android.content.Context;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.cse110project.model.User;
import com.example.cse110project.model.UserDao;
import com.example.cse110project.model.UserDatabase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;

@RunWith(AndroidJUnit4.class)
public class US2Tests {
    private UserDao dao;
    private UserDatabase db;

    @Before
    public void createDb() {
        Context context = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(context, UserDatabase.class)
                .allowMainThreadQueries()
                .build();
        dao = db.getDao();
    }

    @After
    public void closeDb() throws IOException{
        db.close();
    }

    @Test
    public void testUpsertNewUser(){
        User user1 = new User("1", "friend1", 32, 114);
        User user2 = new User("2", "friend2", 33, 114);

        long id1 = dao.upsert(user1);
        long id2 = dao.upsert(user2);

        assertNotEquals(id1,id2);
    }

    /*
    @Test
    public void testUpsertChangeUser(){
        try(ActivityScenario<EnterFriendActivity> scenario = ActivityScenario.launch(EnterFriendActivity.class)){
            scenario.onActivity(activity -> {
                var context = ApplicationProvider.getApplicationContext();
                var db = UserDatabase.provide(context);
                var dao = db.getDao();

                User user = new User("53", "label", 33, 115);
                dao.upsert(user);
                User liveUser = dao.get("53");
                assertEquals(user.toJSON(), liveUser.toJSON());

                user = new User("53", "label5", 33, 115);
                dao.upsert(user);
                liveUser = dao.get("53");
                assertEquals(user.toJSON(), liveUser.toJSON());
            });
        }
    }
     */

    @Test
    public void testGet(){
        User user1 = new User("4", "friend4", 33, 114);
        long id = dao.upsert(user1);

//        LiveData<User> user = dao.get("4");
//        user.observe(ApplicationProvider.getApplicationContext(), returnedUser->{
//            assertEquals("4",user1.public_code);
//            assertEquals(user1.label, returnedUser.label);
//            assertEquals(user1.latitude, returnedUser.latitude);
//            assertEquals(user1.longitude, returnedUser.longitude);
//        });

        User user = dao.get("4");
        assertEquals("4", user.public_code);
        assertEquals(user1.label, user.label);
        assertEquals(user1.latitude, user.latitude,0);
        assertEquals(user1.longitude, user.longitude,0);
    }

    @Test
    public void testDelete(){
        User user1 = new User("5", "friend5", 33, 114);
        long id = dao.upsert(user1);

        user1 = dao.get("5");
        int deletedItem = dao.delete(user1);
        assertEquals(1, deletedItem);
        assertNull(dao.get("5"));
    }
}
