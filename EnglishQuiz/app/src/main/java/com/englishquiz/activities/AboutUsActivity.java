package com.englishquiz.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.englishquiz.R;
import com.englishquiz.databinding.FragmentProfileBinding;
import com.englishquiz.ui.profile.ProfileFragment;

public class AboutUsActivity extends AppCompatActivity {
    ImageView btn;

    TextView txtAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        btn = findViewById(R.id.btnBack);
        txtAccount = findViewById(R.id.txtAccount);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String userName = extras.getString("userName");
            txtAccount.setText(userName);
        }
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }


}