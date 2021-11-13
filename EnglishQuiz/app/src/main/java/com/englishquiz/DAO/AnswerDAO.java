package com.englishquiz.DAO;

import android.util.Log;

import com.englishquiz.callBacks.AnswerCallBack;
import com.englishquiz.callBacks.ExerciseCallBack;
import com.englishquiz.constant.Constant;
import com.englishquiz.model.Answer;
import com.englishquiz.model.Exercise;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class AnswerDAO {
    public AnswerDAO() {
    }
    HashMap<String, Answer> answers = new HashMap<>();
    public void getAnswer( AnswerCallBack myCallback) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(new Constant().DATABASE);
        myRef.child("Answer").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String id = snapshot.child("id").getValue().toString();
                    String question = snapshot.child("question").getValue().toString();
                    String description = snapshot.child("description").getValue().toString();
                    String is_correct = snapshot.child("is_correct").getValue().toString();
                    Answer e = new Answer(id, question, description, is_correct);
                    answers.put(id,e);
                }
                myCallback.onCallbackAnswer(answers);
            }

            @Override
            public void onCancelled(DatabaseError error) {
            }
        });
    }
}
