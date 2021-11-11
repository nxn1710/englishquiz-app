package com.englishquiz.DAO;

import android.util.Log;

import androidx.annotation.NonNull;

import com.englishquiz.callBacks.RankingCallBack;
import com.englishquiz.constant.Constant;
import com.englishquiz.model.Exercise;
import com.englishquiz.model.Ranking;
import com.englishquiz.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class RankingDAO {

    HashMap<String, Ranking> rankingHashMap = new HashMap<>();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference(new Constant().DATABASE);

    public RankingDAO() {

    }

    public HashMap<String, Ranking> getRankingHashMap() {
        return rankingHashMap;
    }

    public void getRanking(RankingCallBack myCallback) throws InterruptedException {
        myRef.child("Ranking").orderByChild("position").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.e("E", "onDataChange: " + dataSnapshot.getChildrenCount() );
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String id = snapshot.child("id").getValue().toString();
                    String user = snapshot.child("user").getValue().toString();
                    String position = snapshot.child("position").getValue().toString();
                    String score = snapshot.child("score").getValue().toString();
                    Ranking e = new Ranking(id, user, position, score);
                    rankingHashMap.put(id,e);
                }
                myCallback.onCallbackRanking(rankingHashMap);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
}
