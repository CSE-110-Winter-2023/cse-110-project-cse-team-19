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

    public static void constrainUser(TextView textView, double ourLat, double ourLong, double theirLat, double theirLong){
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) textView.getLayoutParams();
        layoutParams.circleConstraint = R.id.compass_image;
        double distMi = Utilities.findDistance(ourLat, ourLong, theirLat, theirLong);
        if(distMi < 1){
            layoutParams.circleRadius = 100;
        } else if (distMi < 10) {
            layoutParams.circleRadius = 200;
        } else if (distMi < 500) {
            layoutParams.circleRadius = 250;
        } else {
            layoutParams.circleRadius = 300;
        }
        layoutParams.circleAngle = (float) Utilities.findAngle(ourLat, ourLong, theirLat, theirLong);
        textView.setLayoutParams(layoutParams);
    }
}
