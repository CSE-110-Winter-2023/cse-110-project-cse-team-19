package com.example.cse110project.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.cse110project.R;
import com.example.cse110project.Utilities;

public class EnterNameActivity extends AppCompatActivity {

    private SharedPreferences preferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_name);
        preferences = getSharedPreferences(Utilities.PREFERENCES_NAME, MODE_PRIVATE);
    }

    public void enterNameBtnPressed(View view) {
        EditText nameBox = findViewById(R.id.enterNameEditText);
        String name = nameBox.getText().toString();
        if (name.equals("")){
            Utilities.showAlert(this, "Name box can't be left empty");
        }

        String personalPrivateUID = Utilities.createUID();
        String personalPublicUID = Utilities.createUID();

        SharedPreferences.Editor editor = preferences.edit();

        editor.putString(Utilities.USER_NAME, name);
        editor.putString(Utilities.USER_UID, personalPrivateUID);
        editor.putString(Utilities.USER_PUBLIC_UID, personalPublicUID);

        editor.apply();
        Intent intent = new Intent(this, CompassActivity.class);
        startActivity(intent);
        finish();
    }
}