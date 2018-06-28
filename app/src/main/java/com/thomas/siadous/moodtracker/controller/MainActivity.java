package com.thomas.siadous.moodtracker.controller;

// IMPORTS

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.thomas.siadous.moodtracker.R;
import java.util.ArrayList;


// MainActivity class, first activity launched, permit to access to the different moods with the swipe
public class MainActivity extends AppCompatActivity implements GestureDetector.OnGestureListener {

    // THE DIFFERENT VARIABLES

    private ImageView imageViewBackground; // View for the background
    private ImageView imageViewSmiley; // View for the smiley (mood)
    private ImageButton imageButtonHistory; // Button to access to the mood history
    private ImageButton imageButtonComments; // Button to add comments

    private SharedPreferences mPreferences; // Use to store data
    public final static String PREFERENCE_FILE = "PREFERENCE_FILE";
    public final static String PREF_KEY_COMMENT = "PREF_KEY_COMMENT";
    int levelOfMood = 3; // On what mood we are positioned / ex : 4 = :D / 0 = :(
    private static final String DEBUG_TAG = "Gestures"; // FOR TEST : LOG

    private GestureDetectorCompat mDetector;

    // an ArrayList to store the smiley imageView and background
    ArrayList<Integer> imageList = new ArrayList<>();


    // THE METHOD onCreate

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // reference the elements of layout
        imageViewBackground = findViewById(R.id.imageView_background);
        imageViewSmiley = findViewById(R.id.imageView_happy);
        imageButtonHistory = findViewById(R.id.imageButton_history);
        imageButtonComments = findViewById(R.id.imageButton_comments);

        final Context context = this;
        mDetector = new GestureDetectorCompat(this, this); // Initiate the gesture detector
        mPreferences = getSharedPreferences(PREFERENCE_FILE, MODE_PRIVATE);

        //add smiley images and background in an ArrayList
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

        Log.d(DEBUG_TAG, "Log works"); // FOR TEST

        // Launch a new activity (HistoryActivity) when click on imageButtonHistory
        imageButtonHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent historyActivityIntent = new Intent(MainActivity.this, HistoryActivity.class);
                startActivity(historyActivityIntent);
                Log.d(DEBUG_TAG, "Clicked");
            }
        });
        // Box dialog appears when clicked
        imageButtonComments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.dialog_box_comments);

                TextView titleComment = dialog.findViewById(R.id.title_dialog);
                final EditText editComment = dialog.findViewById(R.id.edit_dialog);

                Button cancelCommentBtn = dialog.findViewById(R.id.cancel_button_dialog);
                Button okCommentBtn = dialog.findViewById(R.id.ok_button_dialog);

                cancelCommentBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

                okCommentBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mPreferences.edit().putString(PREF_KEY_COMMENT, editComment.getText().toString()).apply();
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        if (this.mDetector.onTouchEvent(event)) {
            return true;
        }
        return super.onTouchEvent(event);
    }

        // According to the swipe up or down, background and smiley change
        public void onSwipe (Boolean isUp ){
            // String message = ""; // FOR TEST

               if(isUp) {
                    if (levelOfMood <= 4 && levelOfMood > 0) {
                        levelOfMood--;
                        // message = "Top to Bottom swipe"; // FOR TEST
                        if (levelOfMood == 4) {
                            imageViewSmiley.setImageResource(imageList.get(0));
                            imageViewBackground.setImageResource(imageList.get(1));
                        } else if (levelOfMood == 3) {
                            imageViewSmiley.setImageResource(imageList.get(2));
                            imageViewBackground.setImageResource(imageList.get(3));
                        } else if (levelOfMood == 2) {
                            imageViewSmiley.setImageResource(imageList.get(4));
                            imageViewBackground.setImageResource(imageList.get(5));
                        } else if (levelOfMood == 1) {
                            imageViewSmiley.setImageResource(imageList.get(6));
                            imageViewBackground.setImageResource(imageList.get(7));
                        } else if (levelOfMood == 0) {
                            imageViewSmiley.setImageResource(imageList.get(8));
                            imageViewBackground.setImageResource(imageList.get(9));
                        }
                    } }


              else  {
                   if (levelOfMood < 4 && levelOfMood >= 0) {
                       levelOfMood++;
                       // message = "Bottom to Top swipe"; // FOR TEST
                       if (levelOfMood == 4) {
                           imageViewSmiley.setImageResource(imageList.get(0));
                           imageViewBackground.setImageResource(imageList.get(1));
                       } else if (levelOfMood == 3) {
                           imageViewSmiley.setImageResource(imageList.get(2));
                           imageViewBackground.setImageResource(imageList.get(3));
                       } else if (levelOfMood == 2) {
                           imageViewSmiley.setImageResource(imageList.get(4));
                           imageViewBackground.setImageResource(imageList.get(5));
                       } else if (levelOfMood == 1) {
                           imageViewSmiley.setImageResource(imageList.get(6));
                           imageViewBackground.setImageResource(imageList.get(7));
                       } else if (levelOfMood == 0) {
                           imageViewSmiley.setImageResource(imageList.get(8));
                           imageViewBackground.setImageResource(imageList.get(9));
                       }
                   }
               }
        }
            // Toast.makeText(this, message, Toast.LENGTH_SHORT).show(); // FOR TEST


    @Override
    public boolean onDown(MotionEvent event) {
        Log.d(DEBUG_TAG,"onDown: " + event.toString());
        return true;
    }

    @Override
    public void onShowPress(MotionEvent event) {
        Log.d(DEBUG_TAG, "onShowPress: " + event.toString());
    }

    @Override
    public boolean onSingleTapUp(MotionEvent event) {
        Log.d(DEBUG_TAG, "onSingleTapUp: " + event.toString());
        return true;
    }

    @Override
    public boolean onScroll(MotionEvent event1, MotionEvent event2, float vX, float vY) {
        Log.d(DEBUG_TAG, "onScroll: " + event1.toString() + event2.toString());
        return true;
    }

    @Override
    public void onLongPress(MotionEvent event) {
        Log.d(DEBUG_TAG, "onLongPress: " + event.toString());
    }

    // Permit the vertical swipe
    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
       float deltaX = e2.getX() - e1.getX();
       float deltaY = e2.getY() - e1.getY();

       if(Math.abs(deltaY) > Math.abs(deltaX)) { // Y travels more than X
           if (e1.getY() < e2.getY()) {
               Log.d(DEBUG_TAG, "Up to Down swipe performed");
               onSwipe(true);
           }

           if (e1.getY() > e2.getY()) {
               Log.d(DEBUG_TAG, "Down to Up swipe performed");
               onSwipe(false);
           }
       }
        return true;
    }

    }





