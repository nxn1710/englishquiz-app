package com.englishquiz.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.englishquiz.DAO.Answer_DoneDAO;
import com.englishquiz.DAO.QuestionDAO;
import com.englishquiz.DAO.RankingDAO;
import com.englishquiz.DAO.UserDAO;
import com.englishquiz.MainActivity;
import com.englishquiz.R;
import com.englishquiz.callBacks.Answer_doneCallBack;
import com.englishquiz.callBacks.QuestionCallBack;
import com.englishquiz.callBacks.RankingCallBack;
import com.englishquiz.model.Answer;
import com.englishquiz.model.Answer_done;
import com.englishquiz.model.Exercise;
import com.englishquiz.model.Question;
import com.englishquiz.model.Ranking;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class QuizResultActivity extends AppCompatActivity {
    CardView btnHome, btnReview, btnTestAgain;
    TextView txtScore, txtCompletation, txtTotal, txtCorrect, txtWrong;
    Integer score=0;
    String UserID=FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
    private ArrayList<Answer_done> answer_dones = new ArrayList<>();
    private HashMap<String,Answer_done> answer_doneHashMap = new HashMap<>();
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_result);
        init();
        action();
        getdata();
        Intent i;
    }
    public void init(){
        txtScore = findViewById(R.id.txtScore);
        txtCompletation = findViewById(R.id.txtCompletation);
        txtTotal = findViewById(R.id.txtTotal);
        txtWrong = findViewById(R.id.txtWrong);
        txtCorrect = findViewById(R.id.txtCorrect);
        btnHome = findViewById(R.id.btnHome);
        btnReview = findViewById(R.id.btnReview);
        btnTestAgain = findViewById(R.id.btnTestAgain);
    }
    public void action(){
        Intent intentReview = new Intent(this, ReviewActivity.class);
        btnReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intentReview);
            }
        });
        Intent intentHome = new Intent(this, MainActivity.class);
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intentHome);
            }
        });
        Intent intentAgain = new Intent(this,QuizActivity.class);
        btnTestAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intentAgain);
            }
        });
        Intent intentProfile = new Intent(this, MainActivity.class);
        intentProfile.putExtra("id",2);
    }
    public void getdata(){
        getAnswer_done();
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
                    if (item.getCorrect_ans().equals(item.getUser_ans())){
                        score++;
                        Log.e("895","SCORE: "+score+"");
                    }
                }
                UserDAO dao = new UserDAO();
                dao.addScore(score);
                RankingDAO rankingDAO = new RankingDAO();

                Integer total = answer_doneHashMap.size();
                String point = score*10+"";
                String completation = (int)(score*100/(float)total) + "%";
                txtScore.setText(point);
                txtCompletation.setText(completation);
                txtTotal.setText(total+"");
                txtCorrect.setText(score+"");
                txtWrong.setText(total-score+"");
            }
        });
    }

}