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
    ImageView imageViewBackground;
    ImageView imageViewSmiley;
    int levelOfMood = 3;

    // an ArrayList to store the smiley imageView and background
    ArrayList<Integer> imageList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // reference the elements of layout
        imageViewBackground = findViewById(R.id.imageView_background);
        imageViewSmiley = findViewById(R.id.imageView_happy);

        gestureDetector = new SwipeGestureDetector(this);

        //add smiley images and background to the ArrayList
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
                levelOfMood--;
                message = "Top to Bottom swipe";
                if (levelOfMood == 4) {
                imageViewSmiley.setImageResource(imageList.get(0));
                imageViewBackground.setImageResource(imageList.get(1));
                }
                else if (levelOfMood == 3) {
                imageViewSmiley.setImageResource(imageList.get(2));
                imageViewBackground.setImageResource(imageList.get(3));
                }

                else if (levelOfMood == 2) {
                    imageViewSmiley.setImageResource(imageList.get(4));
                    imageViewBackground.setImageResource(imageList.get(5));
                }
                else if (levelOfMood == 1) {
                    imageViewSmiley.setImageResource(imageList.get(6));
                    imageViewBackground.setImageResource(imageList.get(7));
                }
                else if (levelOfMood == 0) {
                    imageViewSmiley.setImageResource(imageList.get(8));
                    imageViewBackground.setImageResource(imageList.get(9));
                }
                break;

            case BOTTOM_TO_TOP:
                levelOfMood++;
                message = "Bottom to Top swipe";
                if (levelOfMood == 4) {
                    imageViewSmiley.setImageResource(imageList.get(0));
                    imageViewBackground.setImageResource(imageList.get(1));
                }
                else if (levelOfMood == 3) {
                    imageViewSmiley.setImageResource(imageList.get(2));
                    imageViewBackground.setImageResource(imageList.get(3));
                }

                else if (levelOfMood == 2) {
                    imageViewSmiley.setImageResource(imageList.get(4));
                    imageViewBackground.setImageResource(imageList.get(5));
                }
                else if (levelOfMood == 1) {
                    imageViewSmiley.setImageResource(imageList.get(6));
                    imageViewBackground.setImageResource(imageList.get(7));
                }
                else if (levelOfMood == 0) {
                    imageViewSmiley.setImageResource(imageList.get(8));
                    imageViewBackground.setImageResource(imageList.get(9));
                }
                break;
        }
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
