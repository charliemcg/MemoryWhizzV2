package com.violenthoboenterprises.memorygame.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "high_scores")
public class HighScore {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "score")
    private int score;

    @ColumnInfo(name = "date")
    private String date;

    public HighScore(int score, String date){
        this.score = score;
        this.date = date;
    }

    public int getId(){return id;}

    public void setId(int id){this.id = id;}

    public int getScore(){return score;}

    public String getDate(){return date;}

//    public int getId(){return id;}
//
//    public void setId(int id){this.id = id;}
//
//    public int getScore(){return score;}
//
//    public void setScore(int score){this.score = score;}
//
//    public String getDate(){return date;}
//
//    public void setDate(String date){this.date = date;}

}
