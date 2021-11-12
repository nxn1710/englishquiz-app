package com.englishquiz.adapter;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.englishquiz.R;
import com.englishquiz.model.Answer;
import com.englishquiz.model.Answer_done;

import java.util.ArrayList;

public class ListViewAdapterForAnswerReview extends BaseAdapter {
    Context context;
    ArrayList<Answer> modelArrayList;
    Answer_done answer_done;
    public ListViewAdapterForAnswerReview(Context context, ArrayList<Answer> modelArrayList, Answer_done answer_done) {
        this.context = context;
        this.modelArrayList = modelArrayList;
        this.answer_done = answer_done;
    }

    @Override
    public int getCount() {
        return modelArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.answer, null);
        TextView answer=view.findViewById(R.id.answer);
        answer.setText(modelArrayList.get(i).getDescription());
        if (!answer_done.getUser_ans().equals("-1") && answer_done.getUser_ans().equals(i+""))
            answer.setForeground(new ColorDrawable(0x33ff0000));
        if (answer_done.getCorrect_ans().equals(i+""))
            answer.setForeground(new ColorDrawable(0x3300ff00));
        return view;
    }
}
