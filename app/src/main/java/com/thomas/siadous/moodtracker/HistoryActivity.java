package com.thomas.siadous.moodtracker;

// IMPORTS
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity  {

    private LinearLayout layoutHistory; // state LinearLayout

    int a = 0; // height of screen
    int b = 0; // width of screen
    ArrayList<Integer> listColorBackground = new ArrayList<>(5); // state arrayList who will store the five color

    private SharedPreferences mPreference;
    String history; // to recover data in preferences
    int mMoodLevel = 0; // initialize moodLevel to 0
    String mComment = ""; //to store comment
    int dayNumber = 0; // day number in history

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        System.out.println("HEY ! HISTORY ACTIVITY : ON CREATE LAUNCHED !");
        this.layoutHistory = findViewById(R.id.MyLayoutHistory);  // Reference layoutHistory

        mPreference = getSharedPreferences(MainActivity.PREFERENCE_FILE, MODE_PRIVATE);
        addColorToListColorBackground();
        history = mPreference.getString(MainActivity.PREF_KEY, "Nothing");

        if (!(history.equals("Nothing"))) {
            callCreateCardViewAccordingDaysNumber();
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

    private void callCreateCardViewAccordingDaysNumber() {
        String daysData[] = history.split("/");
        int partNumber = daysData.length;
        dayNumber = partNumber - 1;

        for (int i = 1; i < partNumber; i++) {
            String threeDataOfOneDay[] = daysData[i].split(",");
            mMoodLevel = Integer.parseInt(threeDataOfOneDay[1]); // recover moodLevel
            mComment = threeDataOfOneDay[2]; // recover comment
            createCardView(mMoodLevel, mComment, dayNumber);
            dayNumber--;
        }
    }

        //method to create a card view for the moodLevel and comment
        private void createCardView(final int moodLevel, final String comment, final int dayNumber) {
        ViewTreeObserver observer = layoutHistory.getViewTreeObserver();
        observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                init(); // call method init to calculate the size of the screen
                layoutHistory.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                CardView cardView = new CardView(getApplicationContext()); // Initialize CardView

                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams((int) ((moodLevel + 1) * 0.2 * b), (int) (0.143 * a));
                cardView.setLayoutParams(params);

                TextView text = new TextView(getApplicationContext()); // Initialize textView
                displayDaysAgo(params, text, dayNumber);

                manageComment(moodLevel, comment, cardView);

                // change background of cardView
                cardView.setCardBackgroundColor(getResources().getColor(listColorBackground.get(moodLevel)));
                cardView.addView(text); // to add mHistoryBlock to the cardView


                layoutHistory.addView(cardView); // to add cardView to the view (layoutHistory)
             }
          });
        }

    private void manageComment(int moodLevel, String comment, CardView cardView) {
        // Initialize imageButton
        ImageButton commentImageButton = new ImageButton(getApplicationContext());
        // Change image of commentImageButton
        commentImageButton.setImageResource(R.drawable.ic_comment_black_48px);
        // Change background of commentImageButton to transparent
        commentImageButton.setBackgroundColor(getResources().getColor(R.color.transparent));
        positionImageOfMessageOnRight(moodLevel, commentImageButton);
        displayCommentOnToast(comment, cardView, commentImageButton);
    }

    private void displayCommentOnToast(final String comment, CardView cardView, ImageButton commentImageButton) {
        if (!(comment.equals(" "))) {
            commentImageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast msg = Toast.makeText(HistoryActivity.this, comment, Toast.LENGTH_SHORT);
                    msg.show();
                }
            });
            cardView.addView(commentImageButton); // to add commentImageButton to the cardView
        }
    }

    private void displayDaysAgo(LinearLayout.LayoutParams params, TextView text, int dayNumber) {
        text.setLayoutParams(params);
        if (dayNumber > 2 && dayNumber < 8) {
            text.setText("Il y a " + dayNumber + " jours");
        }
        else {
            if (dayNumber == 2) {
                text.setText("Avant-hier");
            }
            else if (dayNumber == 1) {
                text.setText("Hier");
            }
        }
        text.setTextColor(getResources().getColor(R.color.black_background_cardView_text));
    }

    private void positionImageOfMessageOnRight(int moodLevel, ImageButton commentImageButton) {
        switch (moodLevel) {
            case 0 :
                commentImageButton.setX((float) ((b * 0.2) - (b * 0.210)));
                break;
            case 1 :
                commentImageButton.setX((float) ((b * 0.4) - (b * 0.310)));
                break;
            case 2 :
                commentImageButton.setX((float) ((b * 0.6) - (b * 0.420)));
                break;
            case 3 :
                commentImageButton.setX((float) ((b * 0.8) - (b * 0.520)));
                break;
            case 4 :
                commentImageButton.setX((float) (b - (b * 0.625)));
                break;
        }
    }

    // method init to obtain the width and the height of the layout
    protected void init() {
        a = layoutHistory.getHeight(); // to obtain height
        b = layoutHistory.getWidth();  // to obtain width
    }

    @Override
    protected void onStart() {
        super.onStart();
        System.out.println("HEY ! HISTORY ACTIVITY : ON START LAUNCHED !");
    }

    @Override
    protected void onResume() {
        super.onResume();
        System.out.println("HEY ! HISTORY ACTIVITY : ON RESUME LAUNCHED !");
    }

    @Override
    protected void onPause() {
        super.onPause();
        System.out.println("HEY ! HISTORY ACTIVITY : ON PAUSE LAUNCHED !");
    }

    @Override
    protected void onStop() {
        super.onStop();
        System.out.println("HEY ! HISTORY ACTIVITY : ON STOP LAUNCHED !");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.out.println("HEY ! HISTORY ACTIVITY : ON DESTROY LAUNCHED !");
    }

}
