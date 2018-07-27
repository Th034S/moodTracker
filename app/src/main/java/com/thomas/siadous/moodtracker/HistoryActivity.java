package com.thomas.siadous.moodtracker;
// IMPORTS
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

public class HistoryActivity extends AppCompatActivity {

    // VARIABLES
    private SharedPreferences mPreferencesComment;
    private SharedPreferences mPreferencesMoodLevel;
    private SharedPreferences mPreferencesDay;

    private String mComment;
    private int mMoodLevel;
    private int mDays;

    private LinearLayout layoutHistory;

    int a = 0;
    int b = 0;

    ArrayList<Integer> listColorBackground = new ArrayList<>(5);


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        requestWindowFeature(Window.FEATURE_ACTION_BAR);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        Log.d("DEBUG", "ON CREATE WORKS for history");
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


                    init();
                    layoutHistory.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    Log.d("DEBUG", "Oh yeah");
                    CardView cardView = new CardView(getApplicationContext());

                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams((int) ((mMoodLevel + 1) * 0.2 * b), (int) (0.143 * a));

                    cardView.setLayoutParams(params);
                    TextView mHistoryBlock = new TextView(getApplicationContext());

                    Log.d("DEBUG", "YES");
                    mHistoryBlock.setLayoutParams(params);

                    ImageButton commentImageButton = new ImageButton(getApplicationContext());
                    commentImageButton.setImageResource(R.drawable.ic_comment_black_48px);
                    commentImageButton.setBackgroundColor(getResources().getColor(R.color.transparent));
                   
                    Log.d("DEBUG", "Always works !!");
                    cardView.setCardBackgroundColor(getResources().getColor(listColorBackground.get(mMoodLevel)));
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

                    cardView.addView(mHistoryBlock);
                    cardView.addView(commentImageButton);
                    layoutHistory.addView(cardView);



                  //  mPreferencesDay.edit().clear().apply();
                    // TEST
             /*
                if(differenceDayTrue) {

                    init();
                    layoutHistory.getViewTreeObserver().removeOnGlobalLayoutListener(this);

                    CardView cardView2 = new CardView(getApplicationContext());
                    LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams((int) ((mMoodLevel + 1) * 0.2 * b), (int) (0.143 * a));
                    cardView2.setLayoutParams(params2);
                    TextView mHistoryBlock2 = new TextView(getApplicationContext());
                    mHistoryBlock2.setLayoutParams(params2);
                    cardView2.setCardBackgroundColor(getResources().getColor(listColorBackground.get(mMoodLevel)));
                    cardView2.addView(mHistoryBlock2);
                    layoutHistory.addView(cardView2);
                }
             */
            }
        });



    }
    // To obtain the width and the height of the layout
    protected void init() {
        a = layoutHistory.getHeight();
        b = layoutHistory.getWidth();
        Toast.makeText(HistoryActivity.this, " Height " + a + " Width " + b, Toast.LENGTH_LONG).show();
    }


}
