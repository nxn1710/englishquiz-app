package com.englishquiz.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.englishquiz.DAO.UserDAO;
import com.englishquiz.MainActivity;
import com.englishquiz.R;
import com.englishquiz.adapter.ViewPagerAdapterForQuestion;
import com.englishquiz.callBacks.UserCallBack;
import com.englishquiz.databinding.FragmentProfileBinding;
import com.englishquiz.model.User;
import com.englishquiz.ui.profile.ProfileFragment;

import java.util.Objects;

public class EditProfileActivity extends AppCompatActivity {
    ImageView btn;
    Button btnFinish;
    EditText editTxtUsername, editTxtFirstname, editTxtLastname, editTxtNational, editTxtCareer;

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
                        editTxtUsername.setText(user.getUsername());
                        editTxtFirstname.setText(user.getFirst_name());
                        editTxtLastname.setText(user.getLast_name());
                        editTxtNational.setText(user.getNational());
                        editTxtCareer.setText(user.getCareer());
                        flag = true;
                    } else {
                        flag = false;
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

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
                    onBackPressed();
                }

                Toast.makeText(getApplicationContext(),
                        "Update profile success!!",
                        Toast.LENGTH_LONG)
                        .show();
            }
        });
    }
}