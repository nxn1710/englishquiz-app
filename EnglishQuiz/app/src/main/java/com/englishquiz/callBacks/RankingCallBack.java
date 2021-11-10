package com.englishquiz.callBacks;

import com.englishquiz.model.Exercise;
import com.englishquiz.model.Ranking;
import com.englishquiz.model.User;

import java.util.HashMap;

public interface RankingCallBack {
    void onCallbackRanking(HashMap<String, Ranking> value);
}
