package com.example.cse110project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class EnterFriendActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_friend);
    }

    public void onNextPressed(View view) {
        Intent intent = new Intent(this, CompassActivity.class);
        startActivity(intent);
        finish();
    }

    public void onAddPressed(View view) throws ExecutionException, InterruptedException, TimeoutException {
        EditText friendUIDTextBox = findViewById(R.id.enterUIDEditText);
        String friendUID = friendUIDTextBox.getText().toString();
        UserAPI api = UserAPI.provide();
        Future<String> userFriendFuture = api.getUserLocationAsync(friendUID);
        String userFriendJson = userFriendFuture.get(1, TimeUnit.SECONDS);

        User userFriend = User.fromJSON(userFriendJson);
        UserDatabase db = UserDatabase.provide(this);
        UserDao dao = db.getDao();

        dao.upsert(userFriend);
    }
}