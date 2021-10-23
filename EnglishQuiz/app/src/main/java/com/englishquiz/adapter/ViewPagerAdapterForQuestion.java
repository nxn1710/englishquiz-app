package com.englishquiz.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;

import com.englishquiz.R;
import com.englishquiz.model.Question;

import java.util.ArrayList;

public class ViewPagerAdapterForQuestion extends RecyclerView.Adapter<ViewPagerAdapterForQuestion.ViewHolder> {
    private Context context;
    private ArrayList<Question> modelArrayList;

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
    public void onBindViewHolder(@NonNull ViewPagerAdapterForQuestion.ViewHolder holder, int position) {
        Question question = modelArrayList.get(position);
        holder.questionIndex1.setText(position+1+"");
        holder.questionIndex2.setText(position+1+"");
        holder.questionTotal.setText("/"+modelArrayList.size());
        holder.questionContent.setText(question.getDescription());
        ListViewAdapterForAnswer adapter = new ListViewAdapterForAnswer(context, question.getListOfAnswer());
        adapter.notifyDataSetChanged();
        holder.listOfAnswer.setAdapter(adapter);
        if(modelArrayList.size()-1==position){
            holder.btnNext.setText("FINISH");
        }
        holder.listOfAnswer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                holder.listOfAnswer.getChildAt(i).setBackgroundColor(R.drawable.border_selected_answer);
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
