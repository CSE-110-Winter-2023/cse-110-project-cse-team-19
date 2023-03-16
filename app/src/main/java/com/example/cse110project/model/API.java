package com.example.cse110project.model;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeoutException;

public interface API {
    public User getUserLocation(String public_code);
    public Future<User> getUserLocationAsync(String title) throws ExecutionException, InterruptedException, TimeoutException;
    public String putUserLocation(User user);
    public Future<String> putUserAsync(User user);

}
