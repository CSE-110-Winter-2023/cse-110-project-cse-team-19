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
        super.onDestroy();
    }

    public void onSubmitClicked(View view) {
        saveLabels();
        Intent intent = new Intent(this, CompassActivity.class);
        startActivity(intent);
        finish();
    }

    public void saveLabels(){
        SharedPreferences preferences = getApplicationContext().getSharedPreferences(Utilities.PREFERENCES_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        EditText myLabel = findViewById(R.id.personalHomeTextBox);
        EditText friendLabel = findViewById(R.id.friendsHomeTextBox);
        EditText familyLabel = findViewById(R.id.familyHomeTextBox);

        String mine = myLabel.getText().toString();
        String friend = friendLabel.getText().toString();
        String family = familyLabel.getText().toString();

        editor.putString(Utilities.PERSONAL_HOME_LABEL, mine);
        editor.putString(Utilities.FAMILY_HOME_LABEL, family);
        editor.putString(Utilities.FRIEND_HOME_LABEL, friend);

        editor.apply();

    }

    public void applyLabels() {
        SharedPreferences preferences = getApplicationContext().getSharedPreferences("my_preferences", MODE_PRIVATE);
        EditText myLabel = findViewById(R.id.personalHomeTextBox);
        EditText friendLabel = findViewById(R.id.friendsHomeTextBox);
        EditText familyLabel = findViewById(R.id.familyHomeTextBox);

        String mine = preferences.getString(Utilities.PERSONAL_HOME_LABEL, "My House");
        String family = preferences.getString(Utilities.FAMILY_HOME_LABEL, "Family House");
        String friend = preferences.getString(Utilities.FRIEND_HOME_LABEL, "Friend House");

        myLabel.setText(mine);
        familyLabel.setText(family);
        friendLabel.setText(friend);
    }


}