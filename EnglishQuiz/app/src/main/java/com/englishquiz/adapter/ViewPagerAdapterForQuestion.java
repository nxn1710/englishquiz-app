package com.englishquiz.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;

import com.englishquiz.DAO.Answer_DoneDAO;
import com.englishquiz.R;
import com.englishquiz.model.Answer;
import com.englishquiz.model.Answer_done;
import com.englishquiz.model.Question;

import java.util.ArrayList;
import java.util.HashMap;

public class ViewPagerAdapterForQuestion extends RecyclerView.Adapter<ViewPagerAdapterForQuestion.ViewHolder> {
    private Context context;
    private ArrayList<Question> modelArrayList;
    private Integer pre_index=-1;
    private String UserID="1";
    Answer_DoneDAO answer_doneDAO = new Answer_DoneDAO();
    Answer_done a;
    public ViewPagerAdapterForQuestion(Context context, ArrayList<Question> modelArrayList) {
        this.context = context;
        this.modelArrayList = modelArrayList;
    }

    @NonNull
    @Override
    public ViewPagerAdapterForQuestion.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.question, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewPagerAdapterForQuestion.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Question question = modelArrayList.get(position);
        holder.questionIndex1.setText(position+1+"");
        holder.questionIndex2.setText(position+1+"");
        holder.questionTotal.setText("/"+modelArrayList.size());
        holder.questionContent.setText(question.getDescription());
        ListViewAdapterForAnswer adapter = new ListViewAdapterForAnswer(context, question.getListOfAnswer());
        adapter.notifyDataSetChanged();
        holder.listOfAnswer.setAdapter(adapter);
        Integer i=0;
        Boolean add=true;
        //Log.e("895","THUAN/////////////////////////////////////////////////////////");
        //Log.e("895","question "+question.getDescription()+"");
        for (Answer ans : question.getListOfAnswer()){
            //Log.e("895","ans "+ans.toString());
            if (ans.getIs_correct().equals("true") && add){
                //Log.e("895",add+"");
                a = new Answer_done("1",i+"","-1",ans.getQuestion());
                answer_doneDAO.addAnswer_done(a,position+1+"",false);
                Log.e("895",a.toString());
                //Log.e("895","add "+position+"");
                add = false;
                break;
            }
            i++;
        }
        //if(modelArrayList.size()-1==position){
        //    holder.btnNext.setText("FINISH");
        //}
        holder.listOfAnswer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (pre_index!=-1) {
                    holder.listOfAnswer.getChildAt(pre_index).setForeground(new ColorDrawable(0x00000000));
                }
                pre_index=i;
                holder.listOfAnswer.getChildAt(i).setForeground(new ColorDrawable(0x330000ff));
                a.setUser_ans(i+"");
                answer_doneDAO.addAnswer_done(a,position+1+"",false);
                //Log.e("895","update "+position+1+"");
            }
        });
    }

    @Override
    public int getItemCount() {
        return modelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView questionIndex1;
        TextView questionIndex2;
        TextView questionTotal;
        TextView questionContent;
        ListView listOfAnswer;
        Button btnNext;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            questionIndex1 = itemView.findViewById(R.id.txt_question_index_1);
            questionIndex2 = itemView.findViewById(R.id.txt_question_index_2);
            questionTotal = itemView.findViewById(R.id.txt_question_total);
            questionContent = itemView.findViewById(R.id.txt_question_content);
            listOfAnswer = itemView.findViewById(R.id.list_of_answers);
            btnNext = itemView.findViewById(R.id.btn_next);
        }
    }
}
