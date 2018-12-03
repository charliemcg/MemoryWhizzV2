package com.violenthoboenterprises.memorygame.model;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.util.Log;

import com.violenthoboenterprises.memorygame.presenter.HighScoresDatabase;
import com.violenthoboenterprises.memorygame.presenter.TheDao;

import java.util.List;
import java.util.concurrent.ExecutionException;

/*
 * This is the repository where database queries are called off of the UI thread.
 */
public class HighScoresRepository {

    private TheDao theDao;
    private LiveData<List<HighScore>> allHighScores;
//    private int specificHighScore;

    public HighScoresRepository(Application application){
        HighScoresDatabase highScoresDatabase = HighScoresDatabase.getInstance(application);
        theDao = highScoresDatabase.theDao();
        allHighScores = theDao.getHighScores();
//        specificHighScore = theDao.getSpecificHighScore(0);
    }

    public void insert(HighScore highScore){
        new InsertHighScoreAsyncTask(theDao).execute(highScore);
    }

    public void update(HighScore highScore){
        new UpdateHighScoreAsyncTask(theDao).execute(highScore);
    }

    public LiveData<List<HighScore>> getAllHighScores(){
        return allHighScores;
    }

    public int getSpecificHighScore(final int id) {
        AsyncTask<Integer, Void, Integer> result = new GetSpecificHighScoreAsyncTask(theDao, id).execute(id);
        int intResult = 0;
        try {
            intResult = result.get();
            return intResult;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return intResult;
    }

    public int getMinScore(){
        AsyncTask<Void, Void, Integer> result = new GetMinScore(theDao).execute();
        int intResult = 0;
        try {
            intResult = result.get();
            return intResult;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return intResult;
    }

    public HighScore getMinHighScore(int minScore) {
        AsyncTask<Integer, Void, HighScore> result = new GetMinHighScoreAsyncTask(theDao, minScore).execute(minScore);
        HighScore highScoreResult = null;
        try{
            highScoreResult = result.get();
            return highScoreResult;
        }catch (InterruptedException e){
            e.printStackTrace();
        }catch(ExecutionException e){
            e.printStackTrace();
        }
        return highScoreResult;
    }

    /**********************************************************************************************/

    private static class GetMinScore extends AsyncTask<Void, Void, Integer>{
        private TheDao theDao;
        private GetMinScore(TheDao theDao){this.theDao = theDao;}

//        @Override
//        protected Integer doInBackground(Integer... integers) {
//            theDao.getMinScore();
//            return null;
//        }

        @Override
        protected Integer doInBackground(Void... voids) {
//            return null;
            return theDao.getMinScore();
        }
    }

    //This is for displaying data on the UI so it should be LiveData
    private static class InsertHighScoreAsyncTask extends AsyncTask<HighScore, Void, Void> {

        private TheDao theDao;

        private InsertHighScoreAsyncTask(TheDao theDao){
            this.theDao = theDao;
        }

        @Override
        protected Void doInBackground(HighScore... highScores){
            theDao.insert(highScores[0]);
            return null;
        }
    }

    private static class UpdateHighScoreAsyncTask extends AsyncTask<HighScore, Void, Void>{

        private TheDao theDao;

        private UpdateHighScoreAsyncTask(TheDao theDao){
            this.theDao = theDao;
        }

        @Override
        protected Void doInBackground(HighScore... highScores){
            theDao.update(highScores[0]);
            return null;
        }
    }

    private static class GetSpecificHighScoreAsyncTask extends AsyncTask<Integer, Void, Integer>{
        private TheDao theDao;
        private int id;
        public GetSpecificHighScoreAsyncTask(TheDao theDao, int id) {
            this.theDao = theDao;
            this.id = id;
        }
        @Override
        protected Integer doInBackground(Integer... integers){
            return theDao.getSpecificHighScore(id);
//            return null;
        }
    }

    private static class GetMinHighScoreAsyncTask extends AsyncTask<Integer, Void, HighScore>{
        private TheDao theDao;
        private int minScore;
        private GetMinHighScoreAsyncTask(TheDao theDao, int minScore){
            this.theDao = theDao;
            this.minScore = minScore;
        }

        @Override
        protected HighScore doInBackground(Integer... integers) {
            return theDao.getMinHighScore(minScore);
        }
//        @Override
//        protected HighScore doInBackground(HighScore... highScores){
//            return theDao.getMinHighScore(minScore);
//        }
    }

//    public int getMinScore() {return minScore;}

}
