package com.thomas.siadous.moodtracker.controller;

import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.Toast;

import com.thomas.siadous.moodtracker.R;

// MainActivity class, first activity launched
public class MainActivity extends AppCompatActivity {

    private SwipeGestureDetector gestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView imageViewBackground = findViewById(R.id.imageView_background);
        ImageView imageViewSmiley = findViewById(R.id.imageView_happy);

        gestureDetector = new SwipeGestureDetector(this);


    }

    @Override
    public boolean dispatchTouchEvent (MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
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
