package com.thomas.siadous.moodtracker;

 // IMPORTS

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.SoundPool;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

 // MainActivity class, permit to access : to the different moods with the swipe
public class MainActivity extends AppCompatActivity implements GestureDetector.OnGestureListener {

    // DIFFERENT VARIABLES

    private ImageView imageViewBackground; // ImageView for the background
    private ImageView imageViewSmiley; // ImageView for the smiley
    private ImageButton imageButtonHistory; // ImageButton to access to the mood history
    private ImageButton imageButtonComments; // ImageButton to add comments

    private SharedPreferences mPreferences; // Use to store data
    public final static String PREFERENCE_FILE = "PREFERENCE_FILE"; // Preference key
    public final static String PREF_KEY_COMMENT = "PREF_KEY_COMMENT"; // Preference key
    int levelOfMood = 3; // On what mood we are positioned / ex : 4 = :D / 0 = :(
    private static final String DEBUG_TAG = "Gestures"; // constant FOR LOG

    private GestureDetectorCompat mDetector; // For swipe
    int mCoolSuperHappySoundID;
    int mCatHappySoundID;
    int mNatureNormalSoundID; // Nature Sound id
    int mTrainDisappointedSoundID;
    int mBrokenGlassSadSoundID;
    SoundPool mSoundPool; // For sounds

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

        final Context context = this; // context constant
        mDetector = new GestureDetectorCompat(this, this); // Initiate the gesture detector
        mPreferences = getSharedPreferences(PREFERENCE_FILE, MODE_PRIVATE); // Initiate the SharedPreferences

        final SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss"); // Format for date
        final String limitTime = "00:00:00"; // limit hour to save mood

        mSoundPool = new SoundPool(7, AudioManager.STREAM_MUSIC, 0); // initiate the soundPool

        mCoolSuperHappySoundID = mSoundPool.load(getApplicationContext(), R.raw.cool_sound, 1);
        mCatHappySoundID = mSoundPool.load(getApplicationContext(), R.raw.purring_cat_sound, 1);
        mNatureNormalSoundID = mSoundPool.load(getApplicationContext(), R.raw.bird_and_nature_sound, 1); // Reference nature sound
        mTrainDisappointedSoundID = mSoundPool.load(getApplicationContext(), R.raw.train_sound, 1);
        mBrokenGlassSadSoundID = mSoundPool.load(getApplicationContext(), R.raw.broken_glass_sound, 1);

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

        Log.d(DEBUG_TAG, "Log works"); // Log d FOR TEST

