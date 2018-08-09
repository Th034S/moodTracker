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
import java.util.Arrays;
import java.util.StringTokenizer;

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
        final int mMoodLevel = mPreference.getInt(MainActivity.PREF_KEY_MOOD_LEVEL,3);
        final ArrayList<Integer> mMoodLevel1 = new ArrayList<>();
        mMoodLevel1.add(mMoodLevel);     // to store moodLevel
        final String mComment = mPreference.getString(MainActivity.PREF_KEY_COMMENT, "Aucun commentaire"); //to store comment

        addColorToListColorBackground();

        for(int i=0; i<mMoodLevel1.size(); i++) {
            System.out.println(mMoodLevel1.get(i));
        }

        ViewTreeObserver observer = layoutHistory.getViewTreeObserver();
        observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                    init(); // call method init to calculate the size of the screen
                    layoutHistory.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    CardView cardView = new CardView(getApplicationContext()); // Declare CardView

                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams((int) ((mMoodLevel + 1) * 0.2 * b), (int) (0.143 * a));

                    cardView.setLayoutParams(params);

                    TextView mHistoryBlock = new TextView(getApplicationContext()); // Declare textView
                    mHistoryBlock.setLayoutParams(params);

                    ImageButton commentImageButton = new ImageButton(getApplicationContext()); // Declare imageButton
                    commentImageButton.setImageResource(R.drawable.ic_comment_black_48px); // Change image of commentImageButton
                    commentImageButton.setBackgroundColor(getResources().getColor(R.color.transparent)); // Change background of commentImageButton to transparent

                    cardView.setCardBackgroundColor(getResources().getColor(listColorBackground.get(mMoodLevel))); // change background of cardView


                    commentImageButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Toast msg = Toast.makeText(HistoryActivity.this, mComment, Toast.LENGTH_SHORT);
                                msg.show();
                            }
                        });
                    cardView.addView(mHistoryBlock); // to add mHistoryBlock to the cardView
                    cardView.addView(commentImageButton); // to add commentImageButton to the cardView
                    layoutHistory.addView(cardView); // to add cardView to the view (layoutHistory)
            }
        });

    }
    // Method to add color to the listColorBackground
    private void addColorToListColorBackground() {
        listColorBackground.add(0, R.color.faded_red);
        listColorBackground.add(1, R.color.warm_grey);
        listColorBackground.add(2, R.color.cornflower_blue_65);
        listColorBackground.add(3, R.color.light_sage);
        listColorBackground.add(4, R.color.banana_yellow);
    }

    // method init to obtain the width and the height of the layout
    protected void init() {
        a = layoutHistory.getHeight(); // to obtain height
        b = layoutHistory.getWidth();  // to obtain width
    }

}
