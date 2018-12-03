package com.violenthoboenterprises.memorygame.model;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.violenthoboenterprises.memorygame.presenter.TheDao;

import java.util.List;

/*
 * The ViewModel is persisting data if UI activity state is changed
 */
public class HighScoreViewModel extends AndroidViewModel {

    private HighScoresRepository repository;
//    private int specificHighScore;

    //This is for displaying data on the UI so it should be LiveData
    private LiveData<List<HighScore>> allHighScores;

    private int getMinScore;

    public HighScoreViewModel(@NonNull Application application) {
        super(application);
        repository = new HighScoresRepository(application);
        allHighScores = repository.getAllHighScores();
//        specificHighScore = repository.getSpecificHighScore(id);
        getMinScore = repository.getMinScore();
    }

    public void insert(HighScore highScore){repository.insert(highScore);}

    public void update(HighScore highScore){repository.update(highScore);}

    public LiveData<List<HighScore>> getAllHighScores(){return allHighScores;}

    public int getSpecificScore(int id) {return
            /*specificHighScore;*/repository.getSpecificHighScore(id);}

    public int getMinScore() {return getMinScore;}

    public HighScore getMinHighScore(int minScore) { return repository.getMinHighScore(minScore);}
}
