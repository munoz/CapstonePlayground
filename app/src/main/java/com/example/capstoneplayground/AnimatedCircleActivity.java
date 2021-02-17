package com.example.capstoneplayground;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class AnimatedCircleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animated_circle);
    }

    public static Intent intentFactory(Context context) {
        Intent intent = new Intent(context, AnimatedCircleActivity.class);

        return intent;
    }
}