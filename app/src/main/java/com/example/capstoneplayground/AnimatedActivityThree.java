package com.example.capstoneplayground;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

;


public class AnimatedActivityThree extends AppCompatActivity {

    ProgressBar prog_ping;
    ProgressBar prog_down;
    ProgressBar prog_up;
    TextView curTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animated_three);

        prog_ping = findViewById(R.id.progress_bar_ping);
        prog_down = findViewById(R.id.progress_bar_down);
        prog_up = findViewById(R.id.progress_bar_up);
        curTest = findViewById(R.id.text_view_progress);
        Button activityFourButton = findViewById(R.id.activityfourbtn);
        Button up = findViewById(R.id.button_incr);
        up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateProgressBar();
            }
        });

        activityFourButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AnimatedActivityThree.this, AnimatedActivityFour.class);
                startActivity(intent);
            }
        });


    }

    public static Intent intentFactory(Context context) {
        Intent intent = new Intent(context, AnimatedCircleActivity.class);

        return intent;
    }

    private void updateProgressBar() {

        if(prog_ping.getProgress() != 100) {
            curTest.setText("Ping");
            prog_ping.setProgress(prog_ping.getProgress() + 25);
            return;
        }
        if(prog_down.getProgress() != 100) {
            curTest.setText("Download");
            prog_down.setProgress(prog_down.getProgress() + 25);
            return;
        }
        if(prog_up.getProgress() != 100) {
            curTest.setText("Upload");
            prog_up.setProgress(prog_up.getProgress() + 25);
            return;
        }
        if(prog_up.getProgress() == 100) {
            curTest.setText("Complete");
        }

    }
}