package com.example.cse110project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.EditText;

public class EnterNameActivity extends AppCompatActivity {

    private SharedPreferences preferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_name);
        preferences = getSharedPreferences("my_preferences", MODE_PRIVATE);
    }

    public void enterNameBtnPressed(View view) {
        EditText nameBox = findViewById(R.id.enterNameEditText);
        String name = nameBox.getText().toString();
        if (name.equals("")){
            Utilities.showAlert(this, "Name box can't be left empty");
        }
        String personalUID = Utilities.createUID();

        SharedPreferences.Editor editor = preferences.edit();

        editor.putString(Utilities.USER_NAME, name);
        editor.putString(Utilities.USER_UID, personalUID);

        editor.apply();

        finish();
    }
}