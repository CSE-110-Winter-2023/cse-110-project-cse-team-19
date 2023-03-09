package com.example.cse110project.model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import java.time.Instant;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class UserRepository {
    private final UserDao dao;
    private ScheduledFuture<?> poller;
    public UserRepository(UserDao dao) {
        this.dao = dao;
    }

    public LiveData<User> getSynced(String public_id) {
        var user = new MediatorLiveData<User>();

        Observer<User> updateFromRemote = theirUser -> {
            var ourUser = user.getValue();
            if (theirUser == null) return;
            if (ourUser == null || Instant.parse(ourUser.updated_at).compareTo(Instant.parse(theirUser.updated_at)) < 0) {
                upsertLocal(theirUser);
            }
        };
        // If we get a local then we pass it on
        user.addSource(getLocal(public_id), user::postValue);
        // If we get a remote update pass it on
        user.addSource(getRemote(public_id), updateFromRemote);

        return user;
    }

    public void upsertSynced(User user) { upsertLocal(user); }

    public LiveData<User> getLocal(String public_id) {
        return dao.get(public_id);
    }

    public LiveData<List<User>> getAllLocal() { return dao.getAll(); }

    public void upsertLocal(User user, boolean incrementVersion) {
        if (incrementVersion) user.updated_at = Instant.now().toString();
        user.updated_at = Instant.now().toString();
        dao.upsert(user);
    }

    public void upsertLocal(User user) { upsertLocal(user, true); }

    public void deleteUser(User user) { dao.delete(user); }

    public boolean existsLocal(String public_id) { return dao.exists(public_id); }

    public LiveData<User> getRemote(String public_id) {
        if (this.poller != null && !this.poller.isCancelled()) {
            poller.cancel(true);
        }

        UserAPI api = new UserAPI();

        MutableLiveData<User> remoteUser = new MutableLiveData<>();
        var executor = Executors.newSingleThreadScheduledExecutor();

        poller = executor.scheduleAtFixedRate(() -> {
            User user = api.getUserLocation(public_id);
            remoteUser.postValue(user);
        }, 0, 3000, TimeUnit.MILLISECONDS);

        return remoteUser;
    }
}
