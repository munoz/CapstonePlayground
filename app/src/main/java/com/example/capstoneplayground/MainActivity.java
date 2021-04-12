package com.example.capstoneplayground;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button mAnimatedCircle;
    private Button mProgressBar;
    private Button mPing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        wireupDisplay();
    }


    private void wireupDisplay() {
        mAnimatedCircle = findViewById(R.id.buttonAnimatedCircle);
        mAnimatedCircle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = AnimatedCircleActivity.intentFactory(getApplicationContext());
                startActivity(intent);
            }
        });
        mProgressBar = findViewById(R.id.progbar);
        mProgressBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = ProgressBarActivity.intentFactory(getApplicationContext());
                startActivity(intent);
            }
        });
        mPing = findViewById(R.id.Ping);
        mPing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
    }
    public static Intent intentFactory(Context context) {
        Intent intent = new Intent(context, MainActivity.class);

        return intent;
    }
}