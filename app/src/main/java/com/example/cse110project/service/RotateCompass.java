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

    public static void constrainUser(TextView textView, double ourLat, double ourLong, double theirLat, double theirLong){
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) textView.getLayoutParams();
        layoutParams.circleConstraint = R.id.compassLayout;
        layoutParams.circleRadius = 200;
        layoutParams.circleAngle = (float) Utilities.findAngle(ourLat, ourLong, theirLat, theirLong);
        textView.setLayoutParams(layoutParams);
    }
}
