package com.omdhanwant.youstream.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.omdhanwant.youstream.R;

public class SplashScreen extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
        int TIME_OUT = 3000;
        new Handler().postDelayed(() -> {
            Intent homeActivity = new Intent(SplashScreen.this, HomeScreen.class);
            startActivity(homeActivity);
            finish();
        }, TIME_OUT);
    }
}
