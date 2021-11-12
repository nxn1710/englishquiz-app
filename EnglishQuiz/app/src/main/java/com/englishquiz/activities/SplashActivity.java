package com.englishquiz.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ProgressBar;

import com.englishquiz.MainActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.englishquiz.R;

public class SplashActivity extends AppCompatActivity {

    ProgressBar bar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        bar = findViewById(R.id.progressBar);
        bar.setVisibility(View.VISIBLE);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                bar.setVisibility(View.GONE);
            }
        }, 200);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        Intent i;
        if (mAuth.getCurrentUser() == null){
            i = new Intent(this, SignInActivity.class);
        }else{
            i = new Intent(this, MainActivity.class);
        }

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(i);
                finish();
            }
        }, 500);
    }
}