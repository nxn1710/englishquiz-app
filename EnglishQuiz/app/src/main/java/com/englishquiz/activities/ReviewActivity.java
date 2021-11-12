package com.englishquiz.activities;

import androidx.appcompat.app.AppCompatActivity;
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
import com.englishquiz.MainActivity;
import com.englishquiz.R;
import com.englishquiz.adapter.ViewPagerAdapterForQuestion;
import com.englishquiz.adapter.ViewPagerAdapterForQuestionReview;
import com.englishquiz.callBacks.AnswerCallBack;
import com.englishquiz.callBacks.Answer_doneCallBack;
import com.englishquiz.callBacks.ExerciseCallBack;
import com.englishquiz.callBacks.QuestionCallBack;
import com.englishquiz.model.Answer;
import com.englishquiz.model.Answer_done;
import com.englishquiz.model.Exercise;
import com.englishquiz.model.Pre_Exercise;
import com.englishquiz.model.Question;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;

public class ReviewActivity extends AppCompatActivity {
    Button btnFinish;
    FirebaseDatabase database;
    DatabaseReference myRef;
    private ArrayList<Answer> answers = new ArrayList<>();
    private ArrayList<Question> questions = new ArrayList<>();
    private ArrayList<Exercise> exercises = new ArrayList<>();
    private ArrayList<Answer_done> answer_dones = new ArrayList<>();
    private HashMap<String,Exercise> exerciseHashMap = new HashMap<>();
    private HashMap<String,Question> questionHashMap = new HashMap<>();
    private HashMap<String,Answer> answerHashMap = new HashMap<>();
    private HashMap<String,Answer_done> Answer_DoneHashMap = new HashMap<>();
    private ViewPager2 viewPagerQuestions;
    private ProgressBar loadQuestionBar;
    String TAG = "895";
    String UserID="1";
    Boolean submit=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
        getData();
        btnFinish = findViewById(R.id.btn_finish);
        viewPagerQuestions = findViewById(R.id.viewpager_questions);
        loadQuestionBar = findViewById(R.id.load_questioin_bar);
        action();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                loadQuestionBar.setVisibility(View.GONE);
                setAnsForQus();
                ViewPagerAdapterForQuestionReview myAdapter = new ViewPagerAdapterForQuestionReview(getApplicationContext(),questions,answer_dones);
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
        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
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
        getAnswer_Done();
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
    private void getAnswer_Done() {
        Answer_DoneDAO dao = new Answer_DoneDAO();
        dao.getAnswer_done(new Answer_doneCallBack() {
            @Override
            public void onCallbackAnswer_done(HashMap<String, Answer_done> value) {
                Answer_DoneHashMap = value;
                for (Answer_done item : Answer_DoneHashMap.values()) {
                    answer_dones.add(item);
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