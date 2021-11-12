package com.englishquiz.DAO;

import androidx.annotation.NonNull;

import com.englishquiz.callBacks.Pre_ExerciseCallBack;
import com.englishquiz.model.Answer_done;
import com.englishquiz.model.Pre_Exercise;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class Pre_ExerciseDAO {
    Boolean done=false;
    String UserID=FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
    public Pre_ExerciseDAO() {
    }
    HashMap<String, Pre_Exercise> exercises = new HashMap<>();
    public void getPre_Exercise( Pre_ExerciseCallBack myCallback) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("TestWeb");
        myRef.child("Pre_Exercise").child(UserID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                    String id = dataSnapshot.child("id").getValue().toString();
                    String exercise = dataSnapshot.child("exercise").getValue().toString();
                    String user = dataSnapshot.child("user").getValue().toString();
                    String score = dataSnapshot.child("score").getValue().toString();
                    Pre_Exercise e = new Pre_Exercise(id, exercise, user, score);
                    exercises.put(id,e);

                myCallback.onCallbackPre_Exercise(exercises);
            }

            @Override
            public void onCancelled(DatabaseError error) {
            }
        });
    }

    public void addPre_Exercise( Pre_Exercise ans){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("TestNgu");
        myRef.child("Pre_Exercise").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (!done)
                {
                    ans.setId(UserID);
                    myRef.child("Pre_Exercise").child(UserID).setValue(ans);
                    done=true;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}
