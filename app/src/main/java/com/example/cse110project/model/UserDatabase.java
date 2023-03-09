package com.example.cse110project.model;

import android.content.Context;

import androidx.annotation.VisibleForTesting;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {User.class}, version = 2, exportSchema = false)
public abstract class UserDatabase extends RoomDatabase {
    private volatile static UserDatabase instance = null;
    public abstract UserDao getDao();

    public synchronized static UserDatabase provide(Context context) {
        if (instance == null) {
            instance = UserDatabase.make(context);
        }
        return instance;
    }

    private static UserDatabase make(Context context) {
        // TODO: Check whether or not user_app.db is actually what should be there
        return Room.databaseBuilder(context, UserDatabase.class, "user_app.db")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();
    }

    @VisibleForTesting
    public static void inject(UserDatabase testDatabase) {
        if (instance != null) {
            instance.close();
        }
        instance = testDatabase;
    }
}
