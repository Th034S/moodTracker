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
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements GestureDetector.OnGestureListener {

    // DIFFERENT VARIABLES
    private ImageView imageViewBackground; // ImageView for the background
    private ImageView imageViewSmiley; // ImageView for the smiley
    private ImageButton imageButtonHistory; // ImageButton to access to the mood history
    private ImageButton imageButtonComments; // ImageButton to add comments

    private SharedPreferences mPreferences; // Use to store data
    public final static String PREFERENCE_FILE = "PREFERENCE_FILE"; // Preference key
    public final static String PREF_KEY_COMMENT = "PREF_KEY_COMMENT"; // Preference key
    public final static String PREF_KEY_MOOD_LEVEL = "PREF_KEY_MOOD_LEVEL";

    public int levelOfMood = 3; // On what mood we are positioned / 3 correspond default mood / ex : 4 = :D / 0 = :(
    private static final String DEBUG_TAG = "Gestures"; // constant FOR LOG

    private GestureDetectorCompat mDetector; // For swipe
    int mCoolSuperHappySoundID; // Sound id for super happy mood
    int mCatHappySoundID; // Sound id for happy mood
    int mNatureNormalSoundID; // Sound id for normal mood
    int mTrainDisappointedSoundID; // Sound id for disappointed mood
    int mBrokenGlassSadSoundID; // Sound id for sad mood
    SoundPool mSoundPool; // For sounds

    // An ArrayList to store the smiley imageView and background
    ArrayList<Integer> imageList = new ArrayList<>();

    int mDay; // To store the day

    // THE METHOD onCreate
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Reference the elements of layout
        imageViewBackground = findViewById(R.id.imageView_background); // Reference ImageView for background
        imageViewSmiley = findViewById(R.id.imageView_happy); // Reference ImageView for mood smiley
        imageButtonHistory = findViewById(R.id.imageButton_history); // Reference ImageButton for access to history
        imageButtonComments = findViewById(R.id.imageButton_comments); // Reference ImageButton to add comments

        final Context context = this; // context constant
        mDetector = new GestureDetectorCompat(this, this); // Initiate the gesture detector
        mPreferences = getSharedPreferences(PREFERENCE_FILE, MODE_PRIVATE); // Initiate the SharedPreferences

        final Calendar c = Calendar.getInstance();
        mDay = c.get(Calendar.DAY_OF_MONTH); // Store the day of month

        mSoundPool = new SoundPool(7, AudioManager.STREAM_MUSIC, 0); // initiate the soundPool

        // Reference the different sounds
        mCoolSuperHappySoundID = mSoundPool.load(getApplicationContext(), R.raw.cool_sound, 1); // Reference sound for super happy mood
        mCatHappySoundID = mSoundPool.load(getApplicationContext(), R.raw.purring_cat_sound, 1); // Reference sound for happy mood
        mNatureNormalSoundID = mSoundPool.load(getApplicationContext(), R.raw.bird_and_nature_sound, 1); // Reference sound for normal mood
        mTrainDisappointedSoundID = mSoundPool.load(getApplicationContext(), R.raw.train_sound, 1); // Reference sound for disappointed mood
        mBrokenGlassSadSoundID = mSoundPool.load(getApplicationContext(), R.raw.broken_glass_sound, 1); // Reference sound for sad mood

        System.out.println(levelOfMood);

        //add smiley images and background in an ArrayList
        imageList.add(0, R.drawable.smiley_super_happy); // Add super happy smiley
        imageList.add(1, R.color.banana_yellow); // Add banana yellow color for super happy smiley
        imageList.add(2, R.drawable.smiley_happy); // Add happy smiley
        imageList.add(3, R.color.light_sage); // Add light sage color for happy smiley
        imageList.add(4, R.drawable.smiley_normal); // Add normal smiley
        imageList.add(5, R.color.cornflower_blue_65); // Add cornflower blue 665 color for normal smiley
        imageList.add(6, R.drawable.smiley_disappointed); // Add disappointed smiley
        imageList.add(7, R.color.warm_grey); // Add warm grey color for disappointed smiley
        imageList.add(8, R.drawable.smiley_sad); // Add sad smiley
        imageList.add(9, R.color.faded_red); // Add faded red color for sad smiley

        Log.d(DEBUG_TAG, "Log works"); // Log d to learn how to use log // TEST

        // Launch a new activity (HistoryActivity) when click on imageButtonHistory
        imageButtonHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent historyActivityIntent = new Intent(MainActivity.this, HistoryActivity.class);
                startActivity(historyActivityIntent);
                Log.d(DEBUG_TAG, "onClick works"); // Log for TEST
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
                // onClick method for CANCEL Button
                cancelCommentBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss(); // close dialog box when click in cancelButton
                    }
                });
                // onClick method for OK Button
                okDialogBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final Calendar c = Calendar.getInstance();
                        Log.d("DEBUG", "onClick works"); // FOR TEST
                        if (mDay == c.get(Calendar.DAY_OF_MONTH)) {
                            mPreferences.edit().putString(PREF_KEY_COMMENT, editComment.getText().toString()).apply(); // Save comment
                        }
                        dialog.dismiss(); // close dialog box
                    }
                });
                dialog.show();

                // until a character is entered, the button remains disabled
                editComment.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { // NOT USE
                    }
                    @Override
                    public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                        okDialogBtn.setEnabled(s.toString().length() != 0); // Enable the okButton when the length of comment does not equal to zero
                    }
                    @Override
                    public void afterTextChanged(Editable editable) { // NOT USE
                    }
                });
            }
        });
        mDay = c.get(Calendar.DAY_OF_MONTH);
        System.out.println("mDay " + mDay);
        System.out.println(levelOfMood + " toto");

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
                    imageViewSmiley.setImageResource(imageList.get(0));       // super happy
                    imageViewBackground.setImageResource(imageList.get(1));   // banana yellow
                    playCoolSuperHappySound(imageViewSmiley);
                } else if (levelOfMood == 3) {
                    imageViewSmiley.setImageResource(imageList.get(2));       // happy
                    imageViewBackground.setImageResource(imageList.get(3));   // light sage (green)
                    playCatHappySound(imageViewSmiley);
                } else if (levelOfMood == 2) {
                    imageViewSmiley.setImageResource(imageList.get(4));       // normal
                    imageViewBackground.setImageResource(imageList.get(5));   // cornflower blue 65
                    playNatureNormalSound(imageViewSmiley);
                } else if (levelOfMood == 1) {
                    imageViewSmiley.setImageResource(imageList.get(6));       // disappointed
                    imageViewBackground.setImageResource(imageList.get(7));   // warm grey
                    playTrainDisappointedSound(imageViewSmiley);
                } else if (levelOfMood == 0) {
                    imageViewSmiley.setImageResource(imageList.get(8));       // sad
                    imageViewBackground.setImageResource(imageList.get(9));   // faded red
                    playBrokenGlassSadSound(imageViewSmiley);
                }
            }
        } else {
            if (levelOfMood < 4 && levelOfMood >= 0) {
                levelOfMood++;
                // message = "Bottom to Top swipe"; // FOR TEST
                if (levelOfMood == 4) {
                    imageViewSmiley.setImageResource(imageList.get(0));       // super happy
                    imageViewBackground.setImageResource(imageList.get(1));   // banana yellow
                    playCoolSuperHappySound(imageViewSmiley);
                } else if (levelOfMood == 3) {
                    imageViewSmiley.setImageResource(imageList.get(2));       // happy
                    imageViewBackground.setImageResource(imageList.get(3));   // light sage (green)
                    playCatHappySound(imageViewSmiley);
                } else if (levelOfMood == 2) {
                    imageViewSmiley.setImageResource(imageList.get(4));       // normal
                    imageViewBackground.setImageResource(imageList.get(5));   // cornflower blue 65
                    playNatureNormalSound(imageViewSmiley);
                } else if (levelOfMood == 1) {
                    imageViewSmiley.setImageResource(imageList.get(6));       // disappointed
                    imageViewBackground.setImageResource(imageList.get(7));   // warm grey
                    playTrainDisappointedSound(imageViewSmiley);
                } else if (levelOfMood == 0) {
                    imageViewSmiley.setImageResource(imageList.get(8));       // sad
                    imageViewBackground.setImageResource(imageList.get(9));   // faded red
                    playBrokenGlassSadSound(imageViewSmiley);
                }
            }
        }
    }
    // Toast.makeText(this, message, Toast.LENGTH_SHORT).show(); // FOR TEST
    // DIFFERENT METHODS OF GESTURE DETECTOR
    @Override
    public boolean onDown(MotionEvent event) {                                              // NOT USE
        Log.d(DEBUG_TAG, "onDown: " + event.toString());
        return true;
    }
    @Override
    public void onShowPress(MotionEvent event) {                                            // NOT USE
        Log.d(DEBUG_TAG, "onShowPress: " + event.toString());
    }
    @Override
    public boolean onSingleTapUp(MotionEvent event) {                                       // NOT USE
        Log.d(DEBUG_TAG, "onSingleTapUp: " + event.toString());
        return true;
    }
    @Override
    public boolean onScroll(MotionEvent event1, MotionEvent event2, float vX, float vY) {   // NOT USE
        Log.d(DEBUG_TAG, "onScroll: " + event1.toString() + event2.toString());
        return true;
    }
    @Override
    public void onLongPress(MotionEvent event) {                                            // NOT USE
        Log.d(DEBUG_TAG, "onLongPress: " + event.toString());
    }

    // onFling method : Permit the vertical swipe
    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        float deltaX = e2.getX() - e1.getX();
        float deltaY = e2.getY() - e1.getY();

        if (Math.abs(deltaY) > Math.abs(deltaX)) { // Y travels more than X
            final Calendar c = Calendar.getInstance(); // Initialize Calendar
            if (e1.getY() < e2.getY()) {
                Log.d(DEBUG_TAG, "Up to Down swipe performed");
                onSwipe(true);
                System.out.println(levelOfMood + " TEST");
                if (mDay == c.get(Calendar.DAY_OF_MONTH)) {
                    mPreferences.edit().putInt(PREF_KEY_MOOD_LEVEL, levelOfMood).apply();
                }
            }

            if (e1.getY() > e2.getY()) {
                Log.d(DEBUG_TAG, "Down to Up swipe performed");
                onSwipe(false);
                System.out.println(levelOfMood + " TEST 2");

                if(mDay == c.get(Calendar.DAY_OF_MONTH)) {
                    mPreferences.edit().putInt(PREF_KEY_MOOD_LEVEL, levelOfMood).apply();
                }
            }
        }
        return true;
    }

    // METHODS TO PLAY MOOD SOUNDS
    // Method who play the sound (COOL) corresponding to the super happy mood
    public void playCoolSuperHappySound(View view) {
        Log.d("DEBUG", "Super happy sound played");
        mSoundPool.play(mCoolSuperHappySoundID, 1.0f, 1.0f, 0, 0, 1.0f);
    }
    // Method who play the sound (CAT) corresponding to the happy mood
    public void playCatHappySound(View view) {
        Log.d("DEBUG", "happy sound played");
        mSoundPool.play(mCatHappySoundID, 1.0f, 1.0f, 0, 0, 1.0f);
    }
    // Method who play the sound (NATURE) corresponding to the normal mood
    public void playNatureNormalSound(View view) {
        Log.d("DEBUG", "normal sound played");
        mSoundPool.play(mNatureNormalSoundID, 1.0f, 1.0f, 0, 0, 1.0f);
    }
    // Method who play the sound (TRAIN) corresponding to the disappointed mood
    public void playTrainDisappointedSound(View view) {
        Log.d("DEBUG", "disappointed sound played");
        mSoundPool.play(mTrainDisappointedSoundID, 1.0f, 1.0f, 0, 0, 1.0f);
    }
    // Method who play the sound (BROKEN GLASS) corresponding to the sad mood
    public void playBrokenGlassSadSound(View view) {
        Log.d("DEBUG", "Sad sound played");
        mSoundPool.play(mBrokenGlassSadSoundID, 1.0f, 1.0f, 0, 0, 1.0f);
    }
}





