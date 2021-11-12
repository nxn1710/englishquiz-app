package com.englishquiz.ui.ranking;

import android.content.Context;
import android.os.Bundle;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.englishquiz.DAO.RankingDAO;
import com.englishquiz.DAO.UserDAO;
import com.englishquiz.R;
import com.englishquiz.adapter.ViewPagerAdapterForQuestion;
import com.englishquiz.callBacks.RankingCallBack;
import com.englishquiz.callBacks.UsersCallBack;
import com.englishquiz.databinding.FragmentRankingBinding;
import com.englishquiz.model.Ranking;
import com.englishquiz.model.User;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RankingFragment extends Fragment {

    private FragmentRankingBinding binding;

    private RankingCallBack rankingCallBack;

    private RecyclerView rankingView;

    List<Ranking> rankingList = new ArrayList<>();
    List<User> userList = new ArrayList<>();


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentRankingBinding.inflate(inflater, container, false);

        Context context = getContext();

        View root = binding.getRoot();
        rankingView = root.findViewById(R.id.rankingTopView);

        RankingDAO rankingDAO = new RankingDAO();
        UserDAO userDAO = new UserDAO();


        try {
            userDAO.getUsers(new UsersCallBack() {
                @Override
                public void onCallbackUsers(List<User> user) {
                    userList = user;
                }
            });
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            rankingDAO.getTop8(new RankingCallBack() {
                @Override
                public void onGetTop8(List<Ranking> rankingResponse) {

                    for (Ranking ranking : rankingResponse) {
                        if (ranking.getUser().equals(FirebaseAuth.getInstance().getCurrentUser().getDisplayName())) {
                            binding.userPosition.setText("" + ranking.getPosition());
                            binding.userScore.setText("" + ranking.getScore() + "pt");
                            binding.userName.setText("" + getFullName(ranking.getUser()));
                        }
                        Log.e("RANKING", ranking.toString() + "");
                    }

                    bindRankingTop1(rankingResponse.get(0));
                    bindRankingTop2(rankingResponse.get(1));
                    bindRankingTop3(rankingResponse.get(2));
                    rankingResponse.remove(0);
                    rankingResponse.remove(0);
                    rankingResponse.remove(0);

                    if (rankingResponse.size() > 5) {
                        rankingView.setAdapter(new RankingAdapter(context, rankingResponse.subList(0, 5), userList ));
                    } else {
                        rankingView.setAdapter(new RankingAdapter(context, rankingResponse.subList(0, rankingResponse.size()), userList));
                    }
                    rankingView.setLayoutManager(new LinearLayoutManager(context));

                }

            });

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private String getFullName(String userId) {
        String fullName = "";
        for (User user : userList) {
            if (user.getId().equals(userId)) {
                return user.getUsername();
            }
        }
        return fullName;
    }

    private void bindRankingTop1(Ranking ranking) {
        binding.top1Name.setText("" + getFullName(ranking.getUser()));
        binding.top1Score.setText("" + ranking.getScore() + "pt");
    }

    private void bindRankingTop2(Ranking ranking) {
        binding.top2Name.setText("" + getFullName(ranking.getUser()));
        binding.top2Score.setText("" + ranking.getScore() + "pt");
    }

    private void bindRankingTop3(Ranking ranking) {
        binding.top3Name.setText("" + getFullName(ranking.getUser()));
        binding.top3Score.setText("" + ranking.getScore() + "pt");
    }


}