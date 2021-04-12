package com.example.capstoneplayground;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ProgressBarActivity extends AppCompatActivity {


    private Button rAnimatedCircle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progresss_bar);

        wireupDisplay();
    }
    private void wireupDisplay() {

    }
    public static Intent intentFactory(Context context) {
        Intent intent = new Intent(context, ProgressBarActivity.class);

        return intent;
    }
}