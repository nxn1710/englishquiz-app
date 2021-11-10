package com.englishquiz.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.englishquiz.DAO.Answer_DoneDAO;
import com.englishquiz.DAO.Pre_ExerciseDAO;
import com.englishquiz.R;
import com.englishquiz.model.Answer_done;
import com.englishquiz.model.Pre_Exercise;

public class SignUpActivity extends AppCompatActivity {

    Button btn;
    TextView txtHaveAnAccount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        btn = findViewById(R.id.btnSignUp);
        Intent i = new Intent(this, SignInActivity.class);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(i);
            }
        });

        txtHaveAnAccount = findViewById(R.id.txtHaveAccount);
        Intent iLogin = new Intent(this, SignInActivity.class);
        txtHaveAnAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(iLogin);
            }
        });
    }
}