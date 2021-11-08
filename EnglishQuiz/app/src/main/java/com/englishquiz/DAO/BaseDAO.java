package com.englishquiz.DAO;

import androidx.annotation.NonNull;

import com.englishquiz.callBacks.BaseCallBack;
import com.englishquiz.constant.Constant;
import com.englishquiz.model.Base;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class BaseDAO {

    public BaseDAO() {
    }

    List<Base> bases = new ArrayList<>();

    public void getBases(BaseCallBack myCallback) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(new Constant().DATABASE);
        myRef.child("Base").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String id = snapshot.child("id").getValue().toString();
                    String score_min = snapshot.child("score_min").getValue().toString();
                    String score_max = snapshot.child("score_max").getValue().toString();
                    String certificate = snapshot.child("certificate").getValue().toString();
                    String type = snapshot.child("type").getValue().toString();
                    bases.add(new Base(id, score_min, score_max, certificate, type));
                }
                myCallback.onCallBackBases(bases);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
