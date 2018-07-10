package com.thomas.siadous.moodtracker;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

/**
 * HistoryActivity class permit to display the mood history
 */
public class HistoryActivity extends AppCompatActivity {

    /**
     * VARIABLES
     */
    private SharedPreferences mPreferencesHistory;
    private String mComment;

    TextView mSevenDaysAgoTextView;
    TextView mSixDaysAgoTextView;
    TextView mFiveDaysAgoTextView;
    TextView mFourDaysAgoTextView;
    TextView mThreeDaysAgoTextView;
    TextView mTwoDaysAgoTextView;
    TextView mOneDayAgoTextView;


    ImageButton mSevenDaysAgoComment;
    ImageButton mSixDaysAgoComment;
    ImageButton mFiveDaysAgoComment;
    ImageButton mFourDaysAgoComment;
    ImageButton mThreeDaysAgoComment;
    ImageButton mTwoDaysAgoComment;
    ImageButton mOneDayAgoComment;



    int mColorTest = R.color.banana_yellow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        // Reference the textView
        mSevenDaysAgoTextView = findViewById(R.id.happy_block_textView_history);
        mSixDaysAgoTextView = findViewById(R.id.super_happy_block_textView_history);
        mFiveDaysAgoTextView = findViewById(R.id.normal_block_textView_history);
        mFourDaysAgoTextView = findViewById(R.id.disappointed_block_textView_history);
        mThreeDaysAgoTextView = findViewById(R.id.sad_block_textView_history);
        mTwoDaysAgoTextView = findViewById(R.id.disappointed_2_block_textView_history);
        mOneDayAgoTextView = findViewById(R.id.super_happy_2_block_textView_history);

        // Reference the imageButton
        mSevenDaysAgoComment = findViewById(R.id.comment_happy_imageButton_history);
        mSixDaysAgoComment = findViewById(R.id.comment_super_happy_imageButton_history);
        mFiveDaysAgoComment = findViewById(R.id.comment_normal_imageButton_history);
        mFourDaysAgoComment = findViewById(R.id.comment_disappointed_imageButton_history);
        mThreeDaysAgoComment = findViewById(R.id.comment_sad_imageButton_history);
        mTwoDaysAgoComment = findViewById(R.id.comment_disappointed_2_imageButton_history);
        mOneDayAgoComment = findViewById(R.id.comment_super_happy_2_imageButton_history);



        Log.d("DEBUG", "Enter onCreate");

        mSevenDaysAgoComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("DEBUG", "Enter onClick");
                mPreferencesHistory = getSharedPreferences(MainActivity.PREFERENCE_FILE, MODE_PRIVATE);
                mComment = mPreferencesHistory.getString(MainActivity.PREF_KEY_COMMENT, "Comments");
                Toast msg = Toast.makeText(HistoryActivity.this, mComment, Toast.LENGTH_SHORT);
                msg.show();
            }
        });

        mSevenDaysAgoTextView.setText("Hier");

        //mDisplayComment.setBackgroundResource(R.color.banana_yellow);



    }

    private int dpToPx(int dp) {
        return Math.round(dp*(getResources().getDisplayMetrics().xdpi/ DisplayMetrics.DENSITY_DEFAULT));
    }




}
