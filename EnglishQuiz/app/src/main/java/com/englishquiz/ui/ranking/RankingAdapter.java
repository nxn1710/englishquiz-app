package com.englishquiz.ui.ranking;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.englishquiz.model.Ranking;
import com.englishquiz.R;
import com.englishquiz.model.User;

import java.util.List;


public class RankingAdapter extends RecyclerView.Adapter<RankingAdapter.ViewHolder> {

    private List<Ranking> rankingList;
    private List<User> userList;
    private Context context;


    public RankingAdapter(Context context, List<Ranking> rankingList, List<User> userList) {
        this.userList = userList;
        this.rankingList = rankingList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View rankingTopView = inflater.inflate(R.layout.item_ranking, parent, false);
        ViewHolder viewHolder = new ViewHolder(rankingTopView);
        Log.e("onCreateViewHolder: ", rankingList.size()+ "");
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Ranking ranking = rankingList.get(position);
        holder.topPosition.setText(ranking.getPosition() + "");
        holder.topName.setText(getFullName(ranking.getUser()));
        holder.topScore.setText(ranking.getScore()+ "pt");
    }

    @Override
    public int getItemCount() {

        if (rankingList == null || rankingList.isEmpty() ) {
            return 0;
        }

        return rankingList.size();

    }



    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView topPosition;
        public TextView topName;
        public TextView topScore;

        public ViewHolder(View itemView) {
            super(itemView);
            topPosition = (TextView) itemView.findViewById(R.id.topPosition);
            topName = (TextView) itemView.findViewById(R.id.topName);
            topScore = (TextView) itemView.findViewById(R.id.topScore);
        }
    }

    private String getFullName(String userId) {
        for (User user : userList) {
            if (user.getId().equals(userId)) {
                return user.getUsername();
            }
        }
        return "";
    }
}
