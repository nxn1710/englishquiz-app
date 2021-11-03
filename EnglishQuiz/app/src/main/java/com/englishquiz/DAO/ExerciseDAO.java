package com.englishquiz.DAO;

import android.util.Log;

import com.englishquiz.model.Exercise;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.englishquiz.callBacks.ExerciseCallBack;

import java.util.HashMap;

public class ExerciseDAO {
    public ExerciseDAO() {
    }

    HashMap<String, Exercise> exercises = new HashMap<>();
    public void getExercise( ExerciseCallBack myCallback) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("TestWeb");
        myRef.child("Exercise").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.e("895","thuan4");
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String id = snapshot.child("id").getValue().toString();
                    String title = snapshot.child("title").getValue().toString();
                    String description = snapshot.child("description").getValue().toString();
                    String time = snapshot.child("time").getValue().toString();
                    Exercise e = new Exercise(id, title, description, time);
                    exercises.put(id,e);
                }
                myCallback.onCallbackExercise(exercises);
            }

            @Override
            public void onCancelled(DatabaseError error) {
            }
        });
    }
}
