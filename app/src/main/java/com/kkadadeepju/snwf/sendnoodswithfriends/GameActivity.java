package com.kkadadeepju.snwf.sendnoodswithfriends;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.kkadadeepju.snwf.sendnoodswithfriends.widget.BowlImageView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by jzhou on 2017-04-20.
 */

public class GameActivity extends AppCompatActivity {
    private TextView timer;
    private TextView playerTwoScore;
    private TextView playerThreeScore;
    private TextView playerFourScore;
    private TextView playerScore;
    private ImageView noodleBowl;
    private ImageView chopStickUp;
    private ImageView chopStickDown;

    private LinearLayout finishedBowlContainer;
    private BowlImageView finishedBowl;

    private int score = 0;

    private int imgPosition = 0;

    private boolean isChopstickUp = false;

    private Drawable mBowlStack;

    private static final int[] bowls = {
            R.drawable.noods_01,
            R.drawable.noods_02,
            R.drawable.noods_03,
            R.drawable.noods_04,
            R.drawable.noods_05,
            R.drawable.noods_06,
            R.drawable.noods_07,
            R.drawable.noods_08,
            R.drawable.noods_09
    };

    private ArrayList<Drawable> images = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_activity);
        timer = (TextView) findViewById(R.id.timer);
        playerTwoScore = (TextView) findViewById(R.id.player_two_score);
        playerThreeScore = (TextView) findViewById(R.id.player_three_score);
        playerFourScore = (TextView) findViewById(R.id.player_four_score);
        playerScore = (TextView) findViewById(R.id.player_score);

        chopStickUp = (ImageView) findViewById(R.id.chopstick_up);
        chopStickDown = (ImageView) findViewById(R.id.chopstick_down);
        noodleBowl = (ImageView) findViewById(R.id.noodle_bowl);


        mBowlStack = ContextCompat.getDrawable(getApplicationContext(), R.drawable.noods_10);

        final float imgSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, getResources().getDisplayMetrics());

        finishedBowlContainer = (LinearLayout) findViewById(R.id.finished_bowl);


        for (int i = 0; i < bowls.length; i++) {
            images.add(ContextCompat.getDrawable(getApplicationContext(), bowls[i]));
        }


        final Animation pulse = AnimationUtils.loadAnimation(this, R.anim.pulse);

        final LinearLayout.LayoutParams bowlStackLayout = new LinearLayout.LayoutParams((int) imgSize, (int) imgSize);
        bowlStackLayout.topMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, -15, getResources().getDisplayMetrics());

        noodleBowl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playerScore.setText(Integer.toString(++score));
                playerScore.startAnimation(pulse);

                if (imgPosition == 9) {
                    imgPosition = 0;
                    finishedBowl = new BowlImageView(GameActivity.this);
                    finishedBowl.setImageDrawable(mBowlStack);

                    finishedBowl.setLayoutParams(bowlStackLayout);
                    finishedBowlContainer.addView(finishedBowl, 0);
                }
                noodleBowl.setImageDrawable(images.get(imgPosition));
                imgPosition++;

                if (isChopstickUp) {
                    chopStickUp.setVisibility(View.GONE);
                    chopStickDown.setVisibility(View.VISIBLE);
                    isChopstickUp = false;
                } else {
                    chopStickUp.setVisibility(View.VISIBLE);
                    chopStickDown.setVisibility(View.GONE);
                    isChopstickUp = true;
                }
            }
        });


        new CountDownTimer(20000, 1000) {

            public void onTick(long millisUntilFinished) {
                timer.setText("seconds remaining: " + millisUntilFinished / 1000);
            }

            public void onFinish() {
                timer.setText("done!");
                noodleBowl.setClickable(false);
            }
        }.start();
    }

}
