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
        System.out.println(" HEY ! HISTORY ACTIVITY : ON CREATE LAUNCHED");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        this.layoutHistory = findViewById(R.id.MyLayoutHistory);  // Reference layoutHistory

        Log.d("DEBUG", "On Create works");
        mPreference = getSharedPreferences(MainActivity.PREFERENCE_FILE, MODE_PRIVATE);
        addColorToListColorBackground();

        history = mPreference.getString(MainActivity.PREF_KEY, "Nothing");
        System.out.println(history + "///////////////////////////////////////////////////////////////////////////////////////////////////// ");

        if (!(history.equals("Nothing"))) {
        String historyPart[] = history.split("/");
        System.out.println(historyPart[1]);
        int partNumber = historyPart.length;
        System.out.println(historyPart.length + "   part NUMBER");
        dayNumber = partNumber - 1;

        for (int i = 1; i < partNumber; i++) {
            String part[] = historyPart[i].split(",");
            mMoodLevel = Integer.parseInt(part[1]);
            mComment = part[2];
            createCardView(mMoodLevel, mComment, dayNumber);
            dayNumber--;
          }
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
        private void createCardView(final int moodLevel, final String comment, final int dayNumber) {
        System.out.println("JE CREE UNE CARTE !!");
        ViewTreeObserver observer = layoutHistory.getViewTreeObserver();
        observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                init(); // call method init to calculate the size of the screen
                layoutHistory.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                CardView cardView = new CardView(getApplicationContext()); // Declare CardView
                System.out.println(mMoodLevel + " MOOD LEVEL IN CARDVIEW METHOD");
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams((int) ((moodLevel + 1) * 0.2 * b), (int) (0.143 * a));

                cardView.setLayoutParams(params);
                System.out.println("JE CREE UNE CARTE !!");

                TextView text = new TextView(getApplicationContext()); // Declare textView
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
                cardView.addView(text); // to add mHistoryBlock to the cardView
                if (!(comment.equals(" "))) {
                    cardView.addView(commentImageButton); // to add commentImageButton to the cardView
                }
                layoutHistory.addView(cardView); // to add cardView to the view (layoutHistory)
            }
        });
    }

    // method init to obtain the width and the height of the layout
    protected void init() {
        a = layoutHistory.getHeight(); // to obtain height
        b = layoutHistory.getWidth();  // to obtain width
    }

    @Override
    protected void onStart() {
        super.onStart();
        System.out.println("HEY ! MainActivity : ON START LAUNCHED !");
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
