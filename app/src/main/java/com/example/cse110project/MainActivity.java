package com.example.cse110project;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
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


        applyCoordinates();

        if (!preferences.getString("mine", "").equals("") || !preferences.getString("family", "").equals("")
                || !preferences.getString("friend", "").equals("")){
            Intent intent = new Intent(this, CompassActivity.class);
            startActivity(intent);
        }

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
            saveCoordinates();
            Intent intent = new Intent(this, LabelActivity.class);
            startActivity(intent);
        }


    }

    public void saveCoordinates() {
        SharedPreferences.Editor editor = preferences.edit();

        EditText testMyCoords = findViewById(R.id.personalHomeCoords);
        EditText testFamilyCoords = findViewById(R.id.familyHomeCoords);
        EditText testFriendCoords = findViewById(R.id.friendsHomeCoords);
        EditText orientationLabel = findViewById(R.id.mockTextBox);

        String myCoords = testMyCoords.getText().toString();
        String familyCoords = testFamilyCoords.getText().toString();
        String friendCoords = testFriendCoords.getText().toString();
        String mock_orientation = orientationLabel.getText().toString();

        editor.putString("mine", myCoords);
        editor.putString("family", familyCoords);
        editor.putString("friend", friendCoords);
        editor.putString("orientationLabel", mock_orientation);

        editor.apply();
    }

    //Possibly Delete?
    public void applyCoordinates() {

        EditText myCoords = findViewById(R.id.personalHomeCoords);
        EditText familyCoords = findViewById(R.id.familyHomeCoords);
        EditText friendCoords = findViewById(R.id.friendsHomeCoords);
        EditText orientationLabel = findViewById(R.id.mockTextBox);

        String mine = preferences.getString("mine", "");
        String family = preferences.getString("family", "");
        String friend = preferences.getString("friend", "");
        String mock_orientation = preferences.getString("orientationLabel", "");
        System.out.println(mine);

        myCoords.setText(mine);
        familyCoords.setText(family);
        friendCoords.setText(friend);
        orientationLabel.setText(mock_orientation);


    }
}