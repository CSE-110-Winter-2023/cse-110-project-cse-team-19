package com.example.cse110project.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.cse110project.R;
import com.example.cse110project.Utilities;
import com.example.cse110project.model.User;
import com.example.cse110project.model.UserDao;
import com.example.cse110project.model.UserDatabase;


public class EnterNameActivity extends AppCompatActivity {
    private SharedPreferences preferences;
   // public Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_name);
       // context = getApplicationContext();
        preferences = getSharedPreferences(Utilities.PREFERENCES_NAME, MODE_PRIVATE);
    }

    public void enterNameBtnPressed(View view) {
        EditText nameBox = findViewById(R.id.enterNameEditText);
        String name = nameBox.getText().toString();
        if (name.equals("")){
            Utilities.showAlert(this, "Name box can't be left empty");
            return;
        }

        String personalPrivateUID = Utilities.createUID();
        String personalPublicUID = Utilities.createUID();

        SharedPreferences.Editor editor = preferences.edit();

        editor.putString(Utilities.USER_NAME, name);
        editor.putString(Utilities.USER_PRIVATE_UID, personalPrivateUID);
        editor.putString(Utilities.USER_PUBLIC_UID, personalPublicUID);

        editor.apply();

        // Initialize our current user object with their public UID and name as label
        // Still store private UID into preferences tho
        // This behavior can be changed if needed.
        Utilities.personalUser = new User(personalPublicUID, personalPrivateUID, name, 0, 0);


//        UserDatabase db = UserDatabase.provide(context);
//        UserDao dao = db.getDao();

        //dao.upsert(Utilities.personalUser);

        Intent intent = new Intent(this, EnterFriendActivity.class);

        startActivity(intent);
        finish();

    }

}