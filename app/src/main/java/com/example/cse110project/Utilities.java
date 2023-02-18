package com.example.cse110project;

import android.app.Activity;
import android.app.AlertDialog;

import java.util.Locale;

public class Utilities {
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

    public static double angleFromCoordinate(double lat1, double long1, double lat2,
                                       double long2) {

        double dLon = (long2 - long1);

        double y = Math.sin(dLon) * Math.cos(lat2);
        double x = Math.cos(lat1) * Math.sin(lat2) - Math.sin(lat1)
                * Math.cos(lat2) * Math.cos(dLon);

        double brng = Math.atan2(y, x);

        brng = Math.toDegrees(brng);
        brng = (brng + 360) % 360;
        brng = 360 - brng; // count degrees counter-clockwise - remove to make clockwise

        return brng;
    }

    public static double findAngle(double lat1, double long1, double lat2, double long2){
        double radians = Math.atan((long2 - long1) / (lat2 - lat1));
        double degrees = Math.toDegrees(radians);

        // If the longitude is south of us we need to add 180 degrees to place the label
        // on the bottom half of the circle. This is because arcTan is bound by [-90, 90] degrees.

        if (long2 > long1){
            degrees = degrees + 180;
        }
        return degrees;
    }

    public static double stringToDouble(String str){
        return Double.parseDouble(str);
    }

    static String formatLocation(double latitude, double longitude) {
        return String.format(Locale.US, "%.0f° %.0f' %.0f\" N, %.0f° %.0f' %.0f\" W",
                Math.abs(latitude), Math.abs(latitude % 1) * 60, Math.abs(latitude % 1 % 1) * 60,
                Math.abs(longitude), Math.abs(longitude % 1) * 60, Math.abs(longitude % 1 % 1) * 60);
    }
}
