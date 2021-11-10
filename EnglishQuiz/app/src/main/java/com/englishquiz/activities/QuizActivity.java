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

import com.englishquiz.DAO.AnswerDAO;
import com.englishquiz.DAO.Answer_DoneDAO;
import com.englishquiz.DAO.ExerciseDAO;
import com.englishquiz.DAO.Pre_ExerciseDAO;
import com.englishquiz.DAO.QuestionDAO;
import com.englishquiz.adapter.ViewPagerAdapterForQuestion;
import com.englishquiz.callBacks.AnswerCallBack;
import com.englishquiz.callBacks.Answer_doneCallBack;
import com.englishquiz.callBacks.ExerciseCallBack;
import com.englishquiz.callBacks.QuestionCallBack;
import com.englishquiz.model.*;
import com.englishquiz.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class QuizActivity extends AppCompatActivity {
    Button btnNext;
    FirebaseDatabase database;
    DatabaseReference myRef;
    private ArrayList<Answer> answers = new ArrayList<>();
    private ArrayList<Question> questions = new ArrayList<>();
    private ArrayList<Exercise> exercises = new ArrayList<>();
    private ArrayList<Answer_done> answer_dones = new ArrayList<>();
    private HashMap<String,Exercise> exerciseHashMap = new HashMap<>();
    private HashMap<String,Question> questionHashMap = new HashMap<>();
    private HashMap<String,Answer> answerHashMap = new HashMap<>();
    private HashMap<String,Answer_done> answer_doneHashMap = new HashMap<>();
    private ViewPager2 viewPagerQuestions;
    private ProgressBar loadQuestionBar;
    String TAG = "895";
    String UserID="1";
    Boolean submit=false;
    Integer score=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        getData();
        btnNext = findViewById(R.id.btn_next);
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
                Pre_ExerciseDAO pre_exerciseDAO = new Pre_ExerciseDAO();
                pre_exerciseDAO.addPre_Exercise(new Pre_Exercise("1",UserID,"0"));
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
    }
    private void action(){
        //Intent i = new Intent(this, QuizResultActivity.class);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(i);
                Integer index = viewPagerQuestions.getCurrentItem();
                if (!submit) {
                    if (index < questions.size() - 1) {
                        viewPagerQuestions.setCurrentItem(index + 1);
                    }
                    if (index == questions.size() - 2) {
                        btnNext.setText("FINISH");
                        submit = true;
                    }
                }else{
                    getAnswer_done();
                }
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
        AnswerDAO dao = new AnswerDAO();
        dao.getAnswer(new AnswerCallBack() {
            @Override
            public void onCallbackAnswer(HashMap<String, Answer> value) {
                answerHashMap = value;
                for (Answer item : answerHashMap.values()) {
                    answers.add(item);
                }
            }
        });
    }
    private void getQuestion() {
        QuestionDAO dao = new QuestionDAO();
        dao.getQuestion(new QuestionCallBack() {
            @Override
            public void onCallbackQuestion(HashMap<String, Question> value) {
                questionHashMap = value;
                questionHashMap.remove("31");
                for (Question item : questionHashMap.values()) {
                    questions.add(item);
                }
            }
        });
    }
    private void getExercise() {
        ExerciseDAO dao = new ExerciseDAO();
        dao.getExercise(new ExerciseCallBack() {
            @Override
            public void onCallbackExercise(HashMap<String, Exercise> value) {
                exerciseHashMap = value;
                for (Exercise item : exerciseHashMap.values()) {
                    exercises.add(item);
                }
            }
        });
    }
    private void getAnswer_done() {
        score=0;
        Answer_DoneDAO dao = new Answer_DoneDAO();
        dao.getAnswer_done(new Answer_doneCallBack() {
            @Override
            public void onCallbackAnswer_done(HashMap<String, Answer_done> value) {
                answer_doneHashMap = value;
                for (Answer_done item : answer_doneHashMap.values()) {
                    answer_dones.add(item);
                    Log.e("895","XXX " +item.getQuestion()+" ++ "+ item.getCorrect_ans()+" ++ "+item.getUser_ans() );
                    if (item.getCorrect_ans().equals(item.getUser_ans())){
                        score++;
                        Log.e("895","SCORE: "+score+"");
                    }
                }
            }
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