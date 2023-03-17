package com.example.cse110project;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.View;
import android.widget.ImageView;

import com.example.cse110project.model.User;

import java.util.List;
import java.util.Locale;
import java.util.UUID;

public class Utilities {
    public final static String PREFERENCES_NAME = "my_preferences";
    public static final String LABEL_NAME = "user name";
    public static final String USER_PRIVATE_UID = "user private UID";
    public static final String USER_PUBLIC_UID = "user public UID";
    public static final String USER_LONGITUDE = "user longitude";
    public static final String USER_LATITUDE = "user latitude";
    public static final String CREATED_AT = "created at";
    public static final String UPDATED_AT = "updated at";
    public static final String MOCK_URL = "mock url";

    public static User personalUser;

    public static String createUID() {
        return UUID.randomUUID().toString();
    }

    public static void showAlert(Activity activity, String message){
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(activity);

        alertBuilder
                .setTitle("Alert!")
                .setMessage(message)
                .setPositiveButton("Ok", (dialog, id) -> {
                    dialog.cancel();
                })
                .setCancelable(true);

        AlertDialog alertDialog = alertBuilder.create();
        alertDialog.show();
    }

    public static double findAngle(double ourLat, double ourLong, double theirLat, double theirLong){
        double radians = Math.atan2((theirLong - ourLong), (theirLat - ourLat));
        double degrees = Math.toDegrees(radians);

        // If the longitude is south of us we need to add 180 degrees to place the label
        // on the bottom half of the circle. This is because arcTan is bound by [-90, 90] degrees.
        if(degrees < 0) {
            degrees += 360;
        }

        return degrees;
    }

    public static double findDistance(double ourLat, double ourLong, double theirLat, double theirLong){
        var R = 3963; // Radius of the earth in miles
        var dLat = deg2rad(theirLat-ourLat);  // deg2rad below
        var dLon = deg2rad(theirLong-ourLong);
        var a =
                Math.sin(dLat/2) * Math.sin(dLat/2) +
                        Math.cos(deg2rad(ourLat)) * Math.cos(deg2rad(theirLat)) *
                                Math.sin(dLon/2) * Math.sin(dLon/2)
                ;
        var c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        var d = R * c;
        return d;
    }

    public static double deg2rad(double deg){
        return deg * (Math.PI/180);
    }

    static String formatLocation(double latitude, double longitude) {
        return (latitude + ", " + longitude);
    }

    public static void updateZoom(int zoomLevel, List<ImageView> circleViews){
        if(zoomLevel == 0){
            circleViews.get(0).setVisibility(View.INVISIBLE);
            circleViews.get(1).setVisibility(View.INVISIBLE);
            circleViews.get(2).setVisibility(View.INVISIBLE);
            circleViews.get(3).setVisibility(View.VISIBLE);
        }
        if(zoomLevel == 1){
            circleViews.get(0).setVisibility(View.INVISIBLE);
            circleViews.get(1).setVisibility(View.INVISIBLE);
            circleViews.get(2).setVisibility(View.VISIBLE);
            circleViews.get(3).setVisibility(View.VISIBLE);
        }
        if(zoomLevel == 2){
            circleViews.get(0).setVisibility(View.INVISIBLE);
            circleViews.get(1).setVisibility(View.VISIBLE);
            circleViews.get(2).setVisibility(View.VISIBLE);
            circleViews.get(3).setVisibility(View.VISIBLE);
        }
        if(zoomLevel == 3){
            circleViews.get(0).setVisibility(View.VISIBLE);
            circleViews.get(1).setVisibility(View.VISIBLE);
            circleViews.get(2).setVisibility(View.VISIBLE);
            circleViews.get(3).setVisibility(View.VISIBLE);
        }
    }

    public static String formatTime(Long time) {
        Long minutes = (time / 1000) / 60;
        return String.valueOf(minutes);
    }
}