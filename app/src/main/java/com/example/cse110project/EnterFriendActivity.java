package com.example.cse110project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

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

    public void onAddPressed(View view) {

    }
}