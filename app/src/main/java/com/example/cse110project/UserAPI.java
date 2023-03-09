package com.example.cse110project;

import android.util.Log;

import androidx.annotation.AnyThread;
import androidx.annotation.MainThread;
import androidx.annotation.WorkerThread;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class UserAPI {
    private volatile static UserAPI instance = null;

    private OkHttpClient client;

    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");

    public UserAPI() { this.client = new OkHttpClient(); }

    public static UserAPI provide() {
        if (instance == null) {
            instance = new UserAPI();
        }
        return instance;
    }

    public String getUserLocation(String public_code) {
        public_code = public_code.replace(" ", "%20");
        var request = new Request.Builder()
                .url("https://socialcompass.goto.ucsd.edu/location/" + public_code)
                .method("GET", null)
                .build();

        try (var response = client.newCall(request).execute()) {
            var body = response.body().string();
            Log.d("UserLocationCalled", body);
            return body;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Future<String> getUserLocationAsync(String title) throws ExecutionException, InterruptedException, TimeoutException {
        var executor = Executors.newSingleThreadExecutor();
        var future = executor.submit(() -> getUserLocation(title));
        // We can use future.get(1, SECONDS) to wait for the result.
        return future;
    }
}
