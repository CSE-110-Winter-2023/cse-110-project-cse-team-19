package com.example.cse110project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;

import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private LocationService locationService;
    private OrientationService orientationService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView homeIcon = findViewById(R.id.red_icon);
        ImageView familyIcon = findViewById(R.id.blue_icon);
        ImageView friendIcon = findViewById(R.id.purple_icon);

        homeIcon.setVisibility(View.INVISIBLE);
        familyIcon.setVisibility(View.INVISIBLE);
        friendIcon.setVisibility(View.INVISIBLE);

        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        String homeLatLong = preferences.getString("mine", "");
        String friendLatLong = preferences.getString("family", "");
        String familyLatLong = preferences.getString("friend", "");

        System.out.println(homeLatLong);

        if(!homeLatLong.equals("")){
            homeIcon.setVisibility(View.VISIBLE);
        }

        if(!friendLatLong.equals("")){
            friendIcon.setVisibility(View.VISIBLE);
        }

        if(!familyLatLong.equals("")){
            familyIcon.setVisibility(View.VISIBLE);
        }

        if(ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){

            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 200);
        }

        locationService = LocationService.singleton(this);


        TextView textView = (TextView) findViewById(R.id.locationView);

        locationService.getLocation().observe(this, loc->{
            textView.setText(Double.toString(loc.first) + " , " +
                    Double.toString(loc.second));
        });

        ConstraintLayout layout = (ConstraintLayout) findViewById(R.id.compassLayout);

        orientationService = OrientationService.singleton(this);
        TextView orientationView = (TextView) findViewById(R.id.orientation);
        orientationService.getOrientation().observe(this, orientation->{
            orientationView.setText(Float.toString(orientation));
            layout.setRotation((float) Math.toDegrees(-orientation));
        });
    }
}