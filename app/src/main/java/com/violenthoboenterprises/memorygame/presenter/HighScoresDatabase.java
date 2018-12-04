package com.violenthoboenterprises.memorygame.presenter;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.violenthoboenterprises.memorygame.model.HighScore;

import java.util.Calendar;

/*
 * This is the database
 */
@Database(entities = {HighScore.class}, version = 3)
public abstract class HighScoresDatabase extends RoomDatabase {

    //There is only one database instance
    private static HighScoresDatabase instance;

    public abstract TheDao theDao();

    public static synchronized HighScoresDatabase getInstance(Context context){
        if (instance == null){
            //Initialising the database
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    HighScoresDatabase.class, "high_scores")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    //Adding default data to the database
    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {

        private TheDao theDao;

        private PopulateDbAsyncTask(HighScoresDatabase db){
            theDao = db.theDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            Calendar cal = Calendar.getInstance();
            int day = cal.get(Calendar.DAY_OF_MONTH);
            int month = cal.get(Calendar.MONTH) + 1;
            int year = cal.get(Calendar.YEAR);
            String date = day + "/" + month + "/" + year;
            //Adding 5 scores each with a value of 0
            for(int i = 0; i < 5 ; i++){
                theDao.insert(new HighScore(0, date));
            }
            return null;
        }

    }

}
