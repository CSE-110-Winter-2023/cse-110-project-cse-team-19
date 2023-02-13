package com.example.cse110project;

import android.app.Activity;
import android.app.AlertDialog;

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
        String[] list = str.split(" ");
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
}
