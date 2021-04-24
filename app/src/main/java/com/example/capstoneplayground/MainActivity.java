package com.example.capstoneplayground;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Button mAnimatedCircle;

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
    }
}