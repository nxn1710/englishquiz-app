package com.englishquiz.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.englishquiz.DAO.BaseDAO;
import com.englishquiz.DAO.UserDAO;
import com.englishquiz.R;
import com.englishquiz.activities.QuizActivity;
import com.englishquiz.activities.SignInActivity;
import com.englishquiz.adapter.ViewPagerAdapterForQuestion;
import com.englishquiz.callBacks.BaseCallBack;
import com.englishquiz.callBacks.UserCallBack;
import com.englishquiz.databinding.FragmentHomeBinding;
import com.englishquiz.model.Base;
import com.englishquiz.model.User;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    UserDAO userDao = new UserDAO();
    BaseDAO baseDAO = new BaseDAO();
    List<Base> baseList = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        try {
            userDao.getUser(new UserCallBack() {
                @Override
                public void onCallbackUser(User user) {
                            binding.txtHi.setText("Hi " + user.getFirst_name());
                            binding.score.setText(user.getScore_max());
                }
            });
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        baseDAO.getBases(new BaseCallBack() {
            @Override
            public void onCallBackBases(List<Base> base) {
                baseList = base;
            }
        });


        Intent i = new Intent(getContext(), QuizActivity.class);
        binding.btnTakeTheTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(i);
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}