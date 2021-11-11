package com.englishquiz.DAO;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.englishquiz.callBacks.UserCallBack;
import com.englishquiz.constant.Constant;
import com.englishquiz.model.Exercise;
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

import java.util.concurrent.Executor;

public class UserDAO {

    User user;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference(new Constant().DATABASE);

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
                    Log.e("TAG123", user.toString() );
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
                Log.e("TAG", "onClick: 34" );
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


}
