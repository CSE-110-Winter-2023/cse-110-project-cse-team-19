package com.example.cse110project.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.cse110project.R;
import com.example.cse110project.Utilities;

public class mockUrlActivity extends AppCompatActivity {
    SharedPreferences prefs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mock_url);
        prefs = getSharedPreferences(Utilities.PREFERENCES_NAME, MODE_PRIVATE);
    }

    public void doneBtnPressed(View view) {
        EditText urlTextBox = findViewById(R.id.mockUrlEditText);
        String url = urlTextBox.getText().toString();
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(Utilities.MOCK_URL, url);
        editor.apply();
        finish();
    }
}