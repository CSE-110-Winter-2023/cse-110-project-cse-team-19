package com.example.cse110project;

import static org.junit.Assert.assertEquals;

import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.test.core.app.ActivityScenario;

import com.example.cse110project.activity.CompassActivity;
import com.example.cse110project.model.User;
import com.example.cse110project.service.ConstrainUserService;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.util.Enumeration;
import java.util.Hashtable;

@RunWith(RobolectricTestRunner.class)
public class US6Tests {

    @Test
    public void testZoomLevel1() {
        Utilities.personalUser = new User("test","test","test",0,0);
        try (ActivityScenario<CompassActivity> scenario = ActivityScenario.launch(CompassActivity.class)){
            scenario.onActivity(activity -> {
                ConstraintLayout layout = activity.findViewById(R.id.compassLayout);
                Hashtable<String, ConstrainUserService> table = activity.getTextViews();
                Enumeration<String> e = table.keys();

                while (e.hasMoreElements()) {
                    String key = e.nextElement();
                    ConstrainUserService constraintView = table.get(key);
                    TextView textView = constraintView.textView;
                    constraintView.constrainUser(32, 100, 32, -100, 1);
                    var distance = (float) Utilities.findDistance(32, 100, 32, -100);
                    ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) textView.getLayoutParams();
                    float correctDisplay;
                    if(distance < 1){
                        correctDisplay = 470;
                    } else if (distance < 10) {
                        correctDisplay = 520;
                    } else if (distance < 500) {
                        correctDisplay = 520;
                    } else {
                        correctDisplay = 520;
                    }
                    assertEquals(layoutParams.circleRadius, correctDisplay, 0);
                }
            });
        }
    }

    @Test
    public void testZoomLevel2() {
        Utilities.personalUser = new User("test","test","test",0,0);
        try (ActivityScenario<CompassActivity> scenario = ActivityScenario.launch(CompassActivity.class)){
            scenario.onActivity(activity -> {
                ConstraintLayout layout = activity.findViewById(R.id.compassLayout);
                Hashtable<String, ConstrainUserService> table = activity.getTextViews();
                Enumeration<String> e = table.keys();

                while (e.hasMoreElements()) {
                    String key = e.nextElement();
                    ConstrainUserService constraintView = table.get(key);
                    TextView textView = constraintView.textView;
                    constraintView.constrainUser(32, 100, 32, -100, 2);
                    var distance = (float) Utilities.findDistance(32, 100, 32, -100);
                    ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) textView.getLayoutParams();
                    float correctDisplay;
                    if(distance < 1){
                        correctDisplay = 370;
                    } else if (distance < 10) {
                        correctDisplay = 470;
                    } else if (distance < 500) {
                        correctDisplay = 520;
                    } else {
                        correctDisplay = 520;
                    }
                    assertEquals(layoutParams.circleRadius, correctDisplay, 0);
                }
            });
        }
    }

    @Test
    public void testZoomLevel3() {
        Utilities.personalUser = new User("test","test","test",0,0);
        try (ActivityScenario<CompassActivity> scenario = ActivityScenario.launch(CompassActivity.class)){
            scenario.onActivity(activity -> {
                ConstraintLayout layout = activity.findViewById(R.id.compassLayout);
                Hashtable<String, ConstrainUserService> table = activity.getTextViews();
                Enumeration<String> e = table.keys();

                while (e.hasMoreElements()) {
                    String key = e.nextElement();
                    ConstrainUserService constraintView = table.get(key);
                    TextView textView = constraintView.textView;
                    constraintView.constrainUser(32, 100, 32, -100, 3);
                    var distance = (float) Utilities.findDistance(32, 100, 32, -100);
                    ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) textView.getLayoutParams();
                    float correctDisplay;
                    if(distance < 1){
                        correctDisplay = 270;
                    } else if (distance < 10) {
                        correctDisplay = 370;
                    } else if (distance < 500) {
                        correctDisplay = 470;
                    } else {
                        correctDisplay = 520;
                    }
                    assertEquals(layoutParams.circleRadius, correctDisplay, 0);
                }
            });
        }
    }

    @Test
    public void testZoomLevel4() {
        Utilities.personalUser = new User("test","test","test",0,0);
        try (ActivityScenario<CompassActivity> scenario = ActivityScenario.launch(CompassActivity.class)){
            scenario.onActivity(activity -> {
                ConstraintLayout layout = activity.findViewById(R.id.compassLayout);
                Hashtable<String, ConstrainUserService> table = activity.getTextViews();
                Enumeration<String> e = table.keys();

                while (e.hasMoreElements()) {
                    String key = e.nextElement();
                    ConstrainUserService constraintView = table.get(key);
                    TextView textView = constraintView.textView;
                    constraintView.constrainUser(32, 100, 32, -100, 4);
                    var distance = (float) Utilities.findDistance(32, 100, 32, -100);
                    ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) textView.getLayoutParams();
                    float correctDisplay;
                    if(distance < 1){
                        correctDisplay = 150;
                    } else if (distance < 10) {
                        correctDisplay = 270;
                    } else if (distance < 500) {
                        correctDisplay = 370;
                    } else {
                        correctDisplay = 470;
                    }
                    assertEquals(layoutParams.circleRadius, correctDisplay, 0);
                }
            });
        }
    }
}
