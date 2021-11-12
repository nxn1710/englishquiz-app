package com.englishquiz.callBacks;

import com.englishquiz.model.Exercise;
import com.englishquiz.model.Ranking;
import com.englishquiz.model.User;

import java.util.HashMap;
import java.util.List;

public interface RankingCallBack {
    void onGetTop8(List<Ranking> value);
}
