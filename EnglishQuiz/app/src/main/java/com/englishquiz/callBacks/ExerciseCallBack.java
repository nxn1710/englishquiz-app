package com.englishquiz.callBacks;

import com.englishquiz.model.Exercise;

import java.util.HashMap;

public interface ExerciseCallBack {
    public void onCallbackExercise(HashMap<String, Exercise> value);
}
