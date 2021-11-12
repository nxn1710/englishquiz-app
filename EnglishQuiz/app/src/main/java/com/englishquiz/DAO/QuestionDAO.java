package com.englishquiz.DAO;

import android.util.Log;

import com.englishquiz.callBacks.ExerciseCallBack;
import com.englishquiz.callBacks.QuestionCallBack;
import com.englishquiz.model.Exercise;
import com.englishquiz.model.Question;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class QuestionDAO {
    public QuestionDAO() {
    }
    HashMap<String, Question> questionHashMap = new HashMap<>();
    public void getQuestion( QuestionCallBack myCallback) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Master");
        myRef.child("Question").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String id = snapshot.child("id").getValue().toString();
                    String title = snapshot.child("exercise").getValue().toString();
                    String description = snapshot.child("description").getValue().toString();
                    String time = snapshot.child("time").getValue().toString();
                    Question e = new Question(id, title, description, time);
                    questionHashMap.put(id,e);
                }
                myCallback.onCallbackQuestion(questionHashMap);
            }

            @Override
            public void onCancelled(DatabaseError error) {
            }
        });
    }
}
