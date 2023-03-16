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
/*

    public final static String PERSONAL_HOME_COORDINATES = "mine";
    public final static String FAMILY_HOME_COORDINATES = "family";
    public final static String FRIEND_HOME_COORDINATES = "friend";

    public final static String PERSONAL_HOME_LABEL = "myLabel";
    public final static String FAMILY_HOME_LABEL = "familyLabel";
    public final static String FRIEND_HOME_LABEL = "friendLabel";

    public final static String ORIENTATION_LABEL = "orientationLabel";

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

    public static String[] parseCoords(String str){
        String[] list = str.split(", ");
        return list;
    }

    public static boolean isValidOrientation(String str){
        try {
            double orienation = Double.parseDouble(str);
            if ((orienation < 0 || orienation > 360) && !str.equals("")) {
                return false;
            }
            return true;
        }

        catch (Exception e){
            return false;
        }
    }
    public static boolean isValidLatitude(String str){
        try {
            double lat = Double.parseDouble(str);
            if (lat < -90 || lat > 90){
                return false;
            }
            return true;
        }

        catch(Exception e) {
            return false;
        }
    }

    public static boolean isValidLongitude(String str){
        try {
            double longitude = Double.parseDouble(str);
            if (longitude < -180 || longitude > 180){
                return false;
            }
            return true;
        }

        catch(Exception e){
            return false;
        }
    }

//    public static double angleFromCoordinate(double lat1, double long1, double lat2,
//                                       double long2) {
//
//        double dLon = (long2 - long1);
//
//        double y = Math.sin(dLon) * Math.cos(lat2);
//        double x = Math.cos(lat1) * Math.sin(lat2) - Math.sin(lat1)
//                * Math.cos(lat2) * Math.cos(dLon);
//
//        double brng = Math.atan2(y, x);
//
//        brng = Math.toDegrees(brng);
//        brng = (brng + 360) % 360;
//        brng = 360 - brng; // count degrees counter-clockwise - remove to make clockwise
//
//        return brng;
//    }
    
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

    public static double stringToDouble(String str){
        return Double.parseDouble(str);
    }

    static String formatLocation(double latitude, double longitude) {
        return (latitude + ", " + longitude);
    }

 */
//    public static void resetPrefs(){
//        Context context;
//        SharedPreferences preferences;
//        context = ApplicationProvider.getApplicationContext();
//        preferences = context.getSharedPreferences(Utilities.PREFERENCES_NAME, Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = preferences.edit();
//        editor.putString(Utilities.PERSONAL_HOME_COORDINATES, "");
//        editor.putString(Utilities.PERSONAL_HOME_LABEL, "My Home");
//        editor.apply();
//    }
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
        return String.format(Locale.US, "%tT %tZ", time, time);
    }
}