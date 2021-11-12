package com.englishquiz.ui.profile;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.englishquiz.DAO.UserDAO;
import com.englishquiz.activities.AboutUsActivity;
import com.englishquiz.activities.EditProfileActivity;
import com.englishquiz.activities.SignInActivity;
import com.englishquiz.adapter.ViewPagerAdapterForQuestion;
import com.englishquiz.callBacks.UserCallBack;
import com.englishquiz.databinding.FragmentProfileBinding;
import com.englishquiz.model.User;
import com.google.firebase.auth.FirebaseAuth;

public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;
    UserDAO userDao = new UserDAO();


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Log.e("TAG", "onCallbackUser: 12");
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        Intent i1 = new Intent(getContext(), EditProfileActivity.class);
        binding.btnEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(i1);
            }
        });
        Intent i2 = new Intent(getContext(), AboutUsActivity.class);
        binding.btnAboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(i2);
            }
        });
        Intent i3 = new Intent(getContext(), SignInActivity.class);
        binding.btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(i3);
            }
        });

        return root;
    }


    @Override
    public void onResume() {
        super.onResume();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    userDao.getUser(new UserCallBack() {
                        @Override
                        public void onCallbackUser(User user) {
                            binding.txtFullname.setText(user.getLast_name() + " " + user.getFirst_name());
                            binding.txtEmail.setText(user.getMail());
                            binding.txtUsername.setText(user.getUsername());
                            binding.txtAccount.setText(user.getUsername());
                            binding.txtFullname5.setText(user.getNational());
                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, 50);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}