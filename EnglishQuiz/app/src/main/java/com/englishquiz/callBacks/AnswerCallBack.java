package com.englishquiz.callBacks;
import com.englishquiz.model.Answer;
import java.util.HashMap;

public interface AnswerCallBack {
    public void onCallbackAnswer(HashMap<String, Answer> value);
}
