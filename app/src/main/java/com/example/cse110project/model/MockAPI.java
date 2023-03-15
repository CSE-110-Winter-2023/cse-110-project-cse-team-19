package com.example.cse110project.model;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeoutException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class MockAPI implements API{
    private volatile static MockAPI instance = null;

    private OkHttpClient client;

    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    private String mockUrl;

    public MockAPI(String mockUrl) {
        this.client = new OkHttpClient();
        this.mockUrl = mockUrl;
    }

    @Override
    public User getUserLocation(String public_code) {
        public_code = public_code.replace(" ", "%20");
        var request = new Request.Builder()
                .url(this.mockUrl + public_code)
                .method("GET", null)
                .build();

        try (var response = client.newCall(request).execute()) {
            var body = response.body().string();
            User user = User.fromJSON(body);
            return user;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Future<User> getUserLocationAsync(String title) throws ExecutionException, InterruptedException, TimeoutException {
        var executor = Executors.newSingleThreadExecutor();
        var future = executor.submit(() -> getUserLocation(title));
        // We can use future.get(1, SECONDS) to wait for the result.
        return future;
    }

    @Override
    public String putUserLocation(User user) {
        String pub_code = user.public_code;
        String url = pub_code.replace(" ", "%20");

        String userJSON = user.toJSON();
        RequestBody JSONbody = RequestBody.create(userJSON, JSON);

        var request = new Request.Builder()
                .url(this.mockUrl + url)
                .method("PUT", JSONbody)
                .build();

        try (var response = client.newCall(request).execute()) {;
            var body = response.body().string();
            return body;

        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public Future<String> putUserAsync(User user) {
        var executor = Executors.newSingleThreadExecutor();
        var future = executor.submit(() -> putUserLocation(user));
        // We can use future.get(1, SECONDS) to wait for the result.
        return future;
    }

    @Override
    public String toString() {
        return "I am the mocking API!";
    }
}
