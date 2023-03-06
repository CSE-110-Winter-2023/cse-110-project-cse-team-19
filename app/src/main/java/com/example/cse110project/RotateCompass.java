/*
package com.example.cse110project;

import android.app.Activity;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.LifecycleOwner;

public class RotateCompass {
    public static void rotateCompass(Activity activity, LifecycleOwner lifecycle, ConstraintLayout layout, TextView view){
        OrientationService orientationService = OrientationService.singleton(activity);
        orientationService.getOrientation().observe(lifecycle, orientation -> {
            view.setText(Float.toString(orientation));
            layout.setRotation((float) Math.toDegrees(-orientation));
        });
    }

    public static void rotateCompass(ConstraintLayout layout, TextView view, Float rotate){
        view.setText(Float.toString(rotate));
        layout.setRotation(-rotate);
    }
}
*/
