package com.example.cse110project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;

import android.content.Intent;
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

        SharedPreferences preferences = getSharedPreferences("my_preferences", MODE_PRIVATE);
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
        ConstraintLayout layout = (ConstraintLayout) findViewById(R.id.compassLayout);

        locationService.getLocation().observe(this, loc->{
            textView.setText(Double.toString(loc.first) + " , " +
                    Double.toString(loc.second));
            String gilderPort = "32.89075438019187, -117.25108298507078";
            String ourLocation = "32.88129, -117.23758";

            double gliderPortDegrees = Utilities.findAngle(32.88129, -117.23758, 32.89075438019187, -117.25108298507078);
            double sanDiegoCountyDegrees = Utilities.findAngle(32.88129, -117.23758, 32.778364, -116.116286);

            ConstraintLayout.LayoutParams redLayoutParams = (ConstraintLayout.LayoutParams) homeIcon.getLayoutParams();
            ConstraintLayout.LayoutParams blueLayoutParams = (ConstraintLayout.LayoutParams) familyIcon.getLayoutParams();

            redLayoutParams.circleAngle = (float) gliderPortDegrees;
            blueLayoutParams.circleAngle = (float) sanDiegoCountyDegrees;
            homeIcon.setLayoutParams(redLayoutParams);
            familyIcon.setLayoutParams(blueLayoutParams);
        });

        orientationService = OrientationService.singleton(this);
        TextView orientationView = (TextView) findViewById(R.id.orientation);
        orientationService.getOrientation().observe(this, orientation->{
            orientationView.setText(Float.toString(orientation));
            layout.setRotation((float) Math.toDegrees(-orientation));
        });
    }

    public void onBackClicked(View view) {
        Intent intent = new Intent(this, LabelActivity.class);
        startActivity(intent);
        finish();
    }
}