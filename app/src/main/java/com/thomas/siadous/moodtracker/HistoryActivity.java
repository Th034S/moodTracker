package com.thomas.siadous.moodtracker;
// IMPORTS
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity {

    // VARIABLES
    private SharedPreferences mPreferencesComment;
    private SharedPreferences mPreferencesMoodLevel;
    private String mComment;

    private LinearLayout layoutHistory;

    int mColorTest = R.color.banana_yellow;
    int a = 0;
    int b = 0;

    ArrayList<Integer> listColorBackground = new ArrayList<>(5);
    int mMoodLevel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        requestWindowFeature(Window.FEATURE_ACTION_BAR);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        Log.d("DEBUG", "my on Create works");
        // Reference layoutHistory
        this.layoutHistory = findViewById(R.id.MyLayoutHistory);
        mPreferencesMoodLevel = getSharedPreferences(MainActivity.PREFERENCE_FILE, MODE_PRIVATE);
        Log.d("DEBUG", "ON CREATE WORKS");
        listColorBackground.add(0, R.color.faded_red);
        listColorBackground.add(1, R.color.warm_grey);
        listColorBackground.add(2, R.color.cornflower_blue_65);
        listColorBackground.add(3, R.color.light_sage);
        listColorBackground.add(4, R.color.banana_yellow);
//
        mMoodLevel = mPreferencesMoodLevel.getInt(MainActivity.PREF_KEY_MOOD_LEVEL, 1);
        System.out.println(mMoodLevel + " TEST for history");
        Log.d("DEBUG", "onCreate Method works !");

        ViewTreeObserver observer = layoutHistory.getViewTreeObserver();
        observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Log.d("DEBUG", "onGlobalLayout works");
                init();
                layoutHistory.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                CardView cardView = new CardView(getApplicationContext());

                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams((int) (0.2 * b), (int) (0.143 * a));

                    cardView.setLayoutParams(params);
                    TextView mHistoryBlock = new TextView(getApplicationContext());
                    mHistoryBlock.setLayoutParams(params);

                cardView.setCardBackgroundColor(getResources().getColor(mColorTest));

             // cardView.addView(mCommentSevenDays);
                cardView.addView(mHistoryBlock);
                layoutHistory.addView(cardView);
            }
        });

        // Initialize a new CardView

       /* TextView mSevenDaysAgo = new TextView(this);
        ViewGroup.LayoutParams layoutParams = new ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 160);
        mSevenDaysAgo.setLayoutParams(layoutParams);
        mSevenDaysAgo.setText("hello");
        mSevenDaysAgo.setBackgroundResource(R.color.banana_yellow);
        layoutHistory.addView(mSevenDaysAgo); */

  /*
   mSevenDaysAgoComment.setOnClickListener(new View.OnClickListener() {
  @Override public void onClick(View view) {
  Log.d("DEBUG", "Enter onClick");
  mPreferencesHistory = getSharedPreferences(MainActivity.PREFERENCE_FILE, MODE_PRIVATE);
  mComment = mPreferencesHistory.getString(MainActivity.PREF_KEY_COMMENT, "Comments");
  Toast msg = Toast.makeText(HistoryActivity.this, mComment, Toast.LENGTH_SHORT);
  msg.show();
  }
  });

 */
    }
    // To obtain the width and the height of the layout
    protected void init() {
        a = layoutHistory.getHeight();
        b = layoutHistory.getWidth();
        Toast.makeText(HistoryActivity.this, " Height " + a + " Width " + b, Toast.LENGTH_LONG).show();
    }


}
