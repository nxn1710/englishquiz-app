package com.englishquiz.DAO;

import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;

import com.englishquiz.activities.SignInActivity;
import com.englishquiz.callBacks.Answer_doneCallBack;
import com.englishquiz.constant.Constant;
import com.englishquiz.model.Answer_done;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class Answer_DoneDAO {
    public Answer_DoneDAO() {
    }

    String UserID=FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
    Boolean done=false;
    HashMap<String, Answer_done> answer_doneHashMap = new HashMap<>();
    public void getAnswer_done( Answer_doneCallBack myCallback) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(new Constant().DATABASE);
        myRef.child("Answer_done").child(UserID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String id = snapshot.child("id").getValue().toString();
                    String pre_exercise = snapshot.child("pre_exercise").getValue().toString();
                    String correct_ans = snapshot.child("correct_ans").getValue().toString();
                    String user_ans = snapshot.child("user_ans").getValue().toString();
                    String question = snapshot.child("question").getValue().toString();
                    Answer_done e = new Answer_done(id, pre_exercise, correct_ans, user_ans, question);
                    answer_doneHashMap.put(id,e);
                }
                myCallback.onCallbackAnswer_done(answer_doneHashMap);
            }

            @Override
            public void onCancelled(DatabaseError error) {
            }
        });
    }

    public void addAnswer_done( Answer_done ans, String index, boolean start){
        done = start;
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(new Constant().DATABASE).child("Answer_done").child(UserID).child(index);
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (!done)
                {
                    ans.setId(index);
                    myRef.setValue(ans);
                    done=true;
                }else{
                    return;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        start=true;
    }
}
