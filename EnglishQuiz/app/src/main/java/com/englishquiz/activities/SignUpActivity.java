package com.englishquiz.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.englishquiz.DAO.UserDAO;
import com.englishquiz.R;
import com.englishquiz.callBacks.UserCallBack;
import com.englishquiz.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpActivity extends AppCompatActivity {

    Button btn;
    TextView txtHaveAnAccount;
    EditText edtEmail, edtPassword, edtRePassword;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // taking FirebaseAuth instance
        mAuth = FirebaseAuth.getInstance();


        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        edtRePassword = findViewById(R.id.edtRePassword);


        btn = findViewById(R.id.btnSignUp);
        Intent i = new Intent(this, SignInActivity.class);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerNewUser();
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



    private void registerNewUser()
    {
        String email, password, rePassword;
        email = edtEmail.getText().toString();
        password = edtPassword.getText().toString();
        rePassword = edtRePassword.getText().toString();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(),
                    "Please enter email!!",
                    Toast.LENGTH_LONG)
                    .show();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            Toast.makeText(getApplicationContext(),
                    "Email incorrect format!!",
                    Toast.LENGTH_LONG)
                    .show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(),
                    "Please enter password!!",
                    Toast.LENGTH_LONG)
                    .show();
            return;
        }

        if(password.length() < 6){
            Toast.makeText(getApplicationContext(),
                    "Password must be at least 6 characters!!",
                    Toast.LENGTH_LONG)
                    .show();
            return;
        }
        if(TextUtils.isEmpty(rePassword)){
            Toast.makeText(getApplicationContext(),
                    "Please enter retype password!!",
                    Toast.LENGTH_LONG)
                    .show();
            return;
        }

        if(!rePassword.equals(password)){
            Toast.makeText(getApplicationContext(),
                    "Retype password must be same password!!",
                    Toast.LENGTH_LONG)
                    .show();
            return;
        }

        // create new user or register new user
        mAuth
                .createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(),
                                    "Registration successful!",
                                    Toast.LENGTH_LONG)
                                    .show();

                            // if the user created intent to login activity
                            Intent i = new Intent(getApplicationContext(), EditProfileActivity.class);
                            startActivity(i);
                        }
                        else {
                            // Registration failed
                            Toast.makeText(
                                    getApplicationContext(),
                                    "Registration failed!!"
                                            + " Please try again later",
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    }
                });
    }
}