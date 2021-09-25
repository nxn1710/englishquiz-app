package com.englishquiz.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.englishquiz.MainActivity;
import com.englishquiz.R;

public class QuizResultActivity extends AppCompatActivity {
    Button btn1, btn2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_result);
        btn1 = findViewById(R.id.button6);
        btn2 = findViewById(R.id.button5);
        Intent i1 = new Intent(this, ReviewActivity.class);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(i1);
            }
        });
        Intent i2 = new Intent(this, MainActivity.class);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(i2);
            }
        });
    }
}