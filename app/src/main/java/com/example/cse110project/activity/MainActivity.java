package com.example.cse110project.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.cse110project.R;
import com.example.cse110project.Utilities;
import com.example.cse110project.activity.EnterFriendActivity;
import com.example.cse110project.activity.EnterNameActivity;
import com.example.cse110project.model.UserDatabase;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        var context = getApplicationContext();
        var db = UserDatabase.provide(context);
        var dao = db.getDao();

        var preferences = getSharedPreferences(Utilities.PREFERENCES_NAME, MODE_PRIVATE);

        if (preferences.getString(Utilities.USER_PUBLIC_UID, "").equals("")) {
            Intent intent = new Intent(this, EnterNameActivity.class);
            startActivity(intent);
        }

        else {
            Intent intent = new Intent(this, EnterFriendActivity.class);
            startActivity(intent);
        }

    }
}


