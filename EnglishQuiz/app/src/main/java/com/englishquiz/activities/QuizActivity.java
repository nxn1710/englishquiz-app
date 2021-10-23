package com.englishquiz.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ProgressBar;

import com.englishquiz.adapter.ViewPagerAdapterForQuestion;
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
    private ViewPager2 viewPagerQuestions;
    private ProgressBar loadQuestionBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        getData();
        btn = findViewById(R.id.button4);
        viewPagerQuestions = findViewById(R.id.viewpager_questions);
        loadQuestionBar = findViewById(R.id.load_questioin_bar);
        action();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                loadQuestionBar.setVisibility(View.GONE);
                setAnsForQus();
                ViewPagerAdapterForQuestion myAdapter = new ViewPagerAdapterForQuestion(getApplicationContext(),questions);
                viewPagerQuestions.setAdapter(myAdapter);
            }
        }, 2000);

    }

    private void setAnsForQus() {
        for (int i = 0; i < answers.size(); i++) {
            for (int j = 0; j < questions.size(); j++ ){
                if (answers.get(i).getQuestion().equals(questions.get(j).getId())){
                    questions.get(j).getListOfAnswer().add(answers.get(i));
                }
            }
        }
        for (int i = 0; i < questions.size(); i++) {
            Log.e("895",questions.get(i).getId() +"/"+questions.get(i).getListOfAnswer().size());
        }
    }

    private void action(){
        Intent i = new Intent(this, QuizResultActivity.class);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(i);
            }
        });


    }

    private void getData(){
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("TestWeb");
        getExercise();
        getQuestion();
        getAnswer();
        Log.d(TAG,"in");
    }

    private void getAnswer() {
        Log.d(TAG,"chay");
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
                Log.d(TAG,"chay xong");
//                for (Answer answer: answers)
//                    Log.d(TAG,answer.toString());
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
//                for (Question question: questions)
//                    Log.d(TAG,question.toString());
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
//                for (Exercise exercise: exercises)
//                    Log.d(TAG, exercise.toString());
            }

            @Override
            public void onCancelled(DatabaseError error) {}
        });
    }

    private void closeKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            manager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }


}