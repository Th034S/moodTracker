package com.thomas.siadous.moodtracker.controller;

import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.Toast;

import com.thomas.siadous.moodtracker.R;

import java.util.ArrayList;

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

        //an ArrayList to store the smiley imageView and background
        ArrayList<Integer> imageList = new ArrayList<>();
        imageList.add(0, R.drawable.smiley_super_happy);
        imageList.add(1, R.color.banana_yellow);
        imageList.add(2, R.drawable.smiley_happy);
        imageList.add(3, R.color.light_sage);
        imageList.add(4, R.drawable.smiley_normal);
        imageList.add(5, R.color.cornflower_blue_65);
        imageList.add(6, R.drawable.smiley_disappointed);
        imageList.add(7, R.color.warm_grey);
        imageList.add(8, R.drawable.smiley_sad);
        imageList.add(9, R.color.faded_red);


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
