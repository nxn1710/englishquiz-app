package com.englishquiz.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.englishquiz.MainActivity;
import com.englishquiz.R;

public class SignInActivity extends AppCompatActivity {
    Button btn;
    TextView txtRegisterNow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        btn = findViewById(R.id.btnSignIn);
        txtRegisterNow = findViewById(R.id.txtRegisterNow);
        Intent i = new Intent(this, MainActivity.class);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(i);
            }
        });

        Intent iRegisterNow = new Intent(this, SignUpActivity.class);
        txtRegisterNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(iRegisterNow);
            }
        });
    }
}