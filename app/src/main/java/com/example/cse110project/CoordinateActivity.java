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

public class CoordinateActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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

        if (mine.length() == 0 && family.length() == 0 && friend.length() == 0){
            Utilities.showAlert(this, "Must enter at least one coordinate");
            canSwitch = false;
        }

        String[] mineList = Utilities.parseCoords(mine);
        if (mine.length() == 0){
            //Does nothing
        }

        else if (mineList.length != 2 || !Utilities.isValidLatitude(mineList[0]) ||
                !Utilities.isValidLongitude(mineList[1])) {
            canSwitch = false;
            Utilities.showAlert(this, "Personal home coordinates not formatted properly. " +
                    "Must be two numbers separated by a single space.");
        }

        String[] familyList = Utilities.parseCoords(family);
        if (family.length() == 0){
            //Does nothing
        }

        else if (familyList.length != 2 || !Utilities.isValidLatitude(familyList[0]) ||
                !Utilities.isValidLongitude(familyList[1])){
            canSwitch = false;
            Utilities.showAlert(this, "Family home coordinates not formatted properly. " +
                    "Must be two numbers separated by a single space.");
        }

        String[] friendList = Utilities.parseCoords(friend);
        if (friend.length() == 0){
            //Does nothing
        }
        else if (friendList.length != 2 || !Utilities.isValidLatitude(friendList[0]) ||
                !Utilities.isValidLongitude(friendList[1])){
            canSwitch = false;
            Utilities.showAlert(this, "Friend home coordinates not formatted properly. " +
                    "Must be two numbers separated by a single space.");
        }


        if (canSwitch) {
            Intent intent = new Intent(this, LabelActivity.class);
            startActivity(intent);
            finish();
        }
    }

    public void saveCoordinates() {
        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        EditText myCoords = findViewById(R.id.personalHomeCoords);
        EditText familyCoords = findViewById(R.id.familyHomeCoords);
        EditText friendCoords = findViewById(R.id.friendsHomeCoords);

        String mine = myCoords.getText().toString();
        String family = familyCoords.getText().toString();
        String friend = friendCoords.getText().toString();

        editor.putString("mine", mine);
        editor.putString("family", family);
        editor.putString("friend", friend);

        editor.apply();
    }

    public void applyCoordinates() {
        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
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