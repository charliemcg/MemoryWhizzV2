package com.violenthoboenterprises.memorygame.presenter;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.violenthoboenterprises.memorygame.model.HighScore;

import java.util.List;

@Dao
public interface TheDao {

    @Insert
    void insert(HighScore highScore);

    @Update
    void update(HighScore highScore);

    @Query("SELECT * FROM high_scores ORDER BY score DESC")
    LiveData<List<HighScore>> getHighScores();

    @Query("SELECT score FROM high_scores WHERE id = :id")
    int getSpecificHighScore(int id);

    @Query("SELECT MIN(score) FROM high_scores")
    int getMinScore();

    @Query("SELECT MIN(id) FROM high_scores WHERE score = :minScore")
    int getMinScoreId(int minScore);
}
