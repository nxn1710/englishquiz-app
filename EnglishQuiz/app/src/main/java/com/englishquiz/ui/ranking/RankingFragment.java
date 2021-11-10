package com.englishquiz.ui.ranking;

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

import com.englishquiz.DAO.RankingDAO;
import com.englishquiz.callBacks.RankingCallBack;
import com.englishquiz.databinding.FragmentRankingBinding;
import com.englishquiz.model.Ranking;

import java.util.HashMap;

public class RankingFragment extends Fragment {

    private FragmentRankingBinding binding;

    private RankingCallBack rankingCallBack;

    HashMap<String, Ranking> rankingHashMap = new HashMap<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentRankingBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        RankingDAO rankingDAO = new RankingDAO();
        try {
            rankingDAO.getRanking(new RankingCallBack() {
                @Override
                public void onCallbackRanking(HashMap<String, Ranking> value) {
                    rankingHashMap = value;
                    Log.e("E", " " + rankingHashMap.size());
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