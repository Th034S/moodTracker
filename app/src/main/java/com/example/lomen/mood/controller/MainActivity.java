package com.example.lomen.mood.controller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.Toast;

import com.example.lomen.mood.R;

// MainActivity class, first activity launched
public class MainActivity extends AppCompatActivity {

    private SwipeGestureDetector gestureDectector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gestureDectector = new SwipeGestureDetector(this);
    }

    @Override
    public boolean dispatchTouchEvent (MotionEvent event) {
        return gestureDectector.onTouchEvent(event);
    }

    public void onSwipe(SwipeGestureDetector.SwipeDirection direction) {
        String message = "";
        switch (direction) {
            case TOP_TO_BOTTOM:
                message = "Top to Bottom swipe";
                break;
            case BOTTOM_TO_TOP:
                message = "Bottom to Top swipe";
                break;
        }
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
