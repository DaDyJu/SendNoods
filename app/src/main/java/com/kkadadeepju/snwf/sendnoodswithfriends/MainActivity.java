package com.kkadadeepju.snwf.sendnoodswithfriends;

import android.content.Intent;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    private Button findGame;
    private ArrayList<ImageView> figureImgs = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findGame = (Button) findViewById(R.id.find_game_btn);
        figureImgs.add((ImageView) findViewById(R.id.figure_one));
        figureImgs.add((ImageView) findViewById(R.id.figure_two));
        figureImgs.add((ImageView) findViewById(R.id.figure_three));
        figureImgs.add((ImageView) findViewById(R.id.figure_four));
        figureImgs.add((ImageView) findViewById(R.id.figure_five));
        figureImgs.add((ImageView) findViewById(R.id.figure_six));
        final Animation shake = AnimationUtils.loadAnimation(this, R.anim.toggle);

        findGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: send request to the server
//                for (ImageView img : figureImgs) {
//                    img.startAnimation(shake);
//                }
                Intent myIntent = new Intent(MainActivity.this, GameActivity.class);
                startActivity(myIntent);

//                final Handler handler = new Handler();
//                handler.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        Intent myIntent = new Intent(MainActivity.this, GameActivity.class);
//                        startActivity(myIntent);
//                    }
//                }, 3000);
            }
        });
        // TODO: show acknowledge dialog when game is ready to enter
    }
}
