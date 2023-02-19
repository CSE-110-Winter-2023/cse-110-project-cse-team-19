package com.example.cse110project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.util.Pair;

import android.content.SharedPreferences;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class CompassActivity extends AppCompatActivity {
    private LocationService locationService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compass);

        setVisibilities();

        locationService = LocationService.singleton(this);
        this.reobserveLocation();

        ConstraintLayout layout = (ConstraintLayout) findViewById(R.id.compassLayout);
        TextView orientationView = (TextView) findViewById(R.id.orientation);
        RotateCompass.rotateCompass(this, this, layout, orientationView);
    }

    public void backToCoordinates(View view) {
        finish();
    }

    /**
     * Taken from Lab Demo 5
     * Basically rechecks for current location
     */
    public void reobserveLocation() {
        var locationData = locationService.getLocation();
        locationData.observe(this, this::onLocationChanged);
    }

    private void onLocationChanged(android.util.Pair<Double, Double> latLong) {
        SharedPreferences preferences = getSharedPreferences(Utilities.PREFERENCES_NAME, MODE_PRIVATE);
        String homeLatLong = preferences.getString(Utilities.PERSONAL_HOME_COORDINATES, "");
        String familyLatLong = preferences.getString(Utilities.FAMILY_HOME_COORDINATES, "");
        String friendLatLong = preferences.getString(Utilities.FRIEND_HOME_COORDINATES, "");

        ImageView homeIcon = findViewById(R.id.red_icon);
        ImageView familyIcon = findViewById(R.id.blue_icon);
        ImageView friendIcon = findViewById(R.id.purple_icon);

        TextView locationText = findViewById(R.id.locationText);
        locationText.setText(Utilities.formatLocation(latLong.first, latLong.second));

        if (!homeLatLong.equals("")){
            String[] coordinates = Utilities.parseCoords(homeLatLong);
            double latitude = Utilities.stringToDouble(coordinates[0]);
            double longitude = Utilities.stringToDouble(coordinates[1]);
            double ourHomeAngle = Utilities.findAngle(latLong.first, latLong.second, latitude, longitude);

            ConstraintLayout.LayoutParams redLayoutParams = (ConstraintLayout.LayoutParams) homeIcon.getLayoutParams();
            redLayoutParams.circleAngle = (float) ourHomeAngle;
            homeIcon.setLayoutParams(redLayoutParams);
        }

        if (!familyLatLong.equals("")){
            String[] coordinates = Utilities.parseCoords(familyLatLong);
            double latitude = Utilities.stringToDouble(coordinates[0]);
            double longitude = Utilities.stringToDouble(coordinates[1]);
            double familyHomeAngle = Utilities.findAngle(latLong.first, latLong.second, latitude, longitude);

            ConstraintLayout.LayoutParams blueLayoutParams = (ConstraintLayout.LayoutParams) familyIcon.getLayoutParams();
            blueLayoutParams.circleAngle = (float) familyHomeAngle;
            familyIcon.setLayoutParams(blueLayoutParams);
        }

        if (!friendLatLong.equals("")){
            String[] coordinates = Utilities.parseCoords(friendLatLong);
            double latitude = Utilities.stringToDouble(coordinates[0]);
            double longitude = Utilities.stringToDouble(coordinates[1]);
            double friendHomeAngle = Utilities.findAngle(latLong.first, latLong.second, latitude, longitude);

            ConstraintLayout.LayoutParams purpleLayoutParams = (ConstraintLayout.LayoutParams) friendIcon.getLayoutParams();
            purpleLayoutParams.circleAngle = (float) friendHomeAngle;
            friendIcon.setLayoutParams(purpleLayoutParams);
        }
    }

    public void setVisibilities(){
        ImageView homeIcon = findViewById(R.id.red_icon);
        ImageView familyIcon = findViewById(R.id.blue_icon);
        ImageView friendIcon = findViewById(R.id.purple_icon);

        TextView homeLabel = findViewById(R.id.homeLabelDisplay);
        TextView familyLabel = findViewById(R.id.familyLabelDisplay);
        TextView friendLabel = findViewById(R.id.friendLabelDisplay);


        homeIcon.setVisibility(View.INVISIBLE);
        familyIcon.setVisibility(View.INVISIBLE);
        friendIcon.setVisibility(View.INVISIBLE);

        homeLabel.setVisibility(View.INVISIBLE);
        familyLabel.setVisibility(View.INVISIBLE);
        friendLabel.setVisibility(View.INVISIBLE);


        SharedPreferences preferences = getSharedPreferences(Utilities.PREFERENCES_NAME, MODE_PRIVATE);
        String homeLatLong = preferences.getString(Utilities.PERSONAL_HOME_COORDINATES, "");
        String familyLatLong = preferences.getString(Utilities.FAMILY_HOME_COORDINATES, "");
        String friendLatLong = preferences.getString(Utilities.FRIEND_HOME_COORDINATES, "");

        homeLabel.setText(preferences.getString(Utilities.PERSONAL_HOME_LABEL, "My Home"));
        familyLabel.setText(preferences.getString(Utilities.FAMILY_HOME_LABEL, "Family House"));
        friendLabel.setText(preferences.getString(Utilities.FRIEND_HOME_LABEL, "Friend's House"));


        if(!homeLatLong.equals("")){
            homeIcon.setVisibility(View.VISIBLE);
            homeLabel.setVisibility(View.VISIBLE);
        }

        else {
            homeIcon.setVisibility(View.INVISIBLE);
            homeLabel.setVisibility(View.INVISIBLE);
        }

        if(!familyLatLong.equals("")){
            familyIcon.setVisibility(View.VISIBLE);
            familyLabel.setVisibility(View.VISIBLE);
        }

        else {
            familyIcon.setVisibility(View.INVISIBLE);
            familyLabel.setVisibility(View.INVISIBLE);
        }

        if(!friendLatLong.equals("")){
            friendIcon.setVisibility(View.VISIBLE);
            friendLabel.setVisibility(View.VISIBLE);
        }

        else {
            friendIcon.setVisibility(View.INVISIBLE);
            friendLabel.setVisibility(View.INVISIBLE);
        }
    }
}