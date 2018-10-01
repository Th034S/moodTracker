package com.thomas.siadous.moodtracker;

// IMPORTS
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.SoundPool;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements GestureDetector.OnGestureListener {

    // DIFFERENT VARIABLES
    private ImageView imageViewBackground; // State ImageView for the background
    private ImageView imageViewSmiley; // State ImageView for the smiley
    private ImageButton imageButtonHistory; // State ImageButton to access to the mood history
    private ImageButton imageButtonComments; // State ImageButton to add comments

    private SharedPreferences mPreferences; // Use to store data

    public final static String PREFERENCE_FILE = "PREFERENCE_FILE"; // Preference key
    public final static String PREF_KEY_MOOD_LEVEL_BIS = "PREF_KEY_MOOD_LEVEL_BIS";
    public final static String PREF_KEY_COMMENT_BIS = "PREF_KEY_COMMENT_BIS";
    public final static String PREF_KEY = "PREF_KEY"; // Preference key

    public int levelOfMood = 3; // On what mood we are positioned / 3 correspond default mood / ex : 4 = :D / 0 = :(
    final int defaultMoodLevel = 3; // moodLevel by default
    int moodLevelBis;
    String commentBis;
    private static final String DEBUG_TAG = "Gestures"; // constant FOR LOG
    final long oneDayInMillis = 86_400_000L; // constant one day in milliseconds
    final long twoDaysInMillis = 172_800_000L; // constant two days in milliseconds
    final long sevenDaysInMillis = 604_800_000L; // constant seven days in milliseconds
    long lastDate; // last date in milliseconds
    long nowDate; // now date in milliseconds

    private GestureDetectorCompat mDetector; // For swipe
    int mCoolSuperHappySoundID; // Sound id for super happy mood
    int mCatHappySoundID; // Sound id for happy mood
    int mNatureNormalSoundID; // Sound id for normal mood
    int mTrainDisappointedSoundID; // Sound id for disappointed mood
    int mBrokenGlassSadSoundID; // Sound id for sad mood
    SoundPool mSoundPool; // State for sounds

    // An ArrayList to store the smiley imageView and background
    ArrayList<Integer> imageList = new ArrayList<>();

    int nowDay; // To store the day now
    int nowMonth; // to store the month now
    int nowYear; // to store the year now
    int lastDay; // to store the last day registered

    int dayNumber; // day number
    int dayNumberToDelete = 0; // day number to delete of dataHistory

    String mDataHistory; // to store days with mood, day, com
    String dateFormat; // to store the day, the month, and the year
    String comment = ""; // To store comment

    // THE METHOD onCreate
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        referenceElementLayout();
        mDetector = new GestureDetectorCompat(this, this); // Initiate the gesture detector
        mPreferences = getSharedPreferences(PREFERENCE_FILE, MODE_PRIVATE); // Initiate the SharedPreferences

        System.out.println(mPreferences.getInt(PREF_KEY_MOOD_LEVEL_BIS, -42) + " ????????????????????????????");
        moodLevelBis = mPreferences.getInt(PREF_KEY_MOOD_LEVEL_BIS, -1);
        commentBis = mPreferences.getString(PREF_KEY_COMMENT_BIS, " ");
        System.out.println(moodLevelBis + " IS IT EMPTY ????");

        if (moodLevelBis != -1) {
            levelOfMood = moodLevelBis;
            comment = commentBis;
            System.out.println(mPreferences.getAll() + " fkrjiejgierigjeroigjeruijjiugjregrg");
            mPreferences.edit().clear().apply();
            System.out.println(mPreferences.getAll() + " ,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,");
        }

        System.out.println(moodLevelBis + " IS IT -1 CAUSE I CLEARED ???");
        addAValueToLastDateForTheFirstLaunch();
        checkDifferenceOfDays();
        mSoundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0); // initiate the soundPool
        referenceSound();
        addToImageList();
        launchHistory();
        manageAlertDialog();
    }

    private void addAValueToLastDateForTheFirstLaunch() {
        final Calendar c = Calendar.getInstance(); // Initiate Calendar
        nowDay = c.get(Calendar.DAY_OF_MONTH); // to store the day of the month
        nowMonth = c.get(Calendar.MONTH) + 1;
        nowYear = c.get(Calendar.YEAR);

        if (lastDate == 0) {
            lastDay = nowDay;
            final Calendar c6 = Calendar.getInstance(); // Initialize Calendar
            nowDate = c6.getTimeInMillis();
            dateFormat = lastDay + "-" + nowMonth + "-" + nowYear;
            DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
            Date date = null;
            try {
                date = formatter.parse(dateFormat);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            lastDate = date.getTime();
            System.out.println((nowDate - lastDate) + " NOWDATE - LASTDATE");
        }
    }

    private void launchHistory() {
        // Launch a new activity (HistoryActivity) when click on imageButtonHistory
        imageButtonHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent historyActivityIntent = new Intent(MainActivity.this, HistoryActivity.class);
                startActivity(historyActivityIntent);
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) { // For the good performance of gestureDetector
        return this.mDetector.onTouchEvent(event) || super.onTouchEvent(event);
    }

    private void addToImageList() {
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
    }

    private void referenceSound() {
        // Reference the different sounds
        mCoolSuperHappySoundID = mSoundPool.load(getApplicationContext(), R.raw.cool_sound, 1); // Reference sound for super happy mood
        mCatHappySoundID = mSoundPool.load(getApplicationContext(), R.raw.purring_cat_sound, 1); // Reference sound for happy mood
        mNatureNormalSoundID = mSoundPool.load(getApplicationContext(), R.raw.bird_and_nature_sound, 1); // Reference sound for normal mood
        mTrainDisappointedSoundID = mSoundPool.load(getApplicationContext(), R.raw.train_sound, 1); // Reference sound for disappointed mood
        mBrokenGlassSadSoundID = mSoundPool.load(getApplicationContext(), R.raw.broken_glass_sound, 1); // Reference sound for sad mood
    }

    private void referenceElementLayout() {
        // Reference the elements of layout
        imageViewBackground = findViewById(R.id.imageView_background); // Reference ImageView for background
        imageViewSmiley = findViewById(R.id.imageView_happy); // Reference ImageView for mood smiley
        imageButtonHistory = findViewById(R.id.imageButton_history); // Reference ImageButton for access to history
        imageButtonComments = findViewById(R.id.imageButton_comments); // Reference ImageButton to add comments
    }

    // permit to manage the dialog box
    private void manageAlertDialog() {
        imageButtonComments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                final EditText editComment;
                editComment = new EditText(MainActivity.this);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
                editComment.setLayoutParams(lp);
                alert.setView(editComment);
                Log.d("DEBUG", "Enters");
                alert.setTitle("Commentaire");
                alert.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        alert.setCancelable(true);
                    }
                });
                Log.d("DEBUG", "Enters 2");
                alert.setPositiveButton("Valider", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        comment = editComment.getText().toString();
                        alert.setCancelable(true); // close dialog box
                    }
                });
                Log.d("DEBUG", "Enters 3");
                // final AlertDialog dialog = alert.create();
                alert.show();
                Log.d("DEBUG", "Enters 4");
            }
        });
    }

    // According to the swipe up or down, background and smiley change
    public void onSwipe(Boolean isUp) {
        if (isUp) {

            if (levelOfMood <= 4 && levelOfMood > 0) {
                levelOfMood--;
                generateDisplayAccordingToMoodLevel();
            }
        } else {

            if (levelOfMood < 4 && levelOfMood >= 0) {
                levelOfMood++;
                generateDisplayAccordingToMoodLevel();
            }
        }
    }

    private void generateDisplayAccordingToMoodLevel() {
        switch (levelOfMood) {
            case 4:
                imageViewSmiley.setImageResource(imageList.get(0));       // super happy
                imageViewBackground.setImageResource(imageList.get(1));   // banana yellow
                playCoolSuperHappySound(imageViewSmiley);
                break;
            case 3:
                imageViewSmiley.setImageResource(imageList.get(2));       // happy
                imageViewBackground.setImageResource(imageList.get(3));   // light sage (green)
                playCatHappySound(imageViewSmiley);
                break;
            case 2:
                imageViewSmiley.setImageResource(imageList.get(4));       // normal
                imageViewBackground.setImageResource(imageList.get(5));   // cornflower blue 65
                playNatureNormalSound(imageViewSmiley);
                break;
            case 1:
                imageViewSmiley.setImageResource(imageList.get(6));       // disappointed
                imageViewBackground.setImageResource(imageList.get(7));   // warm grey
                playTrainDisappointedSound(imageViewSmiley);
                break;
            case 0:
                imageViewSmiley.setImageResource(imageList.get(8));       // sad
                imageViewBackground.setImageResource(imageList.get(9));   // faded red
                playBrokenGlassSadSound(imageViewSmiley);
                break;
        }
    }

    // permit to register data in preferences
    private void checkDifferenceOfDays() {
        final Calendar c = Calendar.getInstance(); // Initialize Calendar
        nowDate = c.getTimeInMillis();                   // TEST
        long differenceDaysInMillis = nowDate - lastDate;
        if (differenceDaysInMillis < oneDayInMillis) {
            System.out.println("Coucou je fais moins d'1 jour %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
        }
        if ((differenceDaysInMillis) >= oneDayInMillis && (differenceDaysInMillis) < twoDaysInMillis) {
            saveDataForOneDay();
            levelOfMood = 3; // After save, reset the default screen
            generateDisplayAccordingToMoodLevel(); // generate the display
        } else {
            if ((differenceDaysInMillis) > oneDayInMillis && (differenceDaysInMillis) <= sevenDaysInMillis) {
                saveDataForMoreOneDayAndLessSevenDays();
                levelOfMood = 3; // After save, reset the default screen
                generateDisplayAccordingToMoodLevel(); // generate the display
            } else if ((differenceDaysInMillis) > sevenDaysInMillis) {
                resetAndSaveData();
                levelOfMood = 3; // After save, reset the default screen
                generateDisplayAccordingToMoodLevel(); // generate the display
            }
        }
    }

    private void saveDataForOneDay() {
        final Calendar c = Calendar.getInstance();
        lastDay = c.get(Calendar.DAY_OF_MONTH) - 1;
        System.out.println("coucou je fais 1 jour ??????????????????????????????????????????????????????????????????????????????????????????????????????????");
        // System.out.println("nowDate - lastDate = " + (nowDate - lastDate));
        mDataHistory = mDataHistory + "/" + lastDay + "," + levelOfMood + ", " + comment;
        lastDate = nowDate;
        manageEighthDayAndMore();

        mPreferences.edit().putString(PREF_KEY, mDataHistory).apply();
        comment = "";
    }

    private void saveDataForMoreOneDayAndLessSevenDays() {
        final Calendar c = Calendar.getInstance();
        dayNumber = (int) ((nowDate - lastDate) / oneDayInMillis);
        lastDay = (c.get(Calendar.DAY_OF_MONTH) - dayNumber);
        System.out.println("coucou je fais plus de 1 jour et moins de 7 jours !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        //System.out.println("nowDate - lastDate = " + (nowDate - lastDate));
        for (int i = 0; i <= dayNumber - 1; i++) {
            lastDate = lastDate + oneDayInMillis;
            mDataHistory = mDataHistory + "/" + lastDay + "," + defaultMoodLevel + ", ";
            lastDay++;
        }
        lastDate = nowDate;
        manageEighthDayAndMore();
        mPreferences.edit().putString(PREF_KEY, mDataHistory).apply();
        comment = "";
    }

    private void resetAndSaveData() {
        mDataHistory = "";
        System.out.println("Coucou je fais plus de 7 jours /////////////////////////////////////////////////////////////////////////////////////////////");
        //System.out.println("nowDate - lastDate = " + (nowDate - lastDate));
        lastDate = nowDate;
        mPreferences.edit().putString(PREF_KEY, mDataHistory).apply();
        comment = "";
    }

    private void manageEighthDayAndMore() {
        String part[] = mDataHistory.split("/");
        int numberOfDays = part.length - 1;
        System.out.print(mDataHistory + "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        dayNumberToDelete = numberOfDays - 7;
        if (dayNumberToDelete >= 1) {
            System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            for (int i = 1; i <= dayNumberToDelete; i++) {
                part[i] = "";
            }
            mDataHistory = "";
            for (int i = dayNumberToDelete + 1; i <= (dayNumberToDelete + 7); i++) {
                System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                mDataHistory = mDataHistory + "/" + part[i];
            }
        }
    }

    // DIFFERENT METHODS OF GESTURE DETECTOR
    @Override
    public boolean onDown(MotionEvent event) {                                              // NOT USE
        return true;
    }

    @Override
    public void onShowPress(MotionEvent event) {                                            // NOT USE
    }

    @Override
    public boolean onSingleTapUp(MotionEvent event) {                                       // NOT USE
        return true;
    }

    @Override
    public boolean onScroll(MotionEvent event1, MotionEvent event2, float vX, float vY) {   // NOT USE
        return true;
    }

    @Override
    public void onLongPress(MotionEvent event) {                                            // NOT USE
    }

    // onFling method : Permit the vertical swipe
    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        float deltaX = e2.getX() - e1.getX();
        float deltaY = e2.getY() - e1.getY();

        if (Math.abs(deltaY) > Math.abs(deltaX)) { // Y travels more than X

            if (e1.getY() < e2.getY()) {
                Log.d(DEBUG_TAG, "Up to Down swipe performed");
                onSwipe(true);
                comment = "";
            }

            if (e1.getY() > e2.getY()) {
                Log.d(DEBUG_TAG, "Down to Up swipe performed");
                onSwipe(false);
                comment = "";
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

    @Override
    protected void onStart() {
        super.onStart();
        System.out.println("HEY ! MainActivity : ON START LAUNCHED !");
    }

    @Override
    protected void onResume() {
        super.onResume();
        System.out.println("HEY ! mainActivity : ON RESUME LAUNCHED !");
        checkDifferenceOfDays();
    }

    @Override
    protected void onPause() {
        super.onPause();
        System.out.println("HEY ! mainActivity : ON PAUSE LAUNCHED !");
        checkDifferenceOfDays();
    }

    @Override
    protected void onStop() {
        super.onStop();
        System.out.println("HEY ! MainActivity : ON STOP LAUNCHED !");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.out.println("HEY ! MainActivity : ON DESTROY LAUNCHED !");
        System.out.println("MESSAGE FROM ON DESTROY METHOD : level of mood " + levelOfMood );
        mPreferences = getSharedPreferences(PREFERENCE_FILE, MODE_PRIVATE);
        mPreferences.edit().putInt(PREF_KEY_MOOD_LEVEL_BIS, levelOfMood).apply();
        mPreferences.edit().putString(PREF_KEY_COMMENT_BIS, comment).apply();
        checkDifferenceOfDays();
    }
}






