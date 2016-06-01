package com.srinivas.notesapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.srinivas.notesapp.R;


public class SplashScreenActivity extends AppCompatActivity {

    private static final int SPLASH_WAIT_DELAY = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spashscreen);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent loginIntent = new Intent(SplashScreenActivity.this, MainActivity.class);
                startActivity(loginIntent);
                finish();
            }
        }, SPLASH_WAIT_DELAY);

    }


}
