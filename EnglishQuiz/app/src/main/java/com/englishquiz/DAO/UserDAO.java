package com.englishquiz.DAO;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.englishquiz.callBacks.RankingCallBack;
import com.englishquiz.callBacks.UserCallBack;
import com.englishquiz.callBacks.UsersCallBack;
import com.englishquiz.constant.Constant;
import com.englishquiz.model.Exercise;
import com.englishquiz.model.Ranking;
import com.englishquiz.model.User;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

public class UserDAO {

    User user;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference(new Constant().DATABASE);
    List<User> userList = new ArrayList<>();

    public UserDAO() {
    }

    public void getUser(UserCallBack myCallback) throws InterruptedException {
        try{
            myRef.child("User").child(FirebaseAuth.getInstance().getCurrentUser().getDisplayName()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String id = snapshot.child("id").getValue().toString();
                    String username = snapshot.child("username").getValue().toString();
                    String mail = snapshot.child("mail").getValue().toString();
                    String score_max = snapshot.child("score_max").getValue().toString();
                    String first_name = snapshot.child("first_name").getValue().toString();
                    String last_name = snapshot.child("last_name").getValue().toString();
                    String national = snapshot.child("national").getValue().toString();
                    String career = snapshot.child("career").getValue().toString();
                    user = new User(id, username, mail, score_max, first_name, last_name, national, career);
                    myCallback.onCallbackUser(user);
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
        }catch (Exception e){

        }
    }

    public void updateUser(User user) {
        myRef.child("User").child(user.getId()).setValue(user);
        try {
            getUser(new UserCallBack() {
                @Override
                public void onCallbackUser(User user) {
                }
            });
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void addUserProfileFirstTime(User user) {
        myRef.child("User").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String id = (snapshot.getChildrenCount() + 1) + "";
                //set displayName for authentication
                FirebaseUser userAuth = FirebaseAuth.getInstance().getCurrentUser();
                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                        .setDisplayName(id).build();
                userAuth.updateProfile(profileUpdates);
                user.setMail(userAuth.getEmail());
                user.setId(id);
                user.setScore_max("0");
                updateUser(user);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        }
        );
    }

    public void addScore(Integer score){
        String UserID=FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
        myRef = myRef.child("User").child(UserID).child("score_max");
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Integer max = Integer.parseInt(snapshot.getValue().toString());
                if (max<score){
                    myRef.setValue(score+"");
                    try {
                        getRanking(score);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void getUsers(UsersCallBack myCallback) throws InterruptedException {
        try{
            myRef.child("User").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        String id = snapshot.child("id").getValue().toString();
                        String username = snapshot.child("username").getValue().toString();
                        String mail = snapshot.child("mail").getValue().toString();
                        String score_max = snapshot.child("score_max").getValue().toString();
                        String first_name = snapshot.child("first_name").getValue().toString();
                        String last_name = snapshot.child("last_name").getValue().toString();
                        String national = snapshot.child("national").getValue().toString();
                        String career = snapshot.child("career").getValue().toString();
                        user = new User(id, username, mail, score_max, first_name, last_name, national, career);
                        userList.add(user);
                    }
                    myCallback.onCallbackUsers(userList);
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
        }catch (Exception e){

        }
    }

    private List<Ranking> rankings = new ArrayList<>();
    String UserID=FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
    private void getRanking(Integer score) throws InterruptedException {
        RankingDAO dao = new RankingDAO();
        dao.getTop8(new RankingCallBack() {
            @Override
            public void onGetTop8(List<Ranking> value) {
                rankings = value;
                RankingDAO rankingDAO = new RankingDAO();
                Log.e("895","size: "+value.size()+"");
                for (Ranking item: rankings) {
                    if (Integer.parseInt(item.getScore()) == score){
                        Ranking r = new Ranking(UserID+"",UserID+"",item.getPosition(),score+"");
                        Log.e("895","add item"+r.toString());
                        dao.updateRanking(r);
                    }
                    if (Integer.parseInt(item.getScore()) <= score){
                        Log.e("895","update item"+item.toString());
                        item.setPosition(Integer.parseInt(item.getPosition())+1+"");
                        dao.updateRanking(item);
                    }
                }
            }
        });
    }

}
