package com.englishquiz.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.englishquiz.DAO.BaseDAO;
import com.englishquiz.DAO.UserDAO;
import com.englishquiz.activities.QuizActivity;
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

                    baseDAO.getBases(new BaseCallBack() {
                        @Override
                        public void onCallBackBases(List<Base> base) {
                            String Level = "No qualification";
                            String cer = "";
                            String ielts = "";
                            for (int i = 0; i < base.size(); i++) {
                                if (Integer.parseInt(user.getScore_max()) > Integer.parseInt(base.get(i).getScore_min()) - 1 &&
                                        Integer.parseInt(user.getScore_max()) <= Integer.parseInt(base.get(i).getScore_max())) {
                                    Level = base.get(i).getCertificate();
                                    cer = base.get(i).getType();
                                    ielts = base.get(i).getIelts();
                                }
                            }
                            if (Integer.parseInt(user.getScore_max()) == 0) {
                                binding.txtCer.setVisibility(View.INVISIBLE);
                                binding.txtIelts.setVisibility(View.INVISIBLE);
                            } else {
                                binding.txtCer.setVisibility(View.VISIBLE);
                                binding.txtIelts.setVisibility(View.VISIBLE);
                                binding.txtCer.setText(cer);
                                binding.txtIelts.setText(ielts);
                            }
                            binding.txtLevel.setText(Level);
                        }
                    });
                }
            });
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

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