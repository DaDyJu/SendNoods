package com.kkadadeepju.snwf.sendnoodswithfriends.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kkadadeepju.snwf.sendnoodswithfriends.GameActivity;
import com.kkadadeepju.snwf.sendnoodswithfriends.MainActivity;
import com.kkadadeepju.snwf.sendnoodswithfriends.NCUserPreference;
import com.kkadadeepju.snwf.sendnoodswithfriends.R;
import com.kkadadeepju.snwf.sendnoodswithfriends.model.UserInfo;
import com.kkadadeepju.snwf.sendnoodswithfriends.widget.BowlImageView;
import com.kkadadeepju.snwf.sendnoodswithfriends.widget.BowlResultImageView;
import com.kkadadeepju.snwf.sendnoodswithfriends.widget.BowlStackLayout;

import org.w3c.dom.Text;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.V;
import static com.kkadadeepju.snwf.sendnoodswithfriends.MainActivity.GAME_USERS;

/**
 * Created by jzhou on 2017-04-21.
 */

public class EndOfTurnDIalog extends Dialog {

    private LinearLayout playerOneBowlContainer;
    private LinearLayout playerBowlContainer;
    private TextView userWinLose;
    private TextView playerOneScore;
    private BowlResultImageView resultBowl;
    private Drawable mBowlStack;
    private Button rematchBtn;


    private Context context;
    private int playerBowls;
    private String curGameId;

    private DatabaseReference mDatabase;

    public EndOfTurnDIalog(final Context context, final int playerBowls, final String gameId) {
        super(context, R.style.Theme_Dialog_Fullscreen_Darkened);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.end_of_turn_dialog);
        this.context = context;
        this.playerBowls = playerBowls;
        curGameId = gameId;

        playerOneBowlContainer = (LinearLayout) findViewById(R.id.player_one_score);
        playerBowlContainer = (LinearLayout) findViewById(R.id.player_bowl_container);
        userWinLose = (TextView) findViewById(R.id.userWinLose);
        playerOneScore = (TextView) findViewById(R.id.player_one_bowls);
        rematchBtn = (Button) findViewById(R.id.rematch_bth);

        rematchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                Intent myIntent = new Intent(getContext(), MainActivity.class);
                getContext().startActivity(myIntent);
            }
        });

        if (playerBowls / 10 > 20) {
            userWinLose.setText("You Win!");
        } else {
            userWinLose.setText("Try Harder Text Time!");
        }

        playerOneScore.setText(String.format("Player 1 \n %s", Integer.toString(playerBowls)));
        setUpAnimation();
    }

    private void setUpAnimation() {
        final float imgSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, context.getResources().getDisplayMetrics());
        final LinearLayout.LayoutParams bowlStackLayout = new LinearLayout.LayoutParams((int) imgSize, (int) imgSize);
        bowlStackLayout.gravity = Gravity.CENTER;
        bowlStackLayout.topMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, -30, context.getResources().getDisplayMetrics());
        mBowlStack = ContextCompat.getDrawable(getContext().getApplicationContext(), R.drawable.noods_10);

        for (int i = 0; i < playerBowls / 10; i++) {
            resultBowl = new BowlResultImageView(getContext());
            resultBowl.setImageDrawable(mBowlStack);

            resultBowl.setLayoutParams(bowlStackLayout);
            playerOneBowlContainer.addView(resultBowl, 0);
        }

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Games").child(curGameId).child(GAME_USERS);
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    UserInfo userInfo = snapshot.getValue(UserInfo.class);

                    if (userInfo.getUserId().equals(NCUserPreference.getUserId(getContext()))) {
                        continue;
                    }

                    BowlStackLayout bowlStackLayoutContainer = new BowlStackLayout(getContext());

                    for (int i = 0; i < userInfo.getScore() / 10; i++) {
                        resultBowl = new BowlResultImageView(getContext());
                        resultBowl.setImageDrawable(mBowlStack);
                        resultBowl.setLayoutParams(bowlStackLayout);
                        bowlStackLayoutContainer.addView(resultBowl, 0);
                    }

                    playerBowlContainer.addView(bowlStackLayoutContainer);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

}