        // Launch a new activity (HistoryActivity) when click on imageButtonHistory
        imageButtonHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent historyActivityIntent = new Intent(MainActivity.this, HistoryActivity.class);
                startActivity(historyActivityIntent);
                Log.d(DEBUG_TAG, "Clicked");
            }
        });
        // dialog box to add comments appears when clicked
        imageButtonComments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.dialog_box_comments);

                //References
                TextView titleComment = dialog.findViewById(R.id.title_dialog); // title of the dialog box
                final EditText editComment = dialog.findViewById(R.id.edit_dialog); // Input
                Button cancelCommentBtn = dialog.findViewById(R.id.cancel_button_dialog); // cancel button
                final Button okDialogBtn = dialog.findViewById(R.id.ok_button_dialog); // ok button

                okDialogBtn.setEnabled(false); // Disable okDialogButton

                cancelCommentBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss(); // close dialog box when click in cancelButton
                    }
                });

                okDialogBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Log.d("DEBUG", "enter onClick");
                        mPreferences.edit().putString(PREF_KEY_COMMENT, editComment.getText().toString()).apply(); // Save comment
                        dialog.dismiss(); // close dialog box

                    }
                });

                dialog.show();

                // until a character is entered, the button remains disabled
                editComment.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    }
                    @Override
                    public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                        okDialogBtn.setEnabled(s.toString().length() != 0); // Enable the okButton when the length of comment does not equal to zero
                    }
                    @Override
                    public void afterTextChanged(Editable editable) {
                    }
                });
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) { // For the good performance of gestureDetector
        return this.mDetector.onTouchEvent(event) || super.onTouchEvent(event);
    }

    // According to the swipe up or down, background and smiley change
    public void onSwipe(Boolean isUp) {
        // String message = ""; // FOR TEST
        if (isUp) {
            if (levelOfMood <= 4 && levelOfMood > 0) {
                levelOfMood--;
                // message = "Top to Bottom swipe"; // FOR TEST
                if (levelOfMood == 4) {
                    imageViewSmiley.setImageResource(imageList.get(0));
                    imageViewBackground.setImageResource(imageList.get(1));
                    playCoolSuperHappySound(imageViewSmiley);
                } else if (levelOfMood == 3) {
                    imageViewSmiley.setImageResource(imageList.get(2));
                    imageViewBackground.setImageResource(imageList.get(3));
                    playCatHappySound(imageViewSmiley);
                } else if (levelOfMood == 2) {
                    imageViewSmiley.setImageResource(imageList.get(4));
                    imageViewBackground.setImageResource(imageList.get(5));
                    playNatureNormalSound(imageViewSmiley);
                } else if (levelOfMood == 1) {
                    imageViewSmiley.setImageResource(imageList.get(6));
                    imageViewBackground.setImageResource(imageList.get(7));
                    playTrainDisappointedSound(imageViewSmiley);
                } else if (levelOfMood == 0) {
                    imageViewSmiley.setImageResource(imageList.get(8));
                    imageViewBackground.setImageResource(imageList.get(9));
                    playBrokenGlassSadSound(imageViewSmiley);
                }
            }
        } else {
            if (levelOfMood < 4 && levelOfMood >= 0) {
                levelOfMood++;
                // message = "Bottom to Top swipe"; // FOR TEST
                if (levelOfMood == 4) {
                    imageViewSmiley.setImageResource(imageList.get(0));
                    imageViewBackground.setImageResource(imageList.get(1));
                    playCoolSuperHappySound(imageViewSmiley);
                } else if (levelOfMood == 3) {
                    imageViewSmiley.setImageResource(imageList.get(2));
                    imageViewBackground.setImageResource(imageList.get(3));
                    playCatHappySound(imageViewSmiley);
                } else if (levelOfMood == 2) {
                    imageViewSmiley.setImageResource(imageList.get(4));
                    imageViewBackground.setImageResource(imageList.get(5));
                    playNatureNormalSound(imageViewSmiley);
                } else if (levelOfMood == 1) {
                    imageViewSmiley.setImageResource(imageList.get(6));
                    imageViewBackground.setImageResource(imageList.get(7));
                    playTrainDisappointedSound(imageViewSmiley);
                } else if (levelOfMood == 0) {
                    imageViewSmiley.setImageResource(imageList.get(8));
                    imageViewBackground.setImageResource(imageList.get(9));
                    playBrokenGlassSadSound(imageViewSmiley);
                }
            }
        }
    }
    // Toast.makeText(this, message, Toast.LENGTH_SHORT).show(); // FOR TEST

    @Override
    public boolean onDown(MotionEvent event) {
        Log.d(DEBUG_TAG, "onDown: " + event.toString());
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

        if (Math.abs(deltaY) > Math.abs(deltaX)) { // Y travels more than X
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

    // METHODS TO PLAY MOOD SOUNDS

    // Method who play the sound corresponding to the super happy mood
    public void playCoolSuperHappySound(View view) {
        Log.d("DEBUG", "Super happy sound played");
        mSoundPool.play(mCoolSuperHappySoundID, 1.0f, 1.0f, 0, 0, 1.0f);
    }
    // Method who play the sound corresponding to the happy mood
    public void playCatHappySound(View view) {
        Log.d("DEBUG", "happy sound played");
        mSoundPool.play(mCatHappySoundID, 1.0f, 1.0f, 0, 0, 1.0f);
    }
    // Method who play the sound corresponding to the normal mood
    public void playNatureNormalSound(View view) {
        Log.d("DEBUG", "normal sound played");
        mSoundPool.play(mNatureNormalSoundID, 1.0f, 1.0f, 0, 0, 1.0f);
    }
    // Method who play the sound corresponding to the disappointed mood
    public void playTrainDisappointedSound(View view) {
        Log.d("DEBUG", "disappointed sound played");
        mSoundPool.play(mTrainDisappointedSoundID, 1.0f, 1.0f, 0, 0, 1.0f);
    }
    // Method who play the sound corresponding to the sad mood
    public void playBrokenGlassSadSound(View view) {
        Log.d("DEBUG", "Sad sound played");
        mSoundPool.play(mBrokenGlassSadSoundID, 1.0f, 1.0f, 0, 0, 1.0f);
    }


}





