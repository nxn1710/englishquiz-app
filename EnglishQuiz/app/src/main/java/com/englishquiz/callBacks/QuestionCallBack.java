package com.englishquiz.callBacks;

import com.englishquiz.model.Question;

import java.util.HashMap;

public interface QuestionCallBack {
    public void onCallbackQuestion(HashMap<String, Question> value);
}
