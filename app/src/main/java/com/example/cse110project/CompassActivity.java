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
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class CompassActivity extends AppCompatActivity {
    private LocationService locationService;
    //private OrientationService orientationService;
    private final double OUR_LAT = 32.88129;
    private final double OUR_LONG = -117.23758;

    private ExecutorService backgroundThreadExecutor = Executors.newSingleThreadExecutor();
    private Future<Void> future;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compass);

        ImageView familyIcon = findViewById(R.id.blue_icon);
        ImageView friendIcon = findViewById(R.id.purple_icon);
        TextView homeLabel = findViewById(R.id.homeLabelDisplay);
        TextView familyLabel = findViewById(R.id.familyLabelDisplay);
        TextView friendLabel = findViewById(R.id.friendLabelDisplay);

        ImageView homeIcon = findViewById(R.id.red_icon);
        homeIcon.setVisibility(View.INVISIBLE);
        familyIcon.setVisibility(View.INVISIBLE);
        friendIcon.setVisibility(View.INVISIBLE);
        homeLabel.setVisibility(View.INVISIBLE);
        familyLabel.setVisibility(View.INVISIBLE);
        friendLabel.setVisibility(View.INVISIBLE);


        SharedPreferences preferences = getSharedPreferences("my_preferences", MODE_PRIVATE);
        String homeLatLong = preferences.getString("mine", "");
        String friendLatLong = preferences.getString("family", "");
        String familyLatLong = preferences.getString("friend", "");
        String mockOrientation = preferences.getString("orientationLabel", "");

        System.out.println(homeLatLong);

        if(!homeLatLong.equals("")){
            homeIcon.setVisibility(View.VISIBLE);
            homeLabel.setVisibility(View.VISIBLE);
            // I think we can set the intial angles here, but the parseDouble
            // method I use in utilities is causing a java.lang.NumberFormatException
//            String[] coordinates = Utilities.parseCoords(homeLatLong);
//            double latitude = Utilities.stringToDouble(coordinates[0]);
//            double longitude = Utilities.stringToDouble(coordinates[1]);
            // have our coordinates ready by here, for now just using
            // hard coded values
//            double angle = Utilities.findAngle(OUR_LAT, OUR_LONG, latitude, longitude);
//            ConstraintLayout.LayoutParams redLayoutParams = (ConstraintLayout.LayoutParams) homeIcon.getLayoutParams();
//            redLayoutParams.circleAngle = (float) angle;
//            homeIcon.setLayoutParams(redLayoutParams);
        }

        // Else makes labels away if they decide to get rid of
        // coordinates for any reason, doesn't work rn tho
        else {
            homeIcon.setVisibility(View.INVISIBLE);
            homeLabel.setVisibility(View.INVISIBLE);
        }

        if(!friendLatLong.equals("")){
            friendIcon.setVisibility(View.VISIBLE);
            friendLabel.setVisibility(View.VISIBLE);
        }

        else {
            friendIcon.setVisibility(View.INVISIBLE);
            friendLabel.setVisibility(View.INVISIBLE);
        }

        if(!familyLatLong.equals("")){
            familyIcon.setVisibility(View.VISIBLE);
            familyLabel.setVisibility(View.VISIBLE);
        }

        else {
            familyIcon.setVisibility(View.INVISIBLE);
            familyLabel.setVisibility(View.INVISIBLE);
        }

        locationService = LocationService.singleton(this);
        this.reobserveLocation();


        TextView textView = (TextView) findViewById(R.id.locationText);
        ConstraintLayout layout = (ConstraintLayout) findViewById(R.id.compassLayout);
        TextView orientationView = (TextView) findViewById(R.id.orientation);

        if (!mockOrientation.equals("")){
            float mockOri = Float.parseFloat(mockOrientation);
            //orientation = mockOri;
            Log.d("mockOrientation","Henlo1");
            RotateCompass.rotateCompass(this, this, layout, mockOri);

        }
        else{
            Log.d("mockOrientation","Henlo");
            RotateCompass.rotateCompass(this, this, layout, orientationView);
        }



        /*
        orientationService = OrientationService.singleton(this);
        TextView orientationView = (TextView) findViewById(R.id.orientation);
<<<<<<< HEAD
        if (!mockOrientation.equals("")){
            float mockOri = Float.parseFloat(mockOrientation);
            MutableLiveData<Float> mock_orientation = new MutableLiveData<>(mockOri);
            orientationService.setMockOrientationSource(mock_orientation);
        }
        orientationService.getOrientation().observe(this, orientation->{
=======
        orientationService.getOrientation().observe(this, orientation -> {
>>>>>>> e314b3371493872b9b742b72aa1755f654286b72
            orientationView.setText(Float.toString(orientation));


            if (!mockOrientation.equals("")){
                float mockOri = Float.parseFloat(mockOrientation);
                orientation = mockOri;
                Log.d("mockOrientation","Henlo1");

            }
            else{
                Log.d("mockOrientation","Henlo");
            }
            layout.setRotation((float) Math.toDegrees(-orientation));
            //layout.setRotation((float) Math.toDegrees(-60));
        });

         */
    /*
        this.future = backgroundThreadExecutor.submit(() -> {

            Looper.prepare();
            Handler mHandler = new Handler(Looper.myLooper());


            Looper.loop();
            return null;
        });
     */
    }

    public void backToCoordinates(View view) {
        finish();
        //this.future.cancel(true);
    }

    /**
     * Taken from Lab Demo 5
     * Basically rechecks for current location
     */
    private void reobserveLocation() {
        var locationData = locationService.getLocation();
        locationData.observe(this, this::onLocationChanged);
    }

    private void onLocationChanged(android.util.Pair<Double, Double> latLong) {
        SharedPreferences preferences = getSharedPreferences("my_preferences", MODE_PRIVATE);
        String homeLatLong = preferences.getString("mine", "");
        String friendLatLong = preferences.getString("family", "");
        String familyLatLong = preferences.getString("friend", "");

        ImageView homeIcon = findViewById(R.id.red_icon);

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
//        String gilderPort = "32.89075438019187, -117.25108298507078";
//        double parentHouseDegrees = Utilities.findAngle(latLong.first,latLong.second, 38.557209840254735, -121.38793501534316);


//        double gliderPortDegrees = Utilities.findAngle(32.88129, -117.23758, 32.89075438019187, -117.25108298507078);
//            double sanDiegoCountyDegrees = Utilities.findAngle(32.88129, -117.23758, 32.778364, -116.116286);

//        ConstraintLayout.LayoutParams redLayoutParams = (ConstraintLayout.LayoutParams) homeIcon.getLayoutParams();
//            ConstraintLayout.LayoutParams blueLayoutParams = (ConstraintLayout.LayoutParams) familyIcon.getLayoutParams();
//
//        redLayoutParams.circleAngle = (float) parentHouseDegrees;
//            blueLayoutParams.circleAngle = (float) sanDiegoCountyDegrees;
//        homeIcon.setLayoutParams(redLayoutParams);
//            familyIcon.setLayoutParams(blueLayoutParams);
    }
}