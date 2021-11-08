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
            myRef.child("User").child(FirebaseAuth.getInstance().getCurrentUser().getDisplayName()).addValueEventListener(new ValueEventListener() {
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
        myRef.child("User").child(user.getId()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                myRef.child("User").child(user.getId()).setValue(user);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void addUserProfileFirstTime(User user) {
        myRef.child("User").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String id = (snapshot.getChildrenCount() + 1) + "";

                //set displayName for authentication
                FirebaseUser userAuth = FirebaseAuth.getInstance().getCurrentUser();
                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                        .setDisplayName(id).build();
                userAuth.updateProfile(profileUpdates);
                Log.e("TAG", "onDataChange: " + id );

                user.setId(id);

                updateUser(user);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


}
