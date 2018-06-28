package com.thomas.siadous.moodtracker.controller;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


import com.thomas.siadous.moodtracker.R;

public class HistoryActivity extends AppCompatActivity {


    private SharedPreferences mPreferencesHistory;
    private String mComment;
    TextView mDisplayComment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        mDisplayComment = findViewById(R.id.comment_test_preferences);

        mPreferencesHistory = getSharedPreferences(MainActivity.PREFERENCE_FILE, MODE_PRIVATE);
        mComment = mPreferencesHistory.getString(MainActivity.PREF_KEY_COMMENT, "Comments");
        mDisplayComment.setText(mComment); // vérifier si le bon setText



    }


}
