package com.example.cse110project;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class LabelActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_label);
        applyLabels();
    }

    @Override
    protected void onDestroy(){
        saveLabels();
        super.onDestroy();
    }

    public void onSubmitClicked(View view) {
        Intent intent = new Intent(this, CompassActivity.class);
        startActivity(intent);
        finish();
    }

    public void saveLabels(){
        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        EditText myLabel = findViewById(R.id.personalHomeTextBox);
        EditText friendLabel = findViewById(R.id.friendsHomeTextBox);
        EditText familyLabel = findViewById(R.id.familyHomeTextBox);

        String mine = myLabel.getText().toString();
        String friend = friendLabel.getText().toString();
        String family = familyLabel.getText().toString();

        editor.putString("myLabel", mine);
        editor.putString("friendLabel", friend);
        editor.putString("familyLabel", family);

        editor.apply();

    }

    public void applyLabels() {
        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        EditText myLabel = findViewById(R.id.personalHomeTextBox);
        EditText familyLabel = findViewById(R.id.friendsHomeTextBox);
        EditText friendLabel = findViewById(R.id.familyHomeTextBox);

        String mine = preferences.getString("myLabel", "My Label Default");
        String family = preferences.getString("familyLabel", "Family Label Default");
        String friend = preferences.getString("friendLabel", "Friend Label Default");

        myLabel.setText(mine);
        familyLabel.setText(family);
        friendLabel.setText(friend);
    }


}