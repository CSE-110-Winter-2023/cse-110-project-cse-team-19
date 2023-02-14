package com.example.cse110project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.TextView;
import android.os.Bundle;
import android.widget.ImageView;

import java.util.Optional;

public class CompassActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        ImageView homeIcon = findViewById(R.id.red_icon);
        ImageView familyIcon = findViewById(R.id.blue_icon);
        ImageView friendIcon = findViewById(R.id.purple_icon);

        homeIcon.setVisibility(View.INVISIBLE);
        familyIcon.setVisibility(View.INVISIBLE);
        friendIcon.setVisibility(View.INVISIBLE);

        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        String homeLatLong = preferences.getString("myLabel", "default_value");
        String friendLatLong = preferences.getString("friendLabel", "default_value");
        String familyLatLong = preferences.getString("familyLabel", "default_value");

        if(!homeLatLong.equals("default_value")){
            homeIcon.setVisibility(View.VISIBLE);
        }

        if(!friendLatLong.equals("default_value")){
            friendIcon.setVisibility(View.VISIBLE);
        }

        if(!familyLatLong.equals("default_value")){
            familyIcon.setVisibility(View.VISIBLE);
        }

        setContentView(R.layout.activity_compass);setContentView(R.layout.activity_compass);

    }

}