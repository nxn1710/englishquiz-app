package com.englishquiz.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import com.englishquiz.model.*;
import com.englishquiz.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class QuizActivity extends AppCompatActivity {
    Button btn;
    FirebaseDatabase database;
    DatabaseReference myRef;
    private ArrayList<Answer> answers = new ArrayList<>();
    private ArrayList<Question> questions = new ArrayList<>();
    private ArrayList<Exercise> exercises = new ArrayList<>();
    String TAG = "895";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        btn = findViewById(R.id.button4);
        Intent i = new Intent(this, QuizResultActivity.class);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(i);
            }
        });

        Log.d(TAG, "Loading Quiz");
        getData();

    }

    private void getData(){
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("TestWeb");
        getExercise();
        getQuestion();
        getAnswer();
    }

    private void getAnswer() {
        myRef.child("Answer").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                    String id = snapshot.child("id").getValue().toString();
                    String question = snapshot.child("question").getValue().toString();
                    String description = snapshot.child("description").getValue().toString();
                    String is_correct = snapshot.child("is_correct").getValue().toString();
                    Answer a = new Answer(id, question, description, is_correct);
                    answers.add(a);
                }
                for (Answer answer: answers)
                    Log.d(TAG,answer.toString());
            }

            @Override
            public void onCancelled(DatabaseError error) {}
        });
    }

    private void getQuestion() {
        myRef.child("Question").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                    String id = snapshot.child("id").getValue().toString();
                    String exercise = snapshot.child("exercise").getValue().toString();
                    String description = snapshot.child("description").getValue().toString();
                    String time = snapshot.child("time").getValue().toString();
                    Question q = new Question(id, exercise, description, time);
                    questions.add(q);
                }
                for (Question question: questions)
                    Log.d(TAG,question.toString());
            }

            @Override
            public void onCancelled(DatabaseError error) {}
        });
    }

    private void getExercise() {
        myRef.child("Exercise").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                    String id = snapshot.child("id").getValue().toString();
                    String title = snapshot.child("title").getValue().toString();
                    String description = snapshot.child("description").getValue().toString();
                    String time = snapshot.child("time").getValue().toString();
                    Exercise e = new Exercise(id, title, description, time);
                    exercises.add(e);
                }
                for (Exercise exercise: exercises)
                    Log.d(TAG, exercise.toString());
            }

            @Override
            public void onCancelled(DatabaseError error) {}
        });
    }


}