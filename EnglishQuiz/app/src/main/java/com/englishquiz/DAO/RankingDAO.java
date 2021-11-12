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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RankingDAO {

    List<Ranking> rankingList = new ArrayList<>();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference(new Constant().DATABASE);
    Ranking ranking = new Ranking();

    public RankingDAO() {

    }


    public void getTop8(RankingCallBack myCallback) throws InterruptedException {
        myRef.child("Ranking").orderByChild("position").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String id = snapshot.child("id").getValue().toString();
                    String user = snapshot.child("user").getValue().toString();
                    String position = snapshot.child("position").getValue().toString();
                    String score = snapshot.child("score").getValue().toString();
                    Ranking e = new Ranking(id, user, position, score);
                    rankingList.add(e);
                }
                myCallback.onGetTop8(rankingList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    public void updateRanking(Ranking ranking){
        myRef.child("Ranking").child(ranking.getUser()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                myRef.child("Ranking").child(ranking.getUser()+"").setValue(ranking);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


}
