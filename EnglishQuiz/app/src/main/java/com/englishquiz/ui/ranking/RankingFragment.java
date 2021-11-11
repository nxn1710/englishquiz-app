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
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.englishquiz.DAO.RankingDAO;
import com.englishquiz.R;
import com.englishquiz.adapter.ViewPagerAdapterForQuestion;
import com.englishquiz.callBacks.RankingCallBack;
import com.englishquiz.databinding.FragmentRankingBinding;
import com.englishquiz.model.Ranking;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RankingFragment extends Fragment {

    private FragmentRankingBinding binding;

    private RankingCallBack rankingCallBack;

    private RecyclerView rankingView;

    List<Ranking> rankingResponse = new ArrayList<>();
    Ranking userRanking;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentRankingBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        rankingView = getView().findViewById(R.id.rankingTopView);

        RankingDAO rankingDAO = new RankingDAO();
        try {
            rankingDAO.getRanking(new RankingCallBack() {
                @Override
                public void onCallbackRanking(HashMap<String, Ranking> value) {

                    for (String key: value.keySet()) {

                        if (value.get(key).getUser().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
                            userRanking = value.get(key);
                        }

                        rankingResponse.add(value.get(key));

                        Log.e("onCallbackRanking", value.get(key).toString() );

                        if (rankingResponse.size() == 8) {
                            break;
                        }
                    }

                    RankingAdapter rankingAdapter = new RankingAdapter(getActivity().getApplicationContext(),rankingResponse);
                    rankingView.setAdapter(rankingAdapter);
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
}