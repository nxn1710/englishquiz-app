package com.englishquiz.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.englishquiz.R;
import com.englishquiz.model.Answer;

import java.util.ArrayList;

public class ListViewAdapterForAnswer  extends BaseAdapter {
    Context context;
    ArrayList<Answer> modelArrayList;

    public ListViewAdapterForAnswer(Context context, ArrayList<Answer> modelArrayList) {
        this.context = context;
        this.modelArrayList = modelArrayList;
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
        return view;
    }
}
