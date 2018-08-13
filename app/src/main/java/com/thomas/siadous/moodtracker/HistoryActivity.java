package com.thomas.siadous.moodtracker;

// IMPORTS
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Calendar;


public class HistoryActivity extends AppCompatActivity  {

    private LinearLayout layoutHistory; // state LinearLayout

    int a = 0; // height of screen
    int b = 0; // width of screen
    ArrayList<Integer> listColorBackground = new ArrayList<>(5); // state arrayList who will store the five color

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        this.layoutHistory = findViewById(R.id.MyLayoutHistory);  // Reference layoutHistory

        SharedPreferences mPreference = getSharedPreferences(MainActivity.PREFERENCE_FILE, MODE_PRIVATE);
         int mMoodLevel = mPreference.getInt(MainActivity.PREF_KEY_MOOD_LEVEL,3);
         String mComment = mPreference.getString(MainActivity.PREF_KEY_COMMENT, "Aucun commentaire"); //to store comment
         int mDay = mPreference.getInt(MainActivity.PREF_KEY_DAY, 1);

        addColorToListColorBackground();

        final Calendar c = Calendar.getInstance();

        while(c.get(Calendar.DAY_OF_MONTH) - mDay >= 1) {
            createCardView(mMoodLevel, mComment);
            mDay++;
        }
        
    }

    // Method to add color to the listColorBackground
    private void addColorToListColorBackground() {
        listColorBackground.add(0, R.color.faded_red);
        listColorBackground.add(1, R.color.warm_grey);
        listColorBackground.add(2, R.color.cornflower_blue_65);
        listColorBackground.add(3, R.color.light_sage);
        listColorBackground.add(4, R.color.banana_yellow);
    }

    //method to create a card view for the moodLevel and comment
    private void createCardView( final int moodLevel, final String comment) {
        ViewTreeObserver observer = layoutHistory.getViewTreeObserver();
        observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                init(); // call method init to calculate the size of the screen
                layoutHistory.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                CardView cardView = new CardView(getApplicationContext()); // Declare CardView

                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams((int) ((moodLevel + 1) * 0.2 * b), (int) (0.143 * a));

                cardView.setLayoutParams(params);

                TextView mHistoryBlock = new TextView(getApplicationContext()); // Declare textView
                mHistoryBlock.setLayoutParams(params);

                ImageButton commentImageButton = new ImageButton(getApplicationContext()); // Declare imageButton
                commentImageButton.setImageResource(R.drawable.ic_comment_black_48px); // Change image of commentImageButton
                commentImageButton.setBackgroundColor(getResources().getColor(R.color.transparent)); // Change background of commentImageButton to transparent

                cardView.setCardBackgroundColor(getResources().getColor(listColorBackground.get(moodLevel))); // change background of cardView


                commentImageButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast msg = Toast.makeText(HistoryActivity.this, comment, Toast.LENGTH_SHORT);
                        msg.show();
                    }
                });
                cardView.addView(mHistoryBlock); // to add mHistoryBlock to the cardView
                cardView.addView(commentImageButton); // to add commentImageButton to the cardView
                layoutHistory.addView(cardView); // to add cardView to the view (layoutHistory)
            }
        });
    }

    // method init to obtain the width and the height of the layout
    protected void init() {
        a = layoutHistory.getHeight(); // to obtain height
        b = layoutHistory.getWidth();  // to obtain width
    }

}
