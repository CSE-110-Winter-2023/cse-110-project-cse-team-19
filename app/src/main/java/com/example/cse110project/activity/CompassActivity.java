package com.example.cse110project.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.cse110project.R;
import com.example.cse110project.Utilities;
import com.example.cse110project.model.UserAPI;
import com.example.cse110project.model.UserDatabase;
import com.example.cse110project.service.LocationService;

import org.w3c.dom.Text;

import java.time.Instant;

public class CompassActivity extends AppCompatActivity {
    TextView latLong;
    TextView public_uid;
    UserAPI api = new UserAPI();
    private LocationService locationService;
    SharedPreferences prefs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compass);
        prefs = getSharedPreferences(Utilities.PREFERENCES_NAME, MODE_PRIVATE);
        TextView myText = new TextView(this);
        myText.setText("TextView number: 1");


//        for (int i = 0; i < 3; i++) {
//            TextView myText = new TextView(this);
//            myText.setText("TextView number: " + i);
//            layout.addView(myText);
//        }
        latLong = findViewById(R.id.userUIDTextView);
        latLong.setText("Some new text in the box");
        public_uid = findViewById(R.id.public_uid);
        public_uid.setText(Utilities.personalUser.public_code);

//        var context = getApplicationContext();
//        var db = UserDatabase.provide(context);
//        var dao = db.getDao();

        locationService = LocationService.singleton(this);

        if (Utilities.personalUser.private_code == null) {
            throw new IllegalStateException("personal UID can't be empty by the time we get to the Compass");
        }

        this.reobserveLocation();

//        var executor = Executors.newSingleThreadExecutor();
//        var future = executor.submit(this::reobserveLocation);
//        try {
//            var getFuture = future.get(1, TimeUnit.SECONDS);
//        } catch (ExecutionException e) {
//            throw new RuntimeException(e);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        } catch (TimeoutException e) {
//            throw new RuntimeException(e);
//        }

//        personalUIDTextView = findViewById(R.id.userUIDTextView);
//        SharedPreferences preferences = getSharedPreferences(Utilities.PREFERENCES_NAME, MODE_PRIVATE);
//        String personalPublicUID = preferences.getString(Utilities.USER_PUBLIC_UID, "");

//        personalUIDTextView.setText(personalPublicUID);

        // These were used to check that UIDs that I entered on the EnterFriendsActivity were actually
        // put into the Room database correctly and that I would be able to get them back out.
        // They worked correctly for me, if they break when you try to uncomment them make sure you add
        // 'My current location' on EnterFriendsActivity first

//        assert(dao.exists("My current location") == true);
//        dao.get("My current location").observe(this, user -> {
//            personalUIDTextView.setText(user.toJSON());
//        });
    }

    public void enterFriendsBtnPressed(View view) {
        finish();
    }

    public void reobserveLocation() {
        var locationData = locationService.getLocation();
        locationData.observe(this, this::onLocationChanged);
    }

    public void onLocationChanged(Pair<Double, Double> latLong) {
        double newLat = latLong.first;
        double newLong = latLong.second;
        String updatedTime = Instant.now().toString();

        Utilities.personalUser.latitude = (float) newLat;
        Utilities.personalUser.longitude = (float) newLong;
        Utilities.personalUser.updated_at = updatedTime;

        SharedPreferences.Editor editor = prefs.edit();
        editor.putFloat(Utilities.USER_LATITUDE, (float) newLat);
        editor.putFloat(Utilities.USER_LONGITUDE, (float) newLong);
        editor.putString(Utilities.UPDATED_AT, updatedTime);

        editor.apply();


        api.putUserAsync(Utilities.personalUser);
        this.latLong.setText("Latitude: " + newLat + ", longitude: "+ newLong);

//        var context = getApplicationContext();
//        var db = UserDatabase.provide(context);
//        var dao = db.getDao();
//
//        dao.upsert(Utilities.personalUser);


        // Loop through all of the friend UIDs we have and recompute the formula for getting their angles on the compass
    }
}