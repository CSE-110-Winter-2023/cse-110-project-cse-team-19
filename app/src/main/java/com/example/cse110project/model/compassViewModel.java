package com.example.cse110project.model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class compassViewModel extends AndroidViewModel {
    private LiveData<User> users;
    private final UserRepository repo;

    public compassViewModel(@NonNull Application application) {
        super(application);
        var context = application.getApplicationContext();
        var db = UserDatabase.provide(context);
        var dao = db.getDao();
        this.repo = new UserRepository(dao);
    }

    public LiveData<User> getUser(String public_id) {
        if (users == null) {
            users = repo.getSynced(public_id);
        }
        return users;
    }


}
