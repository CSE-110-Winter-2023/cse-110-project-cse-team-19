package com.example.cse110project;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        preferences = getApplicationContext().getSharedPreferences("my_preferences", MODE_PRIVATE);

        if (!preferences.getString("mine", "").equals("") || !preferences.getString("family", "").equals("")
                || !preferences.getString("friend", "").equals("")){
            Intent intent = new Intent(this, CompassActivity.class);
            startActivity(intent);
        }
        applyCoordinates();

    }

    @Override
    protected void onDestroy() {
        saveCoordinates();
        super.onDestroy();

    }

    public void onNextClicked(View view) {

        EditText myCoords = findViewById(R.id.personalHomeCoords);
        EditText familyCoords = findViewById(R.id.familyHomeCoords);
        EditText friendCoords = findViewById(R.id.friendsHomeCoords);

        String mine = myCoords.getText().toString();
        String family = familyCoords.getText().toString();
        String friend = friendCoords.getText().toString();

        boolean canSwitch = true;

        if (mine.length() == 0 && family.length() == 0 && friend.length() == 0) {
            Utilities.showAlert(this, "Must enter at least one coordinate");
            canSwitch = false;
        }

        String[] mineList = Utilities.parseCoords(mine);
        if (mine.length() == 0) {
            //Does nothing
        } else if (mineList.length != 2 || !Utilities.isValidLatitude(mineList[0]) ||
                !Utilities.isValidLongitude(mineList[1])) {
            canSwitch = false;
            Utilities.showAlert(this, "Personal home coordinates not formatted properly. " +
                    "Must be two numbers separated by a single space.");
        }

        String[] familyList = Utilities.parseCoords(family);
        if (family.length() == 0) {
            //Does nothing
        } else if (familyList.length != 2 || !Utilities.isValidLatitude(familyList[0]) ||
                !Utilities.isValidLongitude(familyList[1])) {
            canSwitch = false;
            Utilities.showAlert(this, "Family home coordinates not formatted properly. " +
                    "Must be two numbers separated by a single space.");
        }

        String[] friendList = Utilities.parseCoords(friend);
        if (friend.length() == 0) {
            //Does nothing
        } else if (friendList.length != 2 || !Utilities.isValidLatitude(friendList[0]) ||
                !Utilities.isValidLongitude(friendList[1])) {
            canSwitch = false;
            Utilities.showAlert(this, "Friend home coordinates not formatted properly. " +
                    "Must be two numbers separated by a single space.");
        }


        if (canSwitch) {
            Intent intent = new Intent(this, LabelActivity.class);
            startActivity(intent);
        }


    }

    public void saveCoordinates() {
        SharedPreferences.Editor editor = preferences.edit();

        String testMyCoords = findViewById(R.id.personalHomeCoords).toString();
        String testFamilyCoords = findViewById(R.id.familyHomeCoords).toString();
        String testFriendCoords = findViewById(R.id.friendsHomeCoords).toString();

        editor.putString("mine", testMyCoords);
        editor.putString("family", testFamilyCoords);
        editor.putString("friend", testFriendCoords);

        editor.apply();
    }

    //Possibly Delete?
    public void applyCoordinates() {

        EditText myCoords = findViewById(R.id.personalHomeCoords);
        EditText familyCoords = findViewById(R.id.familyHomeCoords);
        EditText friendCoords = findViewById(R.id.friendsHomeCoords);

        String mine = preferences.getString("mine", "");
        String family = preferences.getString("family", "");
        String friend = preferences.getString("friend", "");

        myCoords.setText(mine);
        familyCoords.setText(family);
        friendCoords.setText(friend);


    }
}


//package com.example.cse110project;
//
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.constraintlayout.widget.ConstraintLayout;
//import androidx.core.app.ActivityCompat;
//import android.content.SharedPreferences;
//
//import android.content.pm.PackageManager;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//public class MainActivity extends AppCompatActivity {
//    private LocationService locationService;
//    private OrientationService orientationService;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        ImageView homeIcon = findViewById(R.id.red_icon);
//        ImageView familyIcon = findViewById(R.id.blue_icon);
//        ImageView friendIcon = findViewById(R.id.purple_icon);
//        TextView homeLabel = findViewById(R.id.homeLabelDisplay);
//        TextView familyLabel = findViewById(R.id.familyLabelDisplay);
//        TextView friendLabel = findViewById(R.id.friendLabelDisplay);
//
//        homeIcon.setVisibility(View.INVISIBLE);
//        familyIcon.setVisibility(View.INVISIBLE);
//        friendIcon.setVisibility(View.INVISIBLE);
//        homeLabel.setVisibility(View.INVISIBLE);
//        familyLabel.setVisibility(View.INVISIBLE);
//        friendLabel.setVisibility(View.INVISIBLE);
//
//
//        SharedPreferences preferences = getSharedPreferences("my_preferences", MODE_PRIVATE);
//        String homeLatLong = preferences.getString("mine", "");
//        String friendLatLong = preferences.getString("family", "");
//        String familyLatLong = preferences.getString("friend", "");
//
//        System.out.println(homeLatLong);
//
//        if(!homeLatLong.equals("")){
//            homeIcon.setVisibility(View.VISIBLE);
//            homeLabel.setVisibility(View.VISIBLE);
//        }
//
//        if(!friendLatLong.equals("")){
//            friendIcon.setVisibility(View.VISIBLE);
//            friendLabel.setVisibility(View.VISIBLE);
//        }
//
//        if(!familyLatLong.equals("")){
//            familyIcon.setVisibility(View.VISIBLE);
//            familyLabel.setVisibility(View.VISIBLE);
//        }
//
//        if(ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
//                && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
//
//            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 200);
//        }
//
//        locationService = LocationService.singleton(this);
//
//
//        TextView textView = (TextView) findViewById(R.id.locationView);
//        ConstraintLayout layout = (ConstraintLayout) findViewById(R.id.compassLayout);
//
//        locationService.getLocation().observe(this, loc->{
//            textView.setText(Double.toString(loc.first) + " , " +
//                    Double.toString(loc.second));
//            String gilderPort = "32.89075438019187, -117.25108298507078";
//            String ourLocation = "32.88129, -117.23758";
//
//            double gliderPortDegrees = Utilities.findAngle(32.88129, -117.23758, 32.89075438019187, -117.25108298507078);
//            double sanDiegoCountyDegrees = Utilities.findAngle(32.88129, -117.23758, 32.778364, -116.116286);
//
//            ConstraintLayout.LayoutParams redLayoutParams = (ConstraintLayout.LayoutParams) homeIcon.getLayoutParams();
//            ConstraintLayout.LayoutParams blueLayoutParams = (ConstraintLayout.LayoutParams) familyIcon.getLayoutParams();
//
//            redLayoutParams.circleAngle = (float) gliderPortDegrees;
//            blueLayoutParams.circleAngle = (float) sanDiegoCountyDegrees;
//            homeIcon.setLayoutParams(redLayoutParams);
//            familyIcon.setLayoutParams(blueLayoutParams);
//        });
//
//        orientationService = OrientationService.singleton(this);
//        TextView orientationView = (TextView) findViewById(R.id.orientation);
//        orientationService.getOrientation().observe(this, orientation->{
//            orientationView.setText(Float.toString(orientation));
//            layout.setRotation((float) Math.toDegrees(-orientation));
//        });
//    }
//}