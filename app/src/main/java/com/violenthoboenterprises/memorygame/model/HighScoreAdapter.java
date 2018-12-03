package com.violenthoboenterprises.memorygame.model;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.violenthoboenterprises.memorygame.R;

import java.util.ArrayList;
import java.util.List;

/*
 * This is where data gets added to the recycler view list item
 */
public class HighScoreAdapter extends RecyclerView.Adapter<HighScoreAdapter.HighScoreHolder> {

    private List<HighScore> highScores = new ArrayList<>();

    @NonNull
    @Override
    public HighScoreHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.high_score_item, parent, false);
        return new HighScoreHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull HighScoreHolder holder, int position) {
        HighScore currentScore = highScores.get(position);
        holder.textViewScore.setText(String.valueOf(currentScore.getScore()));
        holder.textViewDate.setText(currentScore.getDate());
    }

    @Override
    public int getItemCount() {
        return highScores.size();
    }

    public void setHighScores(List<HighScore> highScores){
        this.highScores = highScores;
        notifyDataSetChanged();
    }

    public HighScore getHighScoreAt(int position){return highScores.get(position);}

    class HighScoreHolder extends RecyclerView.ViewHolder{
        private TextView textViewScore;
        private TextView textViewDate;

        public HighScoreHolder(View itemView) {
            super(itemView);
            textViewScore = itemView.findViewById(R.id.text_view_score);
            textViewDate = itemView.findViewById(R.id.text_view_date);

        }
    }

}
