package com.englishquiz.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.englishquiz.DAO.UserDAO;
import com.englishquiz.R;
import com.englishquiz.callBacks.UserCallBack;
import com.englishquiz.model.User;

import java.util.Objects;

public class EditProfileActivity extends AppCompatActivity {
    ImageView btn;
    Button btnFinish;
    EditText editTxtUsername, editTxtFirstname, editTxtLastname, editTxtNational, editTxtCareer;
    User current_user = new User();
    UserDAO dao = new UserDAO();
    boolean flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        editTxtUsername = findViewById(R.id.editTxtUsername);
        editTxtFirstname = findViewById(R.id.editTxtFirstname);
        editTxtLastname = findViewById(R.id.editTxtLastname);
        editTxtNational = findViewById(R.id.editTxtNational);
        editTxtCareer = findViewById(R.id.editTxtCareer);
        btnFinish = findViewById(R.id.btnFinish);

        try {
            dao.getUser(new UserCallBack() {
                @Override
                public void onCallbackUser(User user) {
                    if (user != null) {
                        current_user = user;
                        flag = true;

                    } else {
                        flag = false;
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                editTxtUsername.setText(current_user.getUsername());
                editTxtFirstname.setText(current_user.getFirst_name());
                editTxtLastname.setText(current_user.getLast_name());
                editTxtNational.setText(current_user.getNational());
                editTxtCareer.setText(current_user.getCareer());
                if(!TextUtils.isEmpty(editTxtUsername.getText().toString())){
                    editTxtUsername.setSelection(editTxtUsername.getText().toString().length());
                }
            }
        }, 100);

        btn = findViewById(R.id.btnBack);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(editTxtUsername.getText().toString()) || TextUtils.isEmpty(editTxtFirstname.getText().toString())
                        || TextUtils.isEmpty(editTxtLastname.getText().toString()) || TextUtils.isEmpty(editTxtNational.getText().toString())
                        || TextUtils.isEmpty(editTxtCareer.getText().toString())) {
                    Toast.makeText(getApplicationContext(),
                            "Please don't leave the field blank!",
                            Toast.LENGTH_LONG)
                            .show();
                    return;
                }

                if (flag == false) {
                    dao.addUserProfileFirstTime(new User(editTxtUsername.getText().toString(),
                            editTxtFirstname.getText().toString(),
                            editTxtLastname.getText().toString(), editTxtNational.getText().toString(),
                            editTxtCareer.getText().toString()));
                } else {

                    try {
                        dao.getUser(new UserCallBack() {
                            @Override
                            public void onCallbackUser(User user) {
                                User newUser = new User(user.getId(), editTxtUsername.getText().toString(),
                                        user.getMail(), user.getPassword(), user.getScore_max(), editTxtFirstname.getText().toString(),
                                        editTxtLastname.getText().toString(), editTxtNational.getText().toString(),
                                        editTxtCareer.getText().toString());
                                dao.updateUser(newUser);
                            }
                        });
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                if (flag == false) {
                    Intent i = new Intent(getApplicationContext(), SignInActivity.class);
                    startActivity(i);
                } else {
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            onBackPressed();
                        }
                    }, 300);
                }

                Toast.makeText(getApplicationContext(),
                        "Update profile success!",
                        Toast.LENGTH_LONG)
                        .show();
            }
        });
    }
}