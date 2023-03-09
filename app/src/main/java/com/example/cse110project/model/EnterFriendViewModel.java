package com.example.cse110project.model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class EnterFriendViewModel extends AndroidViewModel {
    private LiveData<List<User>> users;
    private final UserRepository repo;

    public EnterFriendViewModel(@NonNull Application application) {
        super(application);
        var context = application.getApplicationContext();
        var db = UserDatabase.provide(context);
        var dao = db.getDao();
        this.repo = new UserRepository(dao);
    }

    /**
     * Load all users from the local database using room
     * @return List of all Users
     */
    public LiveData<List<User>> getUsers() {
        if (users == null) {
            users = repo.getAllLocal();
        }
        return users;
    }

    /**
     *
     */

    public LiveData<User> getOrCreateUser(String public_id) throws ExecutionException, InterruptedException, TimeoutException {
        if (!repo.existsLocal(public_id)) {
            UserAPI api = new UserAPI();
            Future<User> future = api.getUserLocationAsync(public_id);
            User user = future.get(1, TimeUnit.SECONDS);

            // An endpoint that doesn't exist results in a user being created with just
            // latitude and longitude
            if (user.public_code == null) {
                return null;
            }
            // var user = new User(public_id, "same label", 0, 0);
            repo.upsertLocal(user, false);
        }
        return repo.getLocal(public_id);
    }

    public void delete(User user) { repo.deleteUser(user); }
}
