package com.example.cse110project.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.cse110project.R;
import com.example.cse110project.model.UserDao;
import com.example.cse110project.model.UserDatabase;

public class CompassActivity extends AppCompatActivity {

    private UserDao dao;
    private UserDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compass);

        TextView userDisplayTest = findViewById(R.id.userDisplayTest);
        this.db = UserDatabase.provide(this);
        this.dao = db.getDao();
        userDisplayTest.setText(dao.getAll().toString());
    }
}