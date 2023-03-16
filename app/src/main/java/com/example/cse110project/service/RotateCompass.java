package com.example.cse110project.service;

import android.app.Activity;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.LifecycleOwner;

import com.example.cse110project.R;
import com.example.cse110project.Utilities;
import com.example.cse110project.model.User;

public class RotateCompass {
    public static void rotateCompass(Activity activity, LifecycleOwner lifecycle, ConstraintLayout layout, TextView view){
        com.example.cse110project.OrientationService orientationService = com.example.cse110project.OrientationService.singleton(activity);
        orientationService.getOrientation().observe(lifecycle, orientation -> {
            view.setText(Float.toString(orientation));
            layout.setRotation((float) Math.toDegrees(-orientation));
        });
    }

    public static void rotateCompass(ConstraintLayout layout, TextView view, Float rotate){
        view.setText(Float.toString(rotate));
        layout.setRotation(-rotate);
    }

    /*
    There are just four zones/circles (upper value of range is exclusive):
        0-1 mile
        1-10 miles
        10-500 miles
        500 miles are farther.
     */

//    public static void constrainUser(TextView textView, double ourLat, double ourLong, double theirLat, double theirLong, int zoomLevel){
//        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) textView.getLayoutParams();
//        layoutParams.circleConstraint = R.id.compassLayout;
//        Integer[] zoomRadius = new Integer[4];
//        zoomRadius[0] = 470;
//        zoomRadius[1] = 370;
//        zoomRadius[2] = 270;
//        zoomRadius[3] = 150;
//
//        int circle;
//        double distMi = Utilities.findDistance(ourLat, ourLong, theirLat, theirLong);
//
//        if(distMi < 1){
//            circle = 0;
//        } else if (distMi < 10) {
//            circle = 1;
//        } else if (distMi < 500) {
//            circle = 2;
//        } else {
//            circle = 3;
//        }
//        if(circle > zoomLevel){
//            //Textbox should be blip
//        }
//        int i = zoomLevel - circle;
//        layoutParams.circleRadius = zoomRadius[i];
//        layoutParams.circleAngle = (float) Utilities.findAngle(ourLat, ourLong, theirLat, theirLong);
//        textView.setLayoutParams(layoutParams);
//    }
}
