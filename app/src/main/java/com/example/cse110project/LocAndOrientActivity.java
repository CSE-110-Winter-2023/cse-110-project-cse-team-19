package com.example.cse110project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

public class LocAndOrientActivity extends AppCompatActivity {
    private LocationService locationService;
    private OrientationService orientationService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loc_and_orient);

        if(ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){

            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 200);
        }

        locationService = LocationService.singleton(this);


        TextView textView = (TextView) findViewById(R.id.serviceTextView);

        locationService.getLocation().observe(this, loc->{
            textView.setText(Double.toString(loc.first) + " , " +
                    Double.toString(loc.second));
        });

        orientationService = OrientationService.singleton(this);
        TextView orientationView = (TextView) findViewById(R.id.orientationView);
        orientationService.getOrientation().observe(this, orientation->{
            orientationView.setText(Float.toString(orientation));
        });
    }
}