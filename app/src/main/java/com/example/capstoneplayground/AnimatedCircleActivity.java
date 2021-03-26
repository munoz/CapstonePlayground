package com.example.capstoneplayground;





import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;;import com.google.android.material.button.MaterialButton;


public class AnimatedCircleActivity extends AppCompatActivity {


    ProgressBar prog_ping;
    ProgressBar prog_jitter;
    ProgressBar prog_down;
    ProgressBar prog_up;
    TextView curTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animated_circle);

        prog_ping = findViewById(R.id.progress_bar_ping);
        prog_jitter = findViewById(R.id.progress_bar_jitter);
        prog_down = findViewById(R.id.progress_bar_down);
        prog_up = findViewById(R.id.progress_bar_up);
        curTest = findViewById(R.id.text_view_progress);

        Button up = findViewById(R.id.button_incr);
        up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateProgressBar();
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
        if(prog_jitter.getProgress() != 100) {
            curTest.setText("Jitter");
            prog_jitter.setProgress(prog_jitter.getProgress() + 25);
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