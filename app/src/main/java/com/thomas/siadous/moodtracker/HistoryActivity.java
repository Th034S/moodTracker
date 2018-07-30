package com.thomas.siadous.moodtracker;

// IMPORTS
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.ViewTreeObserver;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Calendar;

public class HistoryActivity extends AppCompatActivity {

    // VARIABLES
    private SharedPreferences mPreferencesComment; // to recover comment of mainActivity
    private SharedPreferences mPreferencesMoodLevel; // to recover mood level of mainActivity
    private SharedPreferences mPreferencesDay; // to recover the day of mainActivity

    private String mComment; // to store comment of preference
    private int mMoodLevel;  // to store moodLevel of preference
    private int mDays;       // to store the day of preference

    private LinearLayout layoutHistory; // state LinearLayout

    int a = 0; // height of screen
    int b = 0; // width of screen

    ArrayList<Integer> listColorBackground = new ArrayList<>(5); // state arrayList who will store the five color

    @Override
    protected void onCreate(Bundle savedInstanceState) {

     //   requestWindowFeature(Window.FEATURE_ACTION_BAR);   // ?????
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        Log.d("DEBUG", "ON CREATE WORKS for history"); // FOR TEST
        // Reference layoutHistory
        this.layoutHistory = findViewById(R.id.MyLayoutHistory);
        mPreferencesMoodLevel = getSharedPreferences(MainActivity.PREFERENCE_FILE, MODE_PRIVATE);
        mPreferencesComment = getSharedPreferences(MainActivity.PREFERENCE_FILE, MODE_PRIVATE);
        mPreferencesDay = getSharedPreferences(MainActivity.PREFERENCE_FILE, MODE_PRIVATE);

        listColorBackground.add(0, R.color.faded_red);
        listColorBackground.add(1, R.color.warm_grey);
        listColorBackground.add(2, R.color.cornflower_blue_65);
        listColorBackground.add(3, R.color.light_sage);
        listColorBackground.add(4, R.color.banana_yellow);
//
        mMoodLevel = mPreferencesMoodLevel.getInt(MainActivity.PREF_KEY_MOOD_LEVEL, 3);
        System.out.println(mMoodLevel + " TEST for history");
        mComment = mPreferencesComment.getString(MainActivity.PREF_KEY_COMMENT, "Comment");
        System.out.println(mComment + " TEST for History");
        mDays = mPreferencesDay.getInt(MainActivity.PREF_KEY_DAY, 0);
        System.out.println(mDays + " DAY : TEST FOR HISTORY");

        final Calendar c = Calendar.getInstance();
        final boolean differenceDayTrue = mDays != c.get(Calendar.DAY_OF_MONTH);


        ViewTreeObserver observer = layoutHistory.getViewTreeObserver();  // to calculate the screen size
        observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Log.d("DEBUG", "onGlobalLayout works");


                    init(); // call method init to calculate the size of the screen
                    layoutHistory.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    Log.d("DEBUG", "Oh yeah");
                    CardView cardView = new CardView(getApplicationContext()); // Declare CardView

                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams((int) ((mMoodLevel + 1) * 0.2 * b), (int) (0.143 * a));

                    cardView.setLayoutParams(params);
                    TextView mHistoryBlock = new TextView(getApplicationContext()); // Declare textView

                    mHistoryBlock.setLayoutParams(params);

                    ImageButton commentImageButton = new ImageButton(getApplicationContext()); // Declare imageButton
                    commentImageButton.setImageResource(R.drawable.ic_comment_black_48px); // Change image of commentImageButton
                    commentImageButton.setBackgroundColor(getResources().getColor(R.color.transparent)); // Change background of commentImageButton to transparent

                    Log.d("DEBUG", "Always works !!");
                    cardView.setCardBackgroundColor(getResources().getColor(listColorBackground.get(mMoodLevel))); // change background of cardView
/*
                        Log.d("DEBUG", "condition enter");
                        commentImageButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Log.d("DEBUG", "Enter onClick Comment");
                                Toast msg = Toast.makeText(HistoryActivity.this, mComment, Toast.LENGTH_SHORT);
                                msg.show();
                            }
                        });
*/
                  //  cardView.addView(commentImageButton);

                    cardView.addView(mHistoryBlock); // to add mHistoryBlock to the cardView
                    cardView.addView(commentImageButton); // to add commentImageButton to the cardView
                    layoutHistory.addView(cardView); // to add cardView to the view (layoutHistory)



            }
        });


    }
    // method init o obtain the width and the height of the layout
    protected void init() {
        a = layoutHistory.getHeight(); // to obtain height
        b = layoutHistory.getWidth();  // to obtain width
        Toast.makeText(HistoryActivity.this, " Height " + a + " Width " + b, Toast.LENGTH_LONG).show();
    }


}
