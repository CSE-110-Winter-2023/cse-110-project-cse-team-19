package com.example.cse110project.service;

import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.cse110project.R;
import com.example.cse110project.Utilities;

public class ConstrainUserService {

    private double latitude;
    private double longitude;
    public TextView textView;

    public ConstrainUserService(double latitude, double longitude, TextView textView) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.textView = textView;
    }

    public void constrainUser(double ourLat, double ourLong, double theirLat, double theirLong, int zoomLevel){
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) textView.getLayoutParams();
        latitude = theirLat;
        longitude = theirLong;
        layoutParams.circleConstraint = R.id.compassLayout;
        Integer[] zoomRadius = new Integer[4];
        zoomRadius[0] = 470;
        zoomRadius[1] = 370;
        zoomRadius[2] = 270;
        zoomRadius[3] = 150;

        int circle;
        double distMi = Utilities.findDistance(ourLat, ourLong, theirLat, theirLong);

        if(distMi < 1){
            circle = 0;
        } else if (distMi < 10) {
            circle = 1;
        } else if (distMi < 500) {
            circle = 2;
        } else {
            circle = 3;
        }
        if(circle > zoomLevel){
            layoutParams.circleRadius = 500;
        }
        else{
            int i = zoomLevel - circle;
            layoutParams.circleRadius = zoomRadius[i];
        }
        layoutParams.circleAngle = (float) Utilities.findAngle(ourLat, ourLong, theirLat, theirLong);
        textView.setLayoutParams(layoutParams);
    }
    public void constrainUser(double ourLat, double ourLong, int zoomLevel){
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) textView.getLayoutParams();
        layoutParams.circleConstraint = R.id.compassLayout;
        Integer[] zoomRadius = new Integer[4];
        zoomRadius[0] = 470;
        zoomRadius[1] = 370;
        zoomRadius[2] = 270;
        zoomRadius[3] = 150;

        int circle;
        double distMi = Utilities.findDistance(ourLat, ourLong, this.latitude, this.longitude);

        if(distMi < 1){
            circle = 0;
        } else if (distMi < 10) {
            circle = 1;
        } else if (distMi < 500) {
            circle = 2;
        } else {
            circle = 3;
        }
        //TODO Change
        if(circle > zoomLevel){
            layoutParams.circleRadius = 600;
        }
        else{
            int i = zoomLevel - circle;
            layoutParams.circleRadius = zoomRadius[i];
        }
        layoutParams.circleAngle = (float) Utilities.findAngle(ourLat, ourLong, this.latitude, this.longitude);
        textView.setLayoutParams(layoutParams);
    }

}
